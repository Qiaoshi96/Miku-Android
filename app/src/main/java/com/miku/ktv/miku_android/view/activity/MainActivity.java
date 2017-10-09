package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.utils.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG="MainActivity";
    private LinearLayout main_linearLayout_phone,main_linearLayout_wechat,main_linearLayout_qq;
    private TextView main_textView_login;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();
        //得到sign
        Constant.getSign();
//        if (sp.getString("LoginToken","null")!=null){
//            startActivity(new Intent(this,HomeActivity.class));
//            finish();
//        }else {
            initView();
            initListener();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Main_LinearLayout_Phone:
                startActivity(new Intent(this,RegisterCodeActivity.class));
                break;
            case R.id.Main_LinearLayout_Wechat:
                break;
            case R.id.Main_LinearLayout_QQ:
                break;
            case R.id.Main_TextView_Login:
                startActivity(new Intent(this,LoginActivity.class));
                break;

            default:
                break;
        }
    }

    private void initListener() {
        main_linearLayout_phone.setOnClickListener(this);
        main_linearLayout_wechat.setOnClickListener(this);
        main_linearLayout_qq.setOnClickListener(this);
        main_textView_login.setOnClickListener(this);
    }

    private void initView() {
        main_linearLayout_phone = (LinearLayout) findViewById(R.id.Main_LinearLayout_Phone);
        main_linearLayout_wechat = (LinearLayout) findViewById(R.id.Main_LinearLayout_Wechat);
        main_linearLayout_qq = (LinearLayout) findViewById(R.id.Main_LinearLayout_QQ);
        main_textView_login = (TextView) findViewById(R.id.Main_TextView_Login);
    }
}
