package com.xixi.plugin.tracking

import org.apache.commons.io.IOUtils

import java.lang.reflect.Array

public class Logger {
    private static boolean isDebug = true

    /**
     * 设置是否打印日志
     */
    def static void setDebug(boolean isDebug) {
        this.isDebug = isDebug
    }

    def static boolean isDebug() {
        return isDebug
    }

    /**
     * 打印日志
     */
    def static info(Object msg) {
        if (!isDebug) return
        try {
            println "${msg}"
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def static logForEach(Object... msg) {
        if (!isDebug) return
        msg.each {
            Object m ->
                try {
                    if (m != null) {
                        if (m.class.isArray()) {
                            print "["
                            def length = Array.getLength(m);
                            if (length > 0) {
                                for (int i = 0; i < length; i++) {
                                    def get = Array.get(m, i);
                                    if (get != null) {
                                        print "${get}\t"
                                    } else {
                                        print "null\t"
                                    }
                                }
                            }
                            print "]\t"
                        } else {
                            print "${m}\t"
                        }
                    } else {
                        print "null\t"
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                }
        }
        println ""
    }


}