package com.xixi.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.xixi.plugin.bean.AutoClassFilter
import com.xixi.plugin.tracking.AppSettingParams
import com.xixi.plugin.tracking.AutoTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginEntry implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println ":applied XX哈哈"
        project.extensions.create('xiaoqingwa', AppSettingParams)
        Controller.setProject(project)


        //使用Transform实行遍历
        def android = project.extensions.getByType(AppExtension)
        registerTransform(android)

        project.afterEvaluate {
            println project.xiaoqingwa.name
            //初始化数据
            initData()
        }
    }

    def static registerTransform(BaseExtension android) {
        AutoTransform transform = new AutoTransform()
        android.registerTransform(transform)
    }

    static void initData() {
        Map<String, Object> matchData = Controller.getParams().matchData

        List<Map<String, Object>> paramsList = matchData.get("ClassFilter")
        AutoClassFilter classFilter = new AutoClassFilter()
        paramsList.each {
            Map<String, Object> map ->
                String className = map.get("ClassName")
                String innerClassName = map.get("InnerClassName")
                String interfaceName = map.get("InterfaceName")
                String methodName = map.get("MethodName")
                String superName = map.get("SuperName")
                classFilter.setClassName(className)
                classFilter.setInnerClassName(interfaceName)
                classFilter.setInterfaceName(interfaceName)
                classFilter.setMethodName(methodName)
                classFilter.setSuperName(superName)
                Controller.setClassFilter(classFilter)
                println '应用传递过来的数据' + className+innerClassName+interfaceName+methodName
        }
        //设置是否使用注解查找相关方法，是的话把指定过来条件去掉
        boolean isAnotation = matchData.get("isAnotation")
        Controller.setIsUseAnotation(isAnotation)
        if (isAnotation) {
            Controller.setClassFilter(null)
        }

        Closure methodVistor = matchData.get("MethodVisitor")
        Controller.setMethodVistor (methodVistor)
    }

}