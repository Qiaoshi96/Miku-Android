package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.utils.IsUtils;

public class EditSignActivity extends Activity implements View.OnClickListener {

    private ImageView es_imageView_back;
    private TextView es_textView_save;
    private EditText es_editText_sign;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sign);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initState();
        initView();
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ES_ImageView_Back:
                finish();
                break;
            case R.id.ES_TextView_Save:
                if (TextUtils.isEmpty(es_editText_sign.getText().toString())){
                    IsUtils.showShort(this,"请输入个性签名");
                }else {
                    edit.putString("signEdit",es_editText_sign.getText().toString());
                    edit.commit();
                    startActivity(new Intent(this,EditActivity.class));
                    finish();
                }
                break;

            default:
                break;
        }
    }

    private void initListener() {
        es_imageView_back.setOnClickListener(this);
        es_editText_sign.setOnClickListener(this);
        es_textView_save.setOnClickListener(this);
    }

    private void initView() {
        es_imageView_back = (ImageView) findViewById(R.id.ES_ImageView_Back);
        es_textView_save = (TextView) findViewById(R.id.ES_TextView_Save);
        es_editText_sign = (EditText) findViewById(R.id.ES_EditText_Sign);
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