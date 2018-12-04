package com.xixi.plugin.bean

/**
 * 用户自定义的功能
 */
class AutoClassFilter {
    String ClassName = ''
    String InterfaceName = ''
    String MethodName = ''
    String MethodDes = ''
    Closure MethodVisitor
    boolean isAnnotation = false
}