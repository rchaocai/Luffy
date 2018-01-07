package com.xishuang.plugintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Counter counter = new Counter();
        try {
            counter.test();
            Class cc = counter.getClass();
            System.out.println(cc.getField("timer").get(counter));
        } catch (Exception e) {
            Log.d("哈哈", e.toString());
        }
    }
}
