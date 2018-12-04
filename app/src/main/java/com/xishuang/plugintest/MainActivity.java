package com.xishuang.plugintest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xishuang.annotation.AutoCount;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView view = (TextView) findViewById(R.id.test);
        findViewById(R.id.main_go).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        view.setOnClickListener(new View.OnClickListener() {
            @AutoCount
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我是文本", Toast.LENGTH_SHORT).show();
            }
        });
        context = this.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_go) {
            startActivity(new Intent(this, TabActivity.class));
        } else if (v.getId() == R.id.button1) {
//            Toast.makeText(MainActivity.this, "我是按钮1", Toast.LENGTH_SHORT).show();
//            Log.d("自动埋点", Thread.currentThread().getName());
        }
    }

    private void doSomething(){

    }
}
