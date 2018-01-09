package com.xixi.plugin.tracking

import com.xixi.plugin.*
import com.xixi.plugin.bean.TextUtil
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/**
 * Author:xishuang
 * Date:2018.01.08
 * Des:修改字节码
 */
public class AutoModify {

    static File modifyJar(File jarFile, File tempDir, boolean nameHex) {
        Log.info("====start modifyJar ====")
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
        Log.info("====start modifyJar${outputJar.absolutePath} ====")
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
                modifiedClassBytes = modifyClasses(className, sourceClassBytes)
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

    public static byte[] modifyClasses(String className, byte[] srcByteCode) {
        String filter = Controller.getClassName()
        //简单过滤
        if (!TextUtil.isEmpty(filter) && !className.contains(filter)) {
            return srcByteCode
        }
        byte[] classBytesCode = null
        try {
            Log.info("====start modifying ${className}====")
            classBytesCode = modifyClass(srcByteCode)
            Log.info("====revisit modified ${className}====")
            onlyVisitClassMethod(classBytesCode)
            Log.info("====finish modifying ${className}====")
            return classBytesCode
        } catch (Exception e) {
            e.printStackTrace()
        }
        if (classBytesCode == null) {
            classBytesCode = srcByteCode
        }
        return classBytesCode
    }

    private static byte[] modifyClass(byte[] srcClass) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor adapter = new AutoClassVisitor(classWriter)
        ClassReader cr = new ClassReader(srcClass)
        cr.accept(adapter, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }

    private static void onlyVisitClassMethod(byte[] srcClass) throws IOException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor visitor = new AutoClassVisitor(classWriter)
        visitor.onlyVisit = true
        ClassReader cr = new ClassReader(srcClass)
        cr.accept(visitor, ClassReader.EXPAND_FRAMES)
    }
}