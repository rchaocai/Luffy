package com.xixi.plugin.util;

import com.xixi.plugin.tracking.AutoMethodVisitor;

import org.objectweb.asm.MethodVisitor;

/**
 * Created by xishuang on 2018/3/7.
 */

public class ChoiceUtil {

    public static MethodVisitor getMethodVisitor(String className, final MethodVisitor methodVisitor, int access, String name, String desc) {
        MethodVisitor adapter = null;

        if (name.equals("onClick")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    super.onMethodEnter();
                    methodVisitor.visitVarInsn(ALOAD, 1);
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/AutoHelper", "onClick", "(Landroid/view/View;)V", false);
                }
            };
        } else if (name.equals("onResume") && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
                    methodVisitor.visitVarInsn(ALOAD, 0);
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/AutoHelper", "onFragmentResume", "(Landroid/support/v4/app/Fragment;)V", false);
                }
            };
        } else if (name.equals("onPause")  && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
                    methodVisitor.visitVarInsn(ALOAD, 0);
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/AutoHelper", "onFragmentPause", "(Landroid/support/v4/app/Fragment;)V", false);
                }
            };
        } else if (name.equals("setUserVisibleHint")  && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
                    methodVisitor.visitVarInsn(ALOAD, 0);
                    methodVisitor.visitVarInsn(ILOAD, 1);
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/AutoHelper", "setFragmentUserVisibleHint", "(Landroid/support/v4/app/Fragment;Z)V", false);
                }
            };
        } else if (name.equals("onHiddenChanged")  && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
                    methodVisitor.visitVarInsn(ALOAD, 0);
                    methodVisitor.visitVarInsn(ILOAD, 1);
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/xishuang/plugintest/AutoHelper", "onFragmentHiddenChanged", "(Landroid/support/v4/app/Fragment;Z)V", false);
                }
            };
        }

        return adapter;
    }

    public static boolean isCanModify(String name) {
        if (name.equals("onClick") || name.equals("onResume") || name.equals("onPause")
                || name.equals("setUserVisibleHint") || name.equals("onHiddenChanged")){
            return true;
        } else {
            return false;
        }
    }

}
