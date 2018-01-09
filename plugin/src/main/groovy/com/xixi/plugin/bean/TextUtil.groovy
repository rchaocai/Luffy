package com.xixi.plugin.bean

public class TextUtil {
    public static boolean isEmpty(String text) {
        return text == null || text.trim().length() < 1
    }
}