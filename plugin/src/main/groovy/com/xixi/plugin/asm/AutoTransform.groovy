package com.xixi.plugin.asm

import com.android.annotations.NonNull
import com.android.annotations.Nullable
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.xixi.plugin.GlobalConfig
import com.xixi.plugin.util.AutoMatchUtil
import com.xixi.plugin.bean.AutoClassFilter
import com.xixi.plugin.util.AutoTextUtil
import com.xixi.plugin.util.Logger
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * 自动埋点追踪，遍历所有文件更换字节码
 */
public class AutoTransform extends Transform {

    private static final String VERSION = "v1.0.3"

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

    /**
     * 打印提示信息
     */
    static void printCopyRight() {
        println()
        println '#######################################################################'
        println '##########                                                    '
        println '##########         欢迎使用 Luffy® (' + VERSION + ')无埋点编译插件'
        println '##########           使用过程中碰到任何问题请联系数据中心          '
        println '##########                                                    '
        println '#######################################################################'
        println '##########                                                    '
        println '##########                      插件配置参数                    '
        println '##########                                                    '
        println '##########                 -isDebug:' + GlobalConfig.getParams().isDebug
        println '##########                 -isOpenLogTrack:' + GlobalConfig.getParams().isOpenLogTrack
        println '##########                 -exclude:' + GlobalConfig.exclude
        println '##########                 -include:' + GlobalConfig.include
        List<AutoClassFilter> autoClassFilterList = GlobalConfig.getAutoClassFilter()
        autoClassFilterList.each {
            AutoClassFilter filter ->
                println '##########                                                    '
                println '##########                 -methodName:' + filter.MethodName
                println '##########                 -methodDes:' + filter.MethodDes
                println '##########                 -className:' + filter.ClassName
                println '##########                 -interfaceName:' + filter.InterfaceName
                println '##########                 -isAnnotation:' + filter.isAnnotation
        }
        println '##########                                                    '
        println '##########                                                    '
        println '#######################################################################'
        println()
    }

    @Override
    public void transform(
            @NonNull Context context,
            @NonNull Collection<TransformInput> inputs,
            @NonNull Collection<TransformInput> referencedInputs,
            @Nullable TransformOutputProvider outputProvider,
            boolean isIncremental) throws IOException, TransformException, InterruptedException {


        if (!incremental) {
            outputProvider.deleteAll()
        }

        /**
         * 打印提示信息
         */
        printCopyRight()

        //开始计算消耗的时间
        Logger.info("||=======================================================================================================")
        Logger.info("||                                                 开始计时                                               ")
        Logger.info("||=======================================================================================================")
        def startTime = System.currentTimeMillis()

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
                Logger.info("||-->开始遍历特定jar ${dest.absolutePath}")
                def modifiedJar = modifyJarFile(jarInput.file, context.getTemporaryDir())
                Logger.info("||-->结束遍历特定jar ${dest.absolutePath}")
                if (modifiedJar == null) {
                    modifiedJar = jarInput.file
                }
                FileUtils.copyFile(modifiedJar, dest)
            }
            /**
             * 遍历目录
             */
            input.directoryInputs.each { DirectoryInput directoryInput ->
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                Logger.info("||-->开始遍历特定目录  ${dest.absolutePath}")
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
//                            Logger.info(target.getAbsolutePath())
                            if (target.exists()) {
                                target.delete()
                            }
                            FileUtils.copyFile(en.getValue(), target)
                            en.getValue().delete()
                    }
                }
                Logger.info("||-->结束遍历特定目录  ${dest.absolutePath}")
            }
        }

        //计算耗时
        def cost = (System.currentTimeMillis() - startTime) / 1000
        Logger.info("||=======================================================================================================")
        Logger.info("||                                       计时结束:费时${cost}秒                                           ")
        Logger.info("||=======================================================================================================")
    }

    /**
     * Jar文件中修改对应字节码
     */
    private static File modifyJarFile(File jarFile, File tempDir) {
        if (jarFile) {
            return modifyJar(jarFile, tempDir, true)

        }
        return null
    }

    private static File modifyJar(File jarFile, File tempDir, boolean nameHex) {
        /**
         * 读取原jar
         */
        def file = new JarFile(jarFile)
        /** 设置输出到的jar */
        def hexName = ""
        if (nameHex) {
            hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        }
        def outputJar = new File(tempDir, hexName + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        Enumeration enumeration = file.entries()

        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            InputStream inputStream = file.getInputStream(jarEntry)

            String entryName = jarEntry.getName()
            String className

            ZipEntry zipEntry = new ZipEntry(entryName)

            jarOutputStream.putNextEntry(zipEntry)

            byte[] modifiedClassBytes = null
            byte[] sourceClassBytes = IOUtils.toByteArray(inputStream)
            if (entryName.endsWith(".class")) {
                className = entryName.replace("/", ".").replace(".class", "")
//                Logger.info("Jar:className:" + className)
                if (AutoMatchUtil.isShouldModifyClass(className)) {
                    modifiedClassBytes = AutoModify.modifyClasses(className, sourceClassBytes)
                }
            }
            if (modifiedClassBytes == null) {
                jarOutputStream.write(sourceClassBytes)
            } else {
                jarOutputStream.write(modifiedClassBytes)
            }
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        file.close()
        return outputJar
    }

    /**
     * 目录文件中修改对应字节码
     */
    private static File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        FileOutputStream outputStream = null
        try {
            String className = AutoTextUtil.path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
//            Logger.info("File:className:" + className)
            if (AutoMatchUtil.isShouldModifyClass(className)) {
                byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
                byte[] modifiedClassBytes = AutoModify.modifyClasses(className, sourceClassBytes)
                if (modifiedClassBytes) {
                    modified = new File(tempDir, className.replace('.', '') + '.class')
                    if (modified.exists()) {
                        modified.delete()
                    }
                    modified.createNewFile()
                    outputStream = new FileOutputStream(modified)
                    outputStream.write(modifiedClassBytes)
                }
            } else {
                return classFile
            }
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close()
                }
            } catch (Exception e) {
            }
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
                Logger.info("||项目class目录：${directoryInput.file.absolutePath}")
            }
            input.jarInputs.each { JarInput jarInput ->
                classPaths.add(jarInput.file.absolutePath)
                Logger.info("||项目jar包：${jarInput.file.absolutePath}")
            }
        }
    }
}
