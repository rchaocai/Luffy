package com.xixi.plugin

import com.xixi.plugin.bean.AutoClassFilter
import com.xixi.plugin.tracking.AppSettingParams
import org.gradle.api.Project

public class Controller {
    private static Project project
    private static AutoClassFilter autoClassFilter
    private static Closure methodVisitor
    private static boolean isUseAnotation

    public static void setProject(Project project) {
        Controller.@project = project
    }

    public static Project getProject() {
        return project
    }

    static void setIsUseAnotation(boolean isAnotation) {
        isUseAnotation = isAnotation
    }

    static boolean isUseAnotation(){
        return isUseAnotation
    }

    static AppSettingParams getParams() {
        return project.xiaoqingwa
    }

    static void setClassFilter(AutoClassFilter filter) {
        autoClassFilter = filter
    }

    static AutoClassFilter getClassFilter() {
        return autoClassFilter
    }
    /**
     * 需要满足的类名
     */
    static String getClassName() {
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getClassName()
    }
    /**
     * 需要满足的内部类
     */
    static String getInnerClassName() {
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getInnerClassName()
    }
    /**
     * 需要满足的实现接口
     */
    static String getInterfaceName() {
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getInterfaceName()
    }
    /**
    * 需要满足的方法名
    */
    static String getMethodName() {
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getMethodName()
    }
    /**
     * 需要满足继承的类
     */
    static String getSuperName() {
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getSuperName()
    }

    static void setMethodVistor(Closure visitor) {
        methodVisitor = visitor
    }

    static Closure getAppMethodVistor() {
        return methodVisitor
    }
}