package com.xixi.plugin.asm

import com.xixi.plugin.util.Logger
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

public class AutoMethodVisitor extends AdviceAdapter {

    String methodName

    public AutoMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM4, mv, access, name, desc)
        methodName = name
        Logger.info("||开始扫描方法：${Logger.accCode2String(access)} ${methodName}${desc}")
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {

//        Logger.logForEach("||visitMethodInsn", Logger.getOpName(opcode), owner, name, desc)
        super.visitMethodInsn(opcode, owner, name, desc)
    }

    @Override
    public void visitAttribute(Attribute attribute) {
//        Logger.logForEach("||visitAttribute", attribute)
        super.visitAttribute(attribute)
    }

    @Override
    public void visitEnd() {
//        Logger.logForEach("||visitEnd")
        Logger.info("||结束扫描方法：${methodName}")
        super.visitEnd()
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
//        Logger.logForEach("||visitFieldInsn", Logger.getOpName(opcode), owner, name, desc)
        super.visitFieldInsn(opcode, owner, name, desc)
    }

    @Override
    public void visitIincInsn(int var, int increment) {
//        Logger.logForEach("||visitIincInsn", var, increment)
        super.visitIincInsn(var, increment)
    }

    @Override
    public void visitIntInsn(int i, int i1) {
//        Logger.logForEach("||visitIntInsn", i, i1)
        super.visitIntInsn(i, i1)
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
//        Logger.logForEach("||visitMaxs", maxStack, maxLocals)
        super.visitMaxs(maxStack, maxLocals)
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
//        Logger.logForEach("||visitVarInsn", Logger.getOpName(opcode), var)
        super.visitVarInsn(opcode, var)
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
//        Logger.logForEach("||visitJumpInsn", Logger.getOpName(opcode), label)
        super.visitJumpInsn(opcode, label)
    }

    @Override
    public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
//        Logger.logForEach("||visitLookupSwitchInsn", label, ints, labels)
        super.visitLookupSwitchInsn(label, ints, labels)
    }

    @Override
    public void visitMultiANewArrayInsn(String s, int i) {
//        Logger.logForEach("||visitMultiANewArrayInsn", s, i)
        super.visitMultiANewArrayInsn(s, i)
    }

    @Override
    public void visitTableSwitchInsn(int i, int i1, Label label, Label[] labels) {
//        Logger.logForEach("||visitTableSwitchInsn", i, i1, label, labels)
        super.visitTableSwitchInsn(i, i1, label, labels)
    }

    @Override
    public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
//        Logger.logForEach("||visitTryCatchBlock", label, label1, label2, s)
        super.visitTryCatchBlock(label, label1, label2, s)
    }

    @Override
    public void visitTypeInsn(int opcode, String s) {
//        Logger.logForEach("||visitTypeInsn", Logger.getOpName(opcode), s)
        super.visitTypeInsn(opcode, s)
    }

    @Override
    public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
//        Logger.logForEach("||visitLocalVariable", s, s1, s2, label, label1, i)
        super.visitLocalVariable(s, s1, s2, label, label1, i)
    }

    @Override
    public void visitInsn(int opcode) {
//        Logger.logForEach("||visitInsn", Logger.getOpName(opcode))
        super.visitInsn(opcode)
    }

    @Override
    AnnotationVisitor visitAnnotation(String s, boolean b) {
//        Logger.logForEach("||visitAnnotation", s)
        return super.visitAnnotation(s, b)
    }

    @Override
    protected void onMethodEnter() {
//        Logger.logForEach("||onMethodEnter")
        super.onMethodEnter()
    }

    @Override
    protected void onMethodExit(int opcode) {
//        Logger.logForEach("||onMethodExit", Logger.getOpName(opcode))
        super.onMethodExit(opcode)
    }
}