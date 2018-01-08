package com.xixi.plugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

public class ClassModifyAdater extends ClassVisitor implements Opcodes {
    ClassVisitor classVisitor;
    ClassModifyAdater(ClassVisitor classVisitor) {
        super(Opcodes.ASM4, classVisitor)
        this.classVisitor = classVisitor;
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        Log.logEach('* visit ClassModifyAdater*', Log.accCode2String(access), name, signature, superName, interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        MethodVisitor myMv = null;
        Log.info("====start ClassModifyAdater visitMethod====");
        return classVisitor.visitMethod(access, name, desc, signature, exceptions);
    }
}