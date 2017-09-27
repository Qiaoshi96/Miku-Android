package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.LoginCodeBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.LoginCodePresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterView;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements IRegisterView<LoginCodeBean>,TextWatcher,View.OnClickListener {

    public static final String TAG="LoginActivity";
    private TextView login_textView_send;
    private EditText login_editText_phone;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private TextView login_textView_phoneError;
    private LinearLayout login_linearLayout_back;
    private LoginCodePresenter loginCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();

        initView();
        initListener();
        bindPresenter();
    }

    private void bindPresenter() {
        loginCodePresenter = new LoginCodePresenter();
        loginCodePresenter.attach(this);
    }

    private void initListener() {
        login_textView_send.setOnClickListener(this);
        login_linearLayout_back.setOnClickListener(this);
        login_editText_phone.addTextChangedListener(this);
    }

    private void initView() {
        login_editText_phone = (EditText) findViewById(R.id.Login_EditText_Phone);
        login_textView_send = (TextView) findViewById(R.id.Login_TextView_Send);
        login_textView_phoneError = (TextView) findViewById(R.id.Login_TextView_PhoneError);
        login_linearLayout_back = (LinearLayout) findViewById(R.id.Login_LinearLayout_Back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login_LinearLayout_Back:
                finish();
                break;
            case R.id.Login_TextView_Send:
                if(TextUtils.isEmpty(login_editText_phone.getText().toString())){
                    IsUtils.showShort(this,"请输入手机号");
                }else {
                    if (IsUtils.validatePhoneNumber(login_editText_phone.getText().toString())){
                        HashMap<String,String> map=new HashMap<>();
                        map.put("phone",login_editText_phone.getText().toString());
                        loginCodePresenter.getSms_login(map, LoginCodeBean.class);
                    }else {
                        login_textView_phoneError.setVisibility(View.VISIBLE);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(LoginCodeBean loginCodeBean) {
        if (loginCodeBean.getStatus()==7 && loginCodeBean.getMsg().equals("user not exist")){
            IsUtils.showShort(this,"该手机号还没注册过");
        }else {
           if (loginCodeBean.getStatus()==1){
               edit.putString("loginPhoneEdit",login_editText_phone.getText().toString());
               edit.commit();
               IsUtils.showShort(this,"验证码发送成功");
               startActivity(new Intent(this,LoginCheckActivity.class));
           }
        }
    }

    @Override
    public void onError(LoginCodeBean loginCodeBean) {
        if (loginCodeBean.getStatus()!=1){
            IsUtils.showShort(this,"验证码发送失败");
            Log.e(TAG, "onError: "+loginCodeBean.getMsg());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(login_editText_phone.getText().toString())){
            login_textView_phoneError.setVisibility(View.GONE);
        }
    }
}
