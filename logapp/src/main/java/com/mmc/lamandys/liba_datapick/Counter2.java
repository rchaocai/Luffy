package com.mmc.lamandys.liba_datapick;

import android.view.View;

import com.xishuang.annotation.AutoCount;

/**
 * Created by xishuang on 2018/1/6.
 */

public class Counter2 implements Counter3 {

    @AutoCount
    public void test() throws InterruptedException {
    }

    @AutoTrackDataViewOnClick
    public void test2(View view) throws InterruptedException {
    }

    @Override
    public void count() {

    }
}
