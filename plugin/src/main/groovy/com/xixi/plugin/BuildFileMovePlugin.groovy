package com.xixi.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.xixi.plugin.tracking.AutoTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildFileMovePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println ":applied XX哈哈"
        project.extensions.create('xiaoqingwa', HiBeaverParams)
        Util.setProject(project)
        try {
            if(Class.forName("com.android.build.gradle.BaseExtension")){
                BaseExtension android = project.extensions.getByType(BaseExtension)
                if (android instanceof LibraryExtension) {
                    DataHelper.ext.projectType = DataHelper.TYPE_LIB
                } else if (android instanceof AppExtension) {
                    DataHelper.ext.projectType = DataHelper.TYPE_APP
                } else {
                    DataHelper.ext.projectType = -1
                }
                if (DataHelper.ext.projectType != -1) {
                    registerTransform(android)
                }
            }
        } catch (Exception e) {
            DataHelper.ext.projectType = -1
        }

        initDir(project);

        project.afterEvaluate {
            println project.xiaoqingwa.name
            Log.setQuiet(false)
        }

        project.task('testTask') << {
            println "Hello XX哈哈 gradle plugin"
            ModifyFiles.modify()
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

}