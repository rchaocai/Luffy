package com.mmc.lamandys.liba_datapick;

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
     * 实现onClick点击事件的自动注入处理
     */
    public static void onClick(View view) {
        String path = AutoUtil.getPath(context, view);
        String activityName = AutoUtil.getActivityName(view);
        path = activityName + ":onClick:" + path;
        Log.d(TAG, path);
    }


    public static void onClick() {
        Log.d(TAG, "onClick()");
    }

    /**
     * 测试方法，测试注解方法的自动注入处理
     */
    public static void onClick2() {
        Log.d(TAG, "onClick2()");
    }

    public static void onClick3() {
        Log.d(TAG, "onClick3()");
    }

    public static void onFragmentResume(Fragment fragment) {
        Log.d(TAG, "onFragmentResume" + fragment.getClass().getSimpleName());
    }

    public static void onFragmentPause(Fragment fragment) {
        Log.d(TAG, "onFragmentPause" + fragment.getClass().getSimpleName());
    }

    public static void setFragmentUserVisibleHint(Fragment fragment, boolean isVisibleToUser) {
        Log.d(TAG, "setFragmentUserVisibleHint->" + isVisibleToUser + "->" + fragment.getClass().getSimpleName());
    }

    public static void onFragmentHiddenChanged(Fragment fragment, boolean hidden) {
        Log.d(TAG, "onFragmentHiddenChanged->" + hidden + "->" + fragment.getClass().getSimpleName());
    }
}
