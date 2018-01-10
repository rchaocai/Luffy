package com.xixi.plugin.bean

public class TextUtil {
    static boolean isEmpty(String text) {
        return text == null || text.trim().length() < 1
    }

    static String path2ClassName(String entryName) {
        entryName.replace(File.separator, ".").replace(".class", "")
    }
}