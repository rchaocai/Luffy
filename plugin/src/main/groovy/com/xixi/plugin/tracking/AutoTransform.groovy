package com.xixi.plugin.tracking

import com.android.annotations.NonNull
import com.android.annotations.Nullable
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.xixi.plugin.*
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

/**
 * 自动埋点追踪，遍历所有文件更换字节码
 */
public class AutoTransform extends Transform {

    @Override
    String getName() {
        return "AutoTrack"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    public void transform(
            @NonNull Context context,
            @NonNull Collection<TransformInput> inputs,
            @NonNull Collection<TransformInput> referencedInputs,
            @Nullable TransformOutputProvider outputProvider,
            boolean isIncremental) throws IOException, TransformException, InterruptedException {

        if (Logger.isDebug()) {
            printlnJarAndDir(inputs)
        }

        /**遍历输入文件*/
        inputs.each { TransformInput input ->
            /**
             * 遍历jar
             */
            input.jarInputs.each { JarInput jarInput ->
                String destName = jarInput.file.name
                /** 截取文件路径的md5值重命名输出文件,因为可能同名,会覆盖*/
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                /** 获得输出文件*/
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                Logger.info("-->start jar ${dest.absolutePath}")
                def modifiedJar = modifyJarFile(jarInput.file, context.getTemporaryDir())
                if (modifiedJar == null) {
                    modifiedJar = jarInput.file
                } else {
                    saveModifiedJarForCheck(modifiedJar)
                }
                FileUtils.copyFile(modifiedJar, dest)
            }
            /**
             * 遍历目录
             */
            input.directoryInputs.each { DirectoryInput directoryInput ->
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY);
                Logger.info("-->start dir  ${dest.absolutePath}")
                File dir = directoryInput.file
                if (dir) {
                    HashMap<String, File> modifyMap = new HashMap<>()
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                        File classFile ->
                            File modified = modifyClassFile(dir, classFile, context.getTemporaryDir())
                            if (modified != null) {
                                //key为相对路径
                                modifyMap.put(classFile.absolutePath.replace(dir.absolutePath, ""), modified)
                            }
                    }
                    FileUtils.copyDirectory(directoryInput.file, dest)
                    modifyMap.entrySet().each {
                        Map.Entry<String, File> en ->
                            File target = new File(dest.absolutePath + en.getKey())
                            Logger.info(target.getAbsolutePath())
                            if (target.exists()) {
                                target.delete()
                            }
                            FileUtils.copyFile(en.getValue(), target)
                            saveModifiedJarForCheck(en.getValue())
                            en.getValue().delete()
                    }
                }
            }
        }
    }


    private static void saveModifiedJarForCheck(File optJar) {
        File dir = DataHelper.ext.hiBeaverDir
        File checkJarFile = new File(dir, optJar.getName())
        if (checkJarFile.exists()) {
            checkJarFile.delete()
        }
        FileUtils.copyFile(optJar, checkJarFile)
    }

    /**
     * Jar文件中修改对应字节码
     */
    private static File modifyJarFile(File jarFile, File tempDir) {
        if (jarFile) {
            return AutoModify.modifyJar(jarFile, tempDir, true)

        }
        return null
    }


    /**
     * 目录文件中修改对应字节码
     */
    private static File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified
        try {
            String className = Util.path2Classname(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
            byte[] modifiedClassBytes = AutoModify.modifyClasses(className, sourceClassBytes)
            if (modifiedClassBytes) {
                modified = new File(tempDir, className.replace('.', '') + '.class')
                if (modified.exists()) {
                    modified.delete()
                }
                modified.createNewFile()
                new FileOutputStream(modified).write(modifiedClassBytes)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return modified

    }

    /**
     * 包括两种数据:jar包和class目录，打印出来用于调试
     */
    private static void printlnJarAndDir(Collection<TransformInput> inputs) {

        def classPaths = []
        String buildTypes
        String productFlavors
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                classPaths.add(directoryInput.file.absolutePath)
                buildTypes = directoryInput.file.name
                productFlavors = directoryInput.file.parentFile.name
                Logger.info("--->项目class目录：${directoryInput.file.absolutePath}")
            }
            input.jarInputs.each { JarInput jarInput ->
                classPaths.add(jarInput.file.absolutePath)
                Logger.info("--->项目jar包：${jarInput.file.absolutePath}")
            }
        }

        def paths = [Util.getExtension().bootClasspath.get(0).absolutePath/*, injectClassPath*/]
        paths.addAll(classPaths)
    }
}
