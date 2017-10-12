package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;

public class SuggestActivity extends Activity implements View.OnClickListener {

    private ImageView suggest_imageView_back;
    private TextView suggest_textView_submit;
    private EditText suggest_editText_feedBack;
    private EditText suggest_editText_qQorWechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        initState();
        initView();
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Suggest_ImageView_Back:
                finish();
                break;
            case R.id.Suggest_TextView_Submit:

                break;

            default:
                break;
        }
    }

    private void initListener() {
        suggest_imageView_back.setOnClickListener(this);
        suggest_textView_submit.setOnClickListener(this);
    }

    private void initView() {
        suggest_imageView_back = (ImageView) findViewById(R.id.Suggest_ImageView_Back);
        suggest_textView_submit = (TextView) findViewById(R.id.Suggest_TextView_Submit);
        suggest_editText_feedBack = (EditText) findViewById(R.id.Suggest_EditText_FeedBack);
        suggest_editText_qQorWechat = (EditText) findViewById(R.id.Suggest_EditText_QQorWechat);
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
