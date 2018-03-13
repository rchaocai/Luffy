package com.xishuang.plugintest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

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
    public static void onClick(View view) {
        String path = AutoUtil.getPath(context, view);
        String activityName = AutoUtil.getActivityName(view);
        path = activityName + ":onClick:" + path;
        Log.d(TAG, path);
    }

    /**
     * 实现onClick点击时间的自动注入处理
     */
    public static void onClick() {
        Log.d(TAG, "onClick()");
    }

    public static void onFragmentResume(Fragment fragment) {
        Log.d(TAG, "onFragmentResume" + fragment.getClass().getSimpleName());
    }

    public static void onFragmentPause(Fragment fragment) {
        Log.d(TAG, "onFragmentPause"  + fragment.getClass().getSimpleName());
    }

    public static void setFragmentUserVisibleHint(Fragment fragment, boolean isVisibleToUser) {
        Log.d(TAG, "setFragmentUserVisibleHint->" + isVisibleToUser + "->" + fragment.getClass().getSimpleName());
    }

    public static void onFragmentHiddenChanged(Fragment fragment, boolean hidden) {
        Log.d(TAG, "onFragmentHiddenChanged->" + hidden + "->" + fragment.getClass().getSimpleName());
    }
}
