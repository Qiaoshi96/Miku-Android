package com.miku.ktv.miku_android.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AvatarBean;
import com.miku.ktv.miku_android.model.bean.HeartBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.HeartPresenter;
import com.miku.ktv.miku_android.view.iview.IHeartView;

import java.util.HashMap;

public class PersonalActivity extends Activity implements IHeartView<HeartBean>, View.OnClickListener {
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
    private HeartPresenter heartPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        getWindow().setStatusBarColor(getResources().getColor(R.color.background));
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
//        initState();
        requestWritePermission();
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
                //奇数为请求码，偶数为结果码
                startActivityForResult(new Intent(this,EditActivity.class),5);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6) {
            switch (requestCode) {
                case 5:
                    if (data == null) {
                        return;
                    } else {
                        String newNickIntent=data.getExtras().getString("newNickIntent");
                        personal_textView_nick.setText(newNickIntent);
                    }
                    break;

                default:
                    break;
            }
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

        heartPresenter = new HeartPresenter();
        heartPresenter.attach(this);

        HashMap<String,String> map=new HashMap<>();
        map.put("token",sp.getString("LoginToken",""));
        heartPresenter.getHeart(map,HeartBean.class);

        //ID
        personal_textView_id.setText(sp.getString("FullnameMain",""));

        Log.d(TAG, "initView: "+Constant.BASE_PIC_URL+sp.getString("AvatarMain",""));
        //头像路径
        String s = Constant.BASE_PIC_URL + sp.getString("AvatarMain", "");
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

    @Override
    public void onSuccess(HeartBean heartBean) {
        if (heartBean.getStatus()==1){
            IsUtils.showShort(this,TAG+"请求成功");
            personal_textView_nick.setText(heartBean.getBody().getNick());
        }else {
            IsUtils.showShort(this,TAG+"请求失败");
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onAvatarSuccess(AvatarBean bean) {

    }

    @Override
    public void onAvatarError(Throwable t) {

    }
}
