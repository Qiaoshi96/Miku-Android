package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.HeartBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.presenter.HeartPresenter;
import com.miku.ktv.miku_android.view.iview.IHeartView;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;

public class MainActivity extends Activity implements IHeartView<HeartBean>, View.OnClickListener {

    public static final String TAG="MainActivity";
    private LinearLayout main_linearLayout_phone,main_linearLayout_wechat,main_linearLayout_qq;
    private TextView main_textView_login;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private HeartPresenter heartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();
        heartPresenter = new HeartPresenter();
        heartPresenter.attach(this);
        //得到sign
        Constant.getSign();

        //验证登录token是否过期
        checkToken();
    }

    private void checkToken() {
        HashMap<String,String> map=new HashMap<>();
        map.put("token",sp.getString("LoginToken",""));
        heartPresenter.getHeart(map,HeartBean.class);
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

    @Override
    public void onSuccess(HeartBean heartBean) {
        if (heartBean.getStatus()==3 && heartBean.getMsg().equals("token 错误, 请重新登陆")){
            initView();
            initListener();
        }else {
            if (heartBean.getStatus()==1){
                AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class)
                        .login(new LoginInfo(heartBean.getBody().getFullname(), sp.getString("LoginToken","")));
                loginRequest.setCallback(new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        Log.i(TAG, "login success");
                        Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                        //关闭之前所有Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(int code) {
                        if (code == 302 || code == 404) {
                            Toast.makeText(MainActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        Log.e(TAG, "onException", exception);
                    }
                });
            }
        }
    }

    @Override
    public void onError(Throwable t) {

    }
}
