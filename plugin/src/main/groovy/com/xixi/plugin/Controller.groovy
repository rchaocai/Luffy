package com.xixi.plugin

import com.xixi.plugin.bean.AutoClassFilter
import com.xixi.plugin.tracking.AppSettingParams
import org.gradle.api.Project

public class Controller {
    private static Project project
    private static AutoClassFilter autoClassFilter
    private static Closure methodVisitor

    public static void setProject(Project project) {
        Controller.@project = project
    }

    public static Project getProject() {
        return project
    }

    static AppSettingParams getParams(){
        return project.xiaoqingwa
    }

    static void setClassFilter(AutoClassFilter filter){
        autoClassFilter = filter
    }

    static AutoClassFilter getClassFilter(){
        return autoClassFilter
    }

    static String getClassName(){
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getClassName()
    }

    static String getInnerClassName(){
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getInnerClassName()
    }

    static String getInterfaceName(){
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getInterfaceName()
    }

    static String getMethodName(){
        if (autoClassFilter == null) {
            return ""
        }
        return autoClassFilter.getMethodName()
    }

    static void setMethodVistor(Closure visitor){
       methodVisitor = visitor
    }

    static Closure getAppMethodVistor(){
        return methodVisitor
    }
}