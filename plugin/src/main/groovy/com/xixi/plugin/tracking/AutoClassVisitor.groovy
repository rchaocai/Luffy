package com.xixi.plugin.tracking

import com.xixi.plugin.Controller
import com.xixi.plugin.Log
import com.xixi.plugin.bean.TextUtil
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


public class AutoClassVisitor extends ClassVisitor {
    public boolean onlyVisit = false
    private boolean isLocationListener = false

    AutoClassVisitor(final ClassVisitor cv) {
        super(Opcodes.ASM4, cv)
    }

    @Override
    void visitEnd() {
//        if (isLocationListener) {
        Logger.logForEach('* visitEnd *')
//        }
        super.visitEnd()
    }

    @Override
    void visitAttribute(Attribute attribute) {
//        if (isLocationListener) {
        Logger.logForEach('* visitAttribute *', attribute, attribute.type, attribute.metaClass, attribute.metaPropertyValues, attribute.properties)
//        }
        super.visitAttribute(attribute)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//        if (isLocationListener) {
        Logger.logForEach('* visitAnnotation *', desc, visible)
//        }
        return super.visitAnnotation(desc, visible)
    }

    @Override
    void visitInnerClass(String name, String outerName, String innerName, int access) {
        // 内部类
//        if (name.contains("OnClickListener")) {
        isLocationListener = true
        Logger.logForEach('* visitInnerClass *', name, outerName, innerName, Log.accCode2String(access))
//        }
        super.visitInnerClass(name, outerName, innerName, access)
    }

    @Override
    void visitOuterClass(String owner, String name, String desc) {
//        if (isLocationListener) {
        Logger.logForEach('* visitOuterClass *', owner, name, desc);
//        }
        super.visitOuterClass(owner, name, desc)
    }

    @Override
    void visitSource(String source, String debug) {
//        if (isLocationListener) {
        Logger.logForEach('* visitSource *', source, debug)
//        }
        super.visitSource(source, debug)
    }

    @Override
    FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
//        if (isLocationListener) {
        Logger.logForEach('* visitField *', Log.accCode2String(access), name, desc, signature, value)
//        }
        return super.visitField(access, name, desc, signature, value)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String interfaceName = Controller.getInterfaceName()
        if (!TextUtil.isEmpty(interfaceName)) {
            interfaces.each {
                String inteface ->
                    if (inteface.contains(interfaceName)) {
                        Logger.logForEach('* visit *', Log.accCode2String(access), name, signature, superName, interfaces);
                        isLocationListener = true
                    }
            }
        }

        super.visit(version, access, name, signature, superName, interfaces)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions)
        MethodVisitor adapter = null
        String methodName = Controller.getMethodName()
        //字节码操作
        if (isLocationListener && name == "countTime") {
            Logger.logForEach('* visitMethod *', Log.accCode2String(access), name, desc, signature, exceptions)
            //调试插入字节码之后
            if (onlyVisit) {
                adapter = new AutoMethodVisitor(methodVisitor, access, name, desc)
                return adapter
            }
//            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
//                @Override
//                protected void onMethodEnter() {
//                    super.onMethodEnter()
//                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/xishuang/plugintest/MainActivity", "hookXM", "()V")
//                            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
//                            mv.visitLdcInsn("========start=========")
//                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
//                            mv.visitLdcInsn(name);
//                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
//                            mv.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/TimeCache", "setStartTime", "(Ljava/lang/String;J)V", false)
//                }
//
//                @Override
//                protected void onMethodExit(int opcode) {
//                    super.onMethodExit(opcode)
//                            mv.visitLdcInsn(name)
//                            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
//                            mv.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/TimeCache", "setEndTime", "(Ljava/lang/String;J)V", false)
//
//                            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
//                            mv.visitLdcInsn(name)
//                            mv.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/TimeCache", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false)
//                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
//
//                            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
//                            mv.visitLdcInsn("========end=========")
//                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
//                }
//            }
            //应用里头设置的
            Closure vivi = Controller.getParams().matchData.get("MethodVisitor")
            if (vivi != null) {
                adapter = vivi(methodVisitor, access, name, desc)
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace()
//                    adapter = null
//                }
            }
        }
        if (adapter != null) {
            return adapter
        }
        return methodVisitor
    }
}