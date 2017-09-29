package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;

/**
 * Created by 焦帆 on 2017/9/28.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView settings_imageView_back;
    private TextView settigs_textView_service;
    private TextView settigs_textView_logout;
    private TextView dialog_textView_cancel;
    private TextView dialog_textView_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initState();
        initView();
        initListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Settings_ImageView_Back:
                finish();
                break;
            case R.id.Settigs_TextView_Service:

                break;
            case R.id.Settigs_TextView_Logout:

                final AlertDialog builder=new AlertDialog.Builder(SettingsActivity.this).create();
                View dialogView=View.inflate(SettingsActivity.this, R.layout.settings_dialog,null);
                builder.setView(dialogView);
                builder.show();

                dialog_textView_cancel = (TextView) dialogView.findViewById(R.id.Dialog_TextView_Cancel);
                dialog_textView_ok = (TextView) dialogView.findViewById(R.id.Dialog_TextView_Ok);
                dialog_textView_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                dialog_textView_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                break;

            default:
                break;
        }
    }

    private void initListener() {
        settings_imageView_back.setOnClickListener(this);
        settigs_textView_service.setOnClickListener(this);
        settigs_textView_logout.setOnClickListener(this);
    }

    private void initView() {
        settings_imageView_back = (ImageView) findViewById(R.id.Settings_ImageView_Back);
        settigs_textView_service = (TextView) findViewById(R.id.Settigs_TextView_Service);
        settigs_textView_logout = (TextView) findViewById(R.id.Settigs_TextView_Logout);
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
