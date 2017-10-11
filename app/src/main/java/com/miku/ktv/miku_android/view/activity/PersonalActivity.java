package com.miku.ktv.miku_android.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.utils.Constant;

public class PersonalActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "PersonalActivity";
    private static final int WRITE_PERMISSION = 0x01;
    private LinearLayout personal_linearLayout_back;
    private ImageView personal_imageView_edit;
    private ImageView personal_imageView_head;
    private TextView personal_textView_nick;
    private TextView personal_textView_id;
    private TextView personal_textView_sign;
    private RelativeLayout personal_relative_feedBack;
    private RelativeLayout personal_relative_settings;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initState();
        requestWritePermission();
        initView();
        initListener();
        personal_textView_nick.setText(sp.getString("nickEdit",""));
        personal_textView_sign.setText(sp.getString("signEdit",""));
        personal_textView_nick.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Personal_LinearLayout_Back:
                finish();
                break;
            case R.id.Personal_ImageView_Edit:
                startActivity(new Intent(this,EditActivity.class));
                break;
            case R.id.Personal_TextView_Sign:
                startActivity(new Intent(this,EditSignActivity.class));
                break;
            case R.id.Personal_Relative_FeedBack:
                startActivity(new Intent(this,SuggestActivity.class));
                break;
            case R.id.Personal_Relative_Settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;

            default:
                break;
        }
    }

    private void initListener() {
        personal_linearLayout_back.setOnClickListener(this);
        personal_imageView_edit.setOnClickListener(this);
        personal_textView_sign.setOnClickListener(this);
        personal_relative_feedBack.setOnClickListener(this);
        personal_relative_settings.setOnClickListener(this);
    }

    private void initView() {
        personal_linearLayout_back = (LinearLayout) findViewById(R.id.Personal_LinearLayout_Back);
        personal_imageView_edit = (ImageView) findViewById(R.id.Personal_ImageView_Edit);
        personal_imageView_head = (ImageView) findViewById(R.id.Personal_ImageView_Head);
        personal_textView_nick = (TextView) findViewById(R.id.Personal_TextView_Nick);
        personal_textView_id = (TextView) findViewById(R.id.Personal_TextView_ID);
        personal_textView_sign = (TextView) findViewById(R.id.Personal_TextView_Sign);
        personal_relative_feedBack = (RelativeLayout) findViewById(R.id.Personal_Relative_FeedBack);
        personal_relative_settings = (RelativeLayout) findViewById(R.id.Personal_Relative_Settings);

        personal_textView_nick.setText(sp.getString("nick","null"));
        personal_textView_id.setText(sp.getString("id","null"));

        Log.d(TAG, "initView: "+Constant.BASE_PIC_URL+sp.getString("avatar",""));
        String s = Constant.BASE_PIC_URL + sp.getString("avatar", "");
        Glide.with(this)
                .load(s)
                .placeholder(R.mipmap.bg9)
                .error(R.mipmap.bg9)
                .into(personal_imageView_head);

    }

    private void requestWritePermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION);
        }
    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
