package com.xishuang.plugintest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author:xishuang
 * Date:2018.03.01
 * Des:自动埋点帮助类
 */
public class AutoHelper {
    private static final String TAG = AutoHelper.class.getSimpleName();
    private static Context context = AutoApplication.getInstance().getApplicationContext();

    /**
     * 实现onClick点击时间的自动注入处理
     */
    public static void onClick(String methodName, View view) {
        String path = AutoUtil.getPath(context, view);
        String activityName = AutoUtil.getActivityName(view);
        path = activityName + ":" + methodName + ":" + path;
        Log.d(TAG, path);
    }


}
