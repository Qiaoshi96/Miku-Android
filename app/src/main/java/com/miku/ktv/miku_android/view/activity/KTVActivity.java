package com.miku.ktv.miku_android.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miku.ktv.miku_android.R;

public class KTVActivity extends AppCompatActivity  implements View.OnClickListener{

    private ImageView mIvback;
    private LinearLayout mLlPaimailist;
    private LinearLayout mLlDiangelist;
    private ImageView mIvmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktv);

        //初始化控件findViewById
        initView();
        //加载监听器
        initListener();
    }

    private void initListener() {
        mIvback.setOnClickListener(this);
        mLlDiangelist.setOnClickListener(this);
        mLlPaimailist.setOnClickListener(this);
        mIvmore.setOnClickListener(this);
    }
    private void initView() {
        //返回键的控件
        mIvback = (ImageView) findViewById(R.id.iv_back);
        //排麦的控件
        mLlPaimailist = (LinearLayout) findViewById(R.id.ll_paimailist);
        //点歌的控件
        mLlDiangelist = (LinearLayout) findViewById(R.id.ll_diangelist);
        //更多按钮
        mIvmore = (ImageView) findViewById(R.id.iv_more);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回键的点击事件
            case R.id.iv_back:
                finish();
                break;
            //排麦的点击事件
            case R.id.ll_diangelist:

                break;
            //点歌的点击事件
            case R.id.ll_paimailist:

                break;
            case R.id.iv_more:
                showDialog();
                break;

        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KTVActivity.this);
        builder.setMessage("告诉我们你需要的功能，我们会使这款产品更加完善哦~");
        builder.setTitle("你希望这里有什么功能");
        builder.setPositiveButton("反馈", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(KTVActivity.this,SuggestionsActivity.class);
                startActivity(intent);

            }

        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });

        builder.create().show();

    }
}

