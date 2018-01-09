package com.xixi.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.xixi.plugin.bean.AutoClassFilter
import com.xixi.plugin.tracking.AppSettingParams
import com.xixi.plugin.tracking.AutoTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildFileMovePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println ":applied XX哈哈"
        project.extensions.create('xiaoqingwa', AppSettingParams)
        Util.setProject(project)
        Controller.setProject(project)


        //使用Transform实行遍历
        def android = project.extensions.getByType(AppExtension)
        registerTransform(android)

        initDir(project)

        project.afterEvaluate {
            println project.xiaoqingwa.name
            Log.setQuiet(false)
            //初始化数据
            initData()
        }
    }

    def static registerTransform(BaseExtension android) {
        AutoTransform transform = new AutoTransform()
        android.registerTransform(transform)
    }

    static void initDir(Project project) {
        if (!project.buildDir.exists()) {
            project.buildDir.mkdirs()
        }
        File hiBeaverDir = new File(project.buildDir, "HiBeaver")
        if (!hiBeaverDir.exists()) {
            hiBeaverDir.mkdir()
        }
        File tempDir = new File(hiBeaverDir, "temp")
        if (!tempDir.exists()) {
            tempDir.mkdir()
        }
        DataHelper.ext.hiBeaverDir = hiBeaverDir
        DataHelper.ext.hiBeaverTempDir = tempDir
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
                classFilter.setClassName(className)
                classFilter.setInnerClassName(interfaceName)
                classFilter.setInterfaceName(interfaceName)
                classFilter.setMethodName(methodName)
                Controller.setClassFilter(classFilter)
                println ":applied XX哈哈"
                println className+innerClassName+interfaceName+methodName
        }

        Closure methodVistor = matchData.get("MethodVisitor")
        Controller.setMethodVistor (methodVistor)
    }

}