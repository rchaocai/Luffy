package com.xixi.plugin.util

import com.xixi.plugin.Controller
import com.xixi.plugin.tracking.AutoMethodVisitor
import com.xixi.plugin.tracking.Logger
import org.objectweb.asm.MethodVisitor

/**
 * Author:xishuang
 * Date:2018.03.07
 * Des:工具类，是否进行方法修改判断
 */
class ChoiceUtil {

    /**
     * 获取自动埋点的方法修改器
     *
     * @param interfaces 实现的接口名
     * @param className 类名
     * @param methodVisitor 需要修改的方法
     * @param name 方法名
     * @param desc 参数描述符
     */
    static MethodVisitor getMethodVisitor(String[] interfaces, String className,
                                          MethodVisitor methodVisitor, int access, String name, String desc) {
        MethodVisitor adapter = null

        if (name == "onClick" && isMatchingInterfaces(interfaces, 'android/view/View$OnClickListener')) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    super.onMethodEnter()
                    // ALOAD 25
                    methodVisitor.visitVarInsn(25, 1)
                    // INVOKESTATIC 184
//                    methodVisitor.visitMethodInsn(184, "com/xishuang/plugintest/AutoHelper", "onClick", "(Landroid/view/View;)V", false)
                    methodVisitor.visitMethodInsn(184, "com/mmc/lamandys/liba_datapick/AutoHelper", "onClick", "(Landroid/view/View;)V", false)
                }
            }
        } else if (name == "onResume" && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode)
                    // ALOAD
                    methodVisitor.visitVarInsn(25, 0)
                    // INVOKESTATIC 184
//                    methodVisitor.visitMethodInsn(184, "com/xishuang/plugintest/AutoHelper", "onFragmentResume", "(Landroid/support/v4/app/Fragment;)V", false)
                    methodVisitor.visitMethodInsn(184, "com/mmc/lamandys/liba_datapick/AutoHelper", "onFragmentResume", "(Landroid/support/v4/app/Fragment;)V", false)
                }
            }
        } else if (name == "onPause" && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode)
                    // ALOAD 25
                    methodVisitor.visitVarInsn(25, 0)
                    // INVOKESTATIC 184
//                    methodVisitor.visitMethodInsn(184, "com/xishuang/plugintest/AutoHelper", "onFragmentPause", "(Landroid/support/v4/app/Fragment;)V", false)
                    methodVisitor.visitMethodInsn(184, "com/mmc/lamandys/liba_datapick/AutoHelper", "onFragmentPause", "(Landroid/support/v4/app/Fragment;)V", false)
                }
            }
        } else if (name == "setUserVisibleHint" && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode)
                    // ALOAD 25
                    methodVisitor.visitVarInsn(25, 0)
                    // ILOAD 21
                    methodVisitor.visitVarInsn(21, 1)
                    // INVOKESTATIC 184
//                    methodVisitor.visitMethodInsn(184, "com/xishuang/plugintest/AutoHelper", "setFragmentUserVisibleHint", "(Landroid/support/v4/app/Fragment;Z)V", false)
                    methodVisitor.visitMethodInsn(184, "com/mmc/lamandys/liba_datapick/AutoHelper", "setFragmentUserVisibleHint", "(Landroid/support/v4/app/Fragment;Z)V", false)
                }
            }
        } else if (name == "onHiddenChanged" && className.contains("Fragment")) {
            adapter = new AutoMethodVisitor(methodVisitor, access, name, desc) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode)
                    // ALOAD 25
                    methodVisitor.visitVarInsn(25, 0)
                    // ILOAD 21
                    methodVisitor.visitVarInsn(21, 1)
                    // INVOKESTATIC 184
