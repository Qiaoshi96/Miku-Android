package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

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
        initView();
        initListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Personal_LinearLayout_Back:
                finish();
                break;
            case R.id.Personal_ImageView_Edit:

                break;
            case R.id.Personal_TextView_Sign:

                break;
            case R.id.Personal_Relative_FeedBack:
                startActivity(new Intent(this,SuggestActivity.class));
                break;
            case R.id.Personal_Relative_Settings:
                edit.clear();
                edit.commit();
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
//        Intent intent = getIntent();
//        if(intent !=null)
//        {
//            byte [] bis=intent.getByteArrayExtra("bitmap");
//            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
//            personal_imageView_head.setImageBitmap(bitmap);
//        }

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
