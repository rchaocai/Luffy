package com.xixi.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.xixi.plugin.bean.AutoClassFilter
import com.xixi.plugin.util.AutoTextUtil
import com.xixi.plugin.bean.AutoSettingParams
import com.xixi.plugin.asm.AutoTransform
import com.xixi.plugin.util.Logger
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 插件入口
 */
class PluginEntry implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('xiaoqingwa', AutoSettingParams)
        GlobalConfig.setProject(project)

        //使用Transform实行遍历
        def android = project.extensions.getByType(AppExtension)
        registerTransform(android)

        project.afterEvaluate {
            Logger.setDebug(project.xiaoqingwa.isDebug)
            //初始化数据
            initData()
        }
    }

    def static registerTransform(BaseExtension android) {
        AutoTransform transform = new AutoTransform()
        android.registerTransform(transform)
    }

    /**
     * 对build.gradle配置参数及自定义内容进行解析
     */
    static void initData() {

        List<Map<String, Object>> matchDataList = GlobalConfig.getParams().matchData
        List<AutoClassFilter> autoClassFilterList = new ArrayList<>()

        matchDataList.each {
            Map<String, Object> map ->
                AutoClassFilter classFilter = new AutoClassFilter()

                String className = map.get("ClassName")
                String interfaceName = map.get("InterfaceName")
                String methodName = map.get("MethodName")
                String methodDes = map.get("MethodDes")
                Closure methodVisitor = map.get("MethodVisitor")
                boolean isAnnotation = map.get("isAnnotation")
                // 类的全类名
                if (!AutoTextUtil.isEmpty(className)) {
                    className = AutoTextUtil.changeClassNameSeparator(className)
                }
                // 接口的全类名
                if (!AutoTextUtil.isEmpty(interfaceName)) {
                    interfaceName = AutoTextUtil.changeClassNameSeparator(interfaceName)
                }

                classFilter.setClassName(className)
                classFilter.setInterfaceName(interfaceName)
                classFilter.setMethodName(methodName)
                classFilter.setMethodDes(methodDes)
                classFilter.setMethodVisitor(methodVisitor)
                classFilter.setIsAnnotation(isAnnotation)

                autoClassFilterList.add(classFilter)
        }

        GlobalConfig.setAutoClassFilter(autoClassFilterList)
    }

}