package com.mmc.lamandys.liba_datapick.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mmc.lamandys.liba_datapick.R;


/**
 * 测试ToolBar，RadioGroup和CompoundButton
 */
public class RadioActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private RadioGroup sex;

    private CheckBox eatBox;
    private CheckBox sleepBox;
    private CheckBox dotaBox;
    private SeekBar seekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);


        sex = (RadioGroup) findViewById(R.id.sex);
        // 方法一监听事件,通过获取点击的id来实例化并获取选中状态的RadioButton控件
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 获取选中的RadioButton的id
                int id = group.getCheckedRadioButtonId();
                // 通过id实例化选中的这个RadioButton
                RadioButton choise = (RadioButton) findViewById(id);
                // 获取这个RadioButton的text内容
                String output = choise.getText().toString();
                Toast.makeText(RadioActivity.this, "你的性别为：" + output, Toast.LENGTH_SHORT).show();
            }
        });


        eatBox = (CheckBox) findViewById(R.id.eatId);
        sleepBox = (CheckBox) findViewById(R.id.sleepId);
        dotaBox = (CheckBox) findViewById(R.id.dotaId);

        CheckBoxListener listener = new CheckBoxListener();//声明实例化 监听器对象
        eatBox.setOnCheckedChangeListener(listener);//设置setOn监听器CheckedChangeListener，参数是监听器对象
        sleepBox.setOnCheckedChangeListener(listener);//不论哪个控件被点击，都会调用onCheckedChanged()方法
        dotaBox.setOnCheckedChangeListener(listener);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(RadioActivity.this, "onRatingChanged:" + rating, Toast.LENGTH_SHORT).show();
            }
        });

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
    }


    private void openDialog() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_radio, null);
        final EditText originPasswordEt = (EditText) linearLayout.findViewById(R.id.origin_password);
        TextView forgetPassword = (TextView) linearLayout.findViewById(R.id.forget_password);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("输入密码")
                .setView(linearLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        dialog.show();
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RadioActivity.this, "忘记密码", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(RadioActivity.this, "onStopTrackingTouch:" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
        openDialog();
    }


    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.eatId) {//判断被选中的是哪个CheckBox多选按钮的ID
                System.out.println("eatBox");
            } else if (buttonView.getId() == R.id.sleepId) {
                System.out.println("sleepBox");
            } else if (buttonView.getId() == R.id.dotaId) {
                System.out.println("dotaBox");
            }

            if (isChecked) {//判断 选中状态 的 布尔值boolean
                System.out.println("checked");
            } else {
                System.out.println("unchecked");
            }
        }
    }
}
