package com.xixi.plugin.util

import com.xixi.plugin.GlobalConfig
import com.xixi.plugin.bean.AutoClassFilter


/**
 * Author :xishuang
 * Date :2018-12-04
 * Des :目标匹配
 */
public class AutoMatchUtil {

    /**
     * 是否对扫描类进行修改
     *
     * @param className 扫描到的类名
     * @param exclude 过滤掉的类
     */
    static boolean isShouldModifyClass(String className) {
        if (className.contains('R$') ||
                className.contains('R2$') ||
                className.endsWith('R') ||
                className.endsWith('R2') ||
                className.endsWith('BuildConfig')) {
            return false
        }

        // 1、用户自定义的优先通过
        Iterator<String> includeIterator = GlobalConfig.include.iterator()
        while (includeIterator.hasNext()) {
            String packageName = includeIterator.next()

            if (className.startsWith(packageName)) {
                return true
            }
        }

        // 2、不允许通过的包，包括用户自定义的
        Iterator<String> iterator = GlobalConfig.exclude.iterator()
        while (iterator.hasNext()) {
            String packageName = iterator.next()
            if (className.startsWith(packageName)) {
                return false
            }
        }


        return true

    }

    /**
     * 是否修改用户自定义方法
     * 匹配规则有3种：
     *  1、有注解的话全部匹配
     *  2、类名+方法名+方法签名匹配
     *  3、接口名+方法名+方法签名匹配
     *
     * @param filter 用户自定义对象
     * @param className 扫描到的类名
     * @param methodName 扫描到的方法名
     * @param methodDesc 扫描到的方法签名
     * @param interfaces 扫描到的接口数组
     *
     */
    static boolean isShouldModifyCustomMethod(AutoClassFilter filter, String className, String methodName
                                              , String methodDesc, String[] interfaces) {

        // 1、自定义方法如果需要注解的话就得每个方法都得进行遍历操作
        if (filter.isAnnotation) {
            return true
        }

//        boolean isMatchClass = className.startsWith(filter.ClassName)
        boolean isMatchClass = filter.ClassName == className
        boolean isMatchMethod = filter.MethodName == methodName
        boolean isMatchMethodDes = filter.MethodDes == methodDesc

        boolean isMatchInteface
        interfaces.each {
            String inteface ->
                if (filter.InterfaceName == inteface)
                    isMatchInteface = true
        }

        // 前提:方法名和方法签名匹配
        // 2、类也得匹配
        // 3、接口也得匹配
        if ((isMatchClass || isMatchInteface) && isMatchMethod && isMatchMethodDes) {
            return true
        } else {
            return false
        }

    }
}
