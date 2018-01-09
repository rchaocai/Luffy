package com.xixi.plugin.tracking

import com.xixi.plugin.Log
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

public class AutoMethodVisitor extends AdviceAdapter {

    MethodVisitor methodVisitor;

    public AutoMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM4, mv, access, name, desc)
        methodVisitor = mv
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        Logger.logForEach("visitMethodInsn", Log.getOpName(opcode), owner, name, desc)
        super.visitMethodInsn(opcode, owner, name, desc)
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        Logger.logForEach("visitAttribute", attribute)
        super.visitAttribute(attribute)
    }

    @Override
    public void visitEnd() {
        Logger.logForEach("visitEnd")
        super.visitEnd()
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        Logger.logForEach("visitFieldInsn", Log.getOpName(opcode), owner, name, desc)
        super.visitFieldInsn(opcode, owner, name, desc)
    }
//
//    @Override
//    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
//        Logger.logForEach("visitFrame", type, local, nLocal, nStack, stack)
//        super.visitFrame(type, nLocal, local, nStack, stack)
//    }

    @Override
    public void visitLabel(Label label) {
//        Logger.logForEach("visitLabel", label)
        super.visitLabel(label)
    }

    @Override
    public void visitLineNumber(int line, Label label) {
//        Logger.logForEach("visitLineNumber", line, label)
        super.visitLineNumber(line, label)
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        Logger.logForEach("visitIincInsn", var, increment)
        super.visitIincInsn(var, increment)
    }

    @Override
    public void visitIntInsn(int i, int i1) {
        Logger.logForEach("visitIntInsn", i, i1)
        super.visitIntInsn(i, i1)
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        Logger.logForEach("visitMaxs", maxStack, maxLocals)
        super.visitMaxs(maxStack, maxLocals)
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        Logger.logForEach("visitVarInsn", Log.getOpName(opcode), var)
        super.visitVarInsn(opcode, var)
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        Logger.logForEach("visitJumpInsn", Log.getOpName(opcode), label)
        super.visitJumpInsn(opcode, label)
    }

    @Override
    public void visitLdcInsn(Object o) {
//        Logger.logForEach("visitLdcInsn", o)
        super.visitLdcInsn(o)
    }

    @Override
    public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
//        Logger.logForEach("visitLookupSwitchInsn", label, ints, labels)
        super.visitLookupSwitchInsn(label, ints, labels)
    }

    @Override
    public void visitMultiANewArrayInsn(String s, int i) {
//        Logger.logForEach("visitMultiANewArrayInsn", s, i)
        super.visitMultiANewArrayInsn(s, i)
    }

    @Override
    public void visitTableSwitchInsn(int i, int i1, Label label, Label[] labels) {
//        Logger.logForEach("visitTableSwitchInsn", i, i1, label, labels)
        super.visitTableSwitchInsn(i, i1, label, labels)
    }

    @Override
    public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
//        Logger.logForEach("visitTryCatchBlock", label, label1, label2, s)
        super.visitTryCatchBlock(label, label1, label2, s)
    }

    @Override
    public void visitTypeInsn(int opcode, String s) {
        Logger.logForEach("visitTypeInsn", Log.getOpName(opcode), s)
        super.visitTypeInsn(opcode, s)
    }

    @Override
    public void visitCode() {
//        Logger.logForEach("visitCode")
        super.visitCode()
    }

    @Override
    public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
//        Logger.logForEach("visitLocalVariable", s, s1, s2, label, label1, i)
        super.visitLocalVariable(s, s1, s2, label, label1, i)
    }

    @Override
    public void visitInsn(int opcode) {
        Logger.logForEach("visitInsn", Log.getOpName(opcode))
        super.visitInsn(opcode)
    }

    @Override
    AnnotationVisitor visitAnnotation(String s, boolean b) {
        Logger.logForEach("visitAnnotation", s)
        return super.visitAnnotation(s, b)
    }

    @Override
    protected void onMethodEnter() {
        Logger.logForEach("onMethodEnter")
        super.onMethodEnter()
    }

    @Override
    protected void onMethodExit(int opcode) {
        Logger.logForEach("visitInsn", Log.getOpName(opcode))
        super.onMethodExit(opcode)
    }
}