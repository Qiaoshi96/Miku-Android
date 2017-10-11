package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.view.custom.CircleImage;

public class EditActivity extends Activity implements View.OnClickListener {

    private ImageView edit_imageView_back;
    private CircleImage edit_circle_head;
    private TextView edit_textView_nick;
    private TextView edit_textView_id;
    private RelativeLayout edit_relativeLayout_head;
    private RelativeLayout edit_relativeLayout_nick;
    private RelativeLayout edit_relativeLayout_sign;
    private TextView dialog_textView_camera;
    private TextView dialog_textView_album;
    private TextView dialog_textView_cancel;
    private Dialog bottomDialog;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initState();
        initView();
        initListener();
        edit_textView_nick.setText(sp.getString("nickEdit",""));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Edit_ImageView_Back:
                finish();
                break;
            case R.id.Edit_RelativeLayout_Head:
                //底部弹出dialog
                bottomDialog();
                break;
            case R.id.Edit_RelativeLayout_Nick:
                startActivity(new Intent(this,EditNickActivity.class));
                finish();
                break;
            case R.id.Edit_RelativeLayout_Sign:
                startActivity(new Intent(this,EditSignActivity.class));
                finish();
                break;

            default:
                break;
        }
    }

    private void bottomDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.edit_dialog, null);
        root.findViewById(R.id.Dialog_TextView_Cancel).setOnClickListener(btnlistener);
        root.findViewById(R.id.Dialog_TextView_Camera).setOnClickListener(btnlistener);
        root.findViewById(R.id.Dialog_TextView_Album).setOnClickListener(btnlistener);
        bottomDialog.setContentView(root);
        Window dialogWindow = bottomDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.y = 20;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                    //拍照
                case R.id.Dialog_TextView_Camera:
                    IsUtils.showShort(EditActivity.this,"拍照");
                    break;
                    //相册
                case R.id.Dialog_TextView_Album:
                    IsUtils.showShort(EditActivity.this,"相册");
                    break;
                    //取消
                case R.id.Dialog_TextView_Cancel:
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                    break;
            }
        }
    };

    private void initListener() {
        edit_imageView_back.setOnClickListener(this);
        edit_relativeLayout_head.setOnClickListener(this);
        edit_relativeLayout_nick.setOnClickListener(this);
        edit_relativeLayout_sign.setOnClickListener(this);
    }

    private void initView() {
        edit_imageView_back = (ImageView) findViewById(R.id.Edit_ImageView_Back);
        edit_circle_head = (CircleImage) findViewById(R.id.Edit_Circle_Head);
        edit_textView_nick = (TextView) findViewById(R.id.Edit_TextView_Nick);
        edit_textView_id = (TextView) findViewById(R.id.Edit_TextView_ID);
        edit_relativeLayout_head = (RelativeLayout) findViewById(R.id.Edit_RelativeLayout_Head);
        edit_relativeLayout_nick = (RelativeLayout) findViewById(R.id.Edit_RelativeLayout_Nick);
        edit_relativeLayout_sign = (RelativeLayout) findViewById(R.id.Edit_RelativeLayout_Sign);

        edit_textView_nick.setText(sp.getString("nick","null"));
        edit_textView_id.setText(sp.getString("id","null"));
        String s = Constant.BASE_PIC_URL + sp.getString("avatar", "");
        Glide.with(this)
                .load(s)
                .placeholder(R.mipmap.bg9)
                .error(R.mipmap.bg9)
                .into(edit_circle_head);
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
