package com.xixi.plugin.bean

/**
 * 信息匹配用
 */
public class AutoClassFilter {
    String className
    String InnerClassName
    String InterfaceName
    String MethodName

    String getClassName() {
        return className
    }

    void setClassName(String className) {
        this.className = className
    }
}