//                    methodVisitor.visitMethodInsn(184, "com/xishuang/plugintest/AutoHelper", "onFragmentHiddenChanged", "(Landroid/support/v4/app/Fragment;Z)V", false)
                    methodVisitor.visitMethodInsn(184, "com/mmc/lamandys/liba_datapick/AutoHelper", "onFragmentHiddenChanged", "(Landroid/support/v4/app/Fragment;Z)V", false)
                }
            }
        } else if (Controller.isUseAnotation()) {
            // 注解的话，使用指定方法
            adapter = getSettingMethodVisitor(methodVisitor, access, name, desc)
        } else if (isMatchingSettingClass(className, interfaces)){
            adapter = getSettingMethodVisitor(methodVisitor, access, name, desc)
        }

        return adapter
    }

    /**
     * 类是否满足匹配条件，满足的才会允许修改其中的方法
     * 1、实现android/view/View$OnClickListener接口的类
     * 2、类名包含Fragment就当做是继承了Fragment的类
     * 3、在app的module中手动设置的监听条件：指定类名或实现接口
     *
     * @param className 类名
     * @param desc 类的实现接口
     */
    static boolean isMatchingClass(String className, String[] interfaces) {
        boolean isMeetClassCondition = false
        //剔除掉以android开头的类，即系统类，以避免出现不可预测的bug
        if (className.startsWith('android')) {
            return isMeetClassCondition
        }
        // 是否满足实现的接口
        isMeetClassCondition = isMatchingInterfaces(interfaces, 'android/view/View$OnClickListener')
        if (className.contains('Fragment')) {
            isMeetClassCondition = true
        } else if (isMatchingSettingClass(className, interfaces)) {
            isMeetClassCondition = true
        }
        return isMeetClassCondition
    }

    /**
     * 在app的module中设置的类与接口匹配
     *
     * @param className 类名
     * @param interfaces 类的实现接口
     */
    private static boolean isMatchingSettingClass(String className, String[] interfaces) {
        boolean isMeetClassCondition
        String appInterfaceName = Controller.getInterfaceName()
        String appClassName = Controller.getClassName()
        // 是否满足实现的接口
        isMeetClassCondition = isMatchingInterfaces(interfaces, appInterfaceName)
        // 类名是否满足
        if (className == appClassName) {
            isMeetClassCondition = true
        }
        // 是否使用注解
        if (Controller.isUseAnotation()) {
            isMeetClassCondition = true
        }
        return isMeetClassCondition
    }

    /**
     * 方法是否匹配到，根据方法名和参数的描述符来确定一个方法是否需要修改的初步条件，
     * 具体要怎么修改要在{@link ChoiceUtil#getMethodVisitor}中确定：
     * 1、View的onClick(View v)方法
     * 2、Fragment的onResume()方法
     * 3、Fragment的onPause()方法
     * 4、Fragment的setUserVisibleHint(boolean b)方法
     * 5、Fragment的onHiddenChanged(boolean b)方法
     * 6、在app的module中手动设置的监听条件：指定方法或注解方法
     * 可以扩展自己想监听的方法
     *
     * @param name 方法名
     * @param desc 参数的方法的描述符
     */
    static boolean isMatchingMethod(String name, String desc) {
        if ((name == 'onClick' && desc == '(Landroid/view/View;)V')
                || (name == 'onResume' && desc == '()V')
                || (name == 'onPause' && desc == '()V')
                || (name == 'setUserVisibleHint' && desc == '(Z)V')
                || (name == 'onHiddenChanged' && desc == '(Z)V')
                || isMatchingSettingMethod(name, desc)) {
            return true
        } else {
            return false
        }
    }

    /**
     * 在app的module中设置的方法匹配
     *
     * @param name 方法名
     * @param desc 参数的方法的描述符
     */
    private static boolean isMatchingSettingMethod(String name, String desc) {
        String appMethodName = Controller.getMethodName()
        String appMethodDes = Controller.getMethodDes()
        if (name == appMethodName && desc == appMethodDes) {
            return true
        } else if (Controller.isUseAnotation()) {
            //使用注解的方式，直接就方法匹配，因为注解的方法hook是自己在app module中
            //控制的
            return true
        }
        return false
    }

    /**
     * 接口名是否匹配
     *
     * @param interfaces 类的实现接口
     * @param interfaceName 需要匹配的接口名
     */
    private static boolean isMatchingInterfaces(String[] interfaces, String interfaceName) {
        boolean isMatch = false
        // 是否满足实现的接口
        interfaces.each {
            String inteface ->
                if (inteface == interfaceName) {
                    isMatch = true
                }
        }
        return isMatch
    }

    /**
     * app的module里头设置的自动埋点方法修改器
     *
     * @param className 类名
     * @param methodVisitor 需要修改的方法
     * @param name 方法名
     * @param desc 参数描述符
     */
    private static MethodVisitor getSettingMethodVisitor(
            MethodVisitor methodVisitor, int access, String name, String desc) {
        MethodVisitor adapter = null
        Closure vivi = Controller.getAppMethodVistor()
        if (vivi != null) {
            try {
                adapter = vivi(methodVisitor, access, name, desc)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        return adapter
    }

}
