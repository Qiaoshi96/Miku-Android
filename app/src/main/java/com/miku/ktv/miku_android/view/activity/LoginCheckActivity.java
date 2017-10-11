package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.LoginCheckBean;
import com.miku.ktv.miku_android.model.bean.RegisterBean;
import com.miku.ktv.miku_android.model.utils.CountDownTimerUtil;
import com.miku.ktv.miku_android.presenter.LoginCodePresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterCheckView;

import java.util.HashMap;

public class LoginCheckActivity extends Activity implements IRegisterCheckView<LoginCheckBean, RegisterBean>, TextWatcher, View.OnClickListener{

    public static final String TAG = "LoginCheckActivity";

    private EditText lc_edit1, lc_edit2, lc_edit3, lc_edit4, lc_edit5, lc_edit6;
    private TextView lc_sendAgain;
    private LinearLayout lc_back;
    private TextView lc_codeError;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private LoginCodePresenter loginCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_check);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit=sp.edit();
        initView();
        setTimer();
        initListener();
        bindPresenter();
    }

    private void bindPresenter() {
        loginCodePresenter = new LoginCodePresenter();
        loginCodePresenter.attach(this);
    }

    private void initListener() {
        lc_edit1.addTextChangedListener(this);
        lc_edit2.addTextChangedListener(this);
        lc_edit3.addTextChangedListener(this);
        lc_edit4.addTextChangedListener(this);
        lc_edit5.addTextChangedListener(this);
        lc_edit6.addTextChangedListener(this);
        lc_sendAgain.setOnClickListener(this);
        lc_back.setOnClickListener(this);
    }

    private void setTimer() {
        CountDownTimerUtil timerUtil = new CountDownTimerUtil(lc_sendAgain, 60000, 1000);
        timerUtil.start();
    }

    private void initView() {
        lc_edit1 = (EditText) findViewById(R.id.LC_Edit1);
        lc_edit2 = (EditText) findViewById(R.id.LC_Edit2);
        lc_edit3 = (EditText) findViewById(R.id.LC_Edit3);
        lc_edit4 = (EditText) findViewById(R.id.LC_Edit4);
        lc_edit5 = (EditText) findViewById(R.id.LC_Edit5);
        lc_edit6 = (EditText) findViewById(R.id.LC_Edit6);
        lc_sendAgain = (TextView) findViewById(R.id.LC_sendAgain);
        lc_back = (LinearLayout) findViewById(R.id.LC_Back);
        lc_codeError = (TextView) findViewById(R.id.LC_CodeError);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == 1) {
            if (lc_edit1.isFocused()) {
                lc_edit1.clearFocus();
                lc_edit2.requestFocus();
            } else if (lc_edit2.isFocused()) {
                lc_edit2.clearFocus();
                lc_edit3.requestFocus();
            } else if (lc_edit3.isFocused()) {
                lc_edit3.clearFocus();
                lc_edit4.requestFocus();
            } else if (lc_edit4.isFocused()) {
                lc_edit4.clearFocus();
                lc_edit5.requestFocus();
            } else if (lc_edit5.isFocused()) {
                lc_edit5.clearFocus();
                lc_edit6.requestFocus();
            } else if (lc_edit6.isFocused()) {
                lc_edit6.clearFocus();

                //自动效验 验证码
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", sp.getString("loginPhoneEdit", "null"));
                map.put("code", lc_edit1.getText().toString()
                        + lc_edit2.getText().toString()
                        + lc_edit3.getText().toString()
                        + lc_edit4.getText().toString()
                        + lc_edit5.getText().toString()
                        + lc_edit6.getText().toString()
                );
                loginCodePresenter.postSms_login(map,LoginCheckBean.class);
            }
        }

        if (TextUtils.isEmpty(lc_edit1.getText().toString())
                &&TextUtils.isEmpty(lc_edit2.getText().toString())
                &&TextUtils.isEmpty(lc_edit3.getText().toString())
                &&TextUtils.isEmpty(lc_edit4.getText().toString())
                &&TextUtils.isEmpty(lc_edit5.getText().toString())
                &&TextUtils.isEmpty(lc_edit6.getText().toString())
                ){
            lc_codeError.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LC_Back:
                finish();
                break;
            case R.id.LC_sendAgain:
                HashMap<String,String> map=new HashMap<>();
                map.put("phone",sp.getString("loginPhoneEdit","null"));
                loginCodePresenter.getSms_login(map,RegisterBean.class);
                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(LoginCheckBean loginCheckBean) {
        if (loginCheckBean.getStatus() == 1) {
            Toast.makeText(this, "验证码验证成功", Toast.LENGTH_SHORT).show();
            edit.putString("LoginToken",loginCheckBean.getBody().getToken());
            edit.putString("nick",loginCheckBean.getBody().getNick());
            edit.putString("id",loginCheckBean.getBody().getFullname());
            edit.putString("avatar",loginCheckBean.getBody().getAvatar());
            edit.commit();
            Intent intent=new Intent(this, HomeActivity.class);
            //关闭之前所有Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Log.d(TAG, "onSuccess: " + loginCheckBean.getMsg());

//            String path = Environment.getExternalStorageDirectory()+"/sina/weibo/weibo/img-5jyf.jpg";
//            File file = new File(path);
//            new RegisterInfoPresenter().postAvatar(loginCheckBean.getBody().getToken(), file, AvatarBean.class);

        }else {
            Toast.makeText(this, "验证码验证失败", Toast.LENGTH_SHORT).show();
            lc_codeError.setVisibility(View.VISIBLE);
            Log.d(TAG, "onError: " + loginCheckBean.getMsg());
        }
    }

    @Override
    public void onError(LoginCheckBean loginCheckBean) {

    }

    @Override
    public void onSendVetifyCodeSuccess(RegisterBean t) {
        Log.d(TAG, "onSendVetifyCodeSuccess: " + t.getMsg());

    }

    @Override
    public void onSendVetifyCodeError(Throwable throwable) {
        throwable.printStackTrace();
        Log.d(TAG, "onSendVetifyCodeError: " + throwable.toString());

    }
}
