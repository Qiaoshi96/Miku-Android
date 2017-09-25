package com.miku.ktv.miku_android.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;

public class RegisterSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout rs_linearLayout_head;
    private EditText rs_editText_nick;
    private RadioButton rs_radioButton_man;
    private RadioButton rs_radioButton_woMan;
    private TextView rs_textView_register;
    private RadioGroup rs_radioGroup;
    private TextView rs_textView_man;
    private TextView rs_textView_woMan;
    private LinearLayout rs_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_setting);
        initView();
        initRadio();
    }

    private void initRadio() {
        rs_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.RS_RadioButton_Man){
                    rs_textView_man.setText("男");
                }else {
                    rs_textView_woMan.setText("女");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RS_Back:
                finish();
                break;
            case R.id.RS_TextView_Register:

                break;

            default:
                break;
        }
    }

    private void initView() {
        rs_linearLayout_head = (LinearLayout) findViewById(R.id.RS_LinearLayout_Head);
        rs_editText_nick = (EditText) findViewById(R.id.RS_EditText_Nick);
        rs_back = (LinearLayout) findViewById(R.id.RS_Back);

        rs_textView_man = (TextView) findViewById(R.id.RS_TextView_Man);
        rs_radioButton_man = (RadioButton) findViewById(R.id.RS_RadioButton_Man);
        rs_textView_woMan = (TextView) findViewById(R.id.RS_TextView_WoMan);
        rs_radioButton_woMan = (RadioButton) findViewById(R.id.RS_RadioButton_WoMan);

        rs_radioGroup = (RadioGroup) findViewById(R.id.RS_RadioGroup);
        rs_textView_register = (TextView) findViewById(R.id.RS_TextView_Register);

        rs_linearLayout_head.setOnClickListener(this);
        rs_textView_register.setOnClickListener(this);
    }
}
