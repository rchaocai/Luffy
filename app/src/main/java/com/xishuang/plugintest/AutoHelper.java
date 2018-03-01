package com.xishuang.plugintest;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:xishuang
 * Date:2018.03.01
 * Des:自动埋点帮助类
 */
public class AutoHelper {
    private static final String TAG = AutoHelper.class.getSimpleName();
    private static Context context = AutoApplication.getInstance().getApplicationContext();

    public static void onClick(String methodName, View view) {
        //1. 构造ViewPath中于view对应的节点:ViewType[index]

        String path = getPath(methodName, view);
        Log.d(TAG, path);
    }

    /**
     * 构造view的全路径作为标识
     */
    private static String getPath(String methodName, View childView) {
        StringBuilder builder = new StringBuilder();
        String viewType = childView.getClass().getSimpleName();
        View parentView = childView;
        int index;
        // 遍历view获取父view来进行拼接
        do {
            index = ((ViewGroup) childView.getParent()).indexOfChild(childView);
            builder.insert(0, getResourceId(childView.getId()));
            builder.insert(0, "]");
            builder.insert(0, index);
            builder.insert(0, "[");
            builder.insert(0, viewType);

            parentView = (ViewGroup) parentView.getParent();
            viewType = parentView.getClass().getSimpleName();
            childView = parentView;
            builder.insert(0, "/");
        } while (parentView.getParent() instanceof View);

        builder.insert(0, getResourceId(childView.getId()));
        builder.insert(0, viewType);
        builder.insert(0, "->");
        builder.insert(0, methodName);
        return builder.toString();
    }

    /**
     * 通过资源id来获取xml中设置的id名字
     */
    private static String getResourceId(int viewId){
        String resourceName = "";
        try {
            resourceName = context.getResources().getResourceEntryName(viewId);
            resourceName = "#" + resourceName;
        } catch (Exception e) {
        }
        return resourceName;
    }
}
