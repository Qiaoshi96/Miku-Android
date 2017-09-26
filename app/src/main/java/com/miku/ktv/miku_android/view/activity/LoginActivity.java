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
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.RegisterBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.RegisterPresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterView;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements IRegisterView<RegisterBean>,TextWatcher,View.OnClickListener {

    public static final String TAG="LoginActivity";
    private TextView login_textView_send;
    private EditText login_editText_phone;
    private RegisterPresenter presenter;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private TextView login_textView_phoneError;
    private LinearLayout login_linearLayout_back;

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
        presenter = new RegisterPresenter();
        presenter.attach(this);
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
                        presenter.post(map, RegisterBean.class);
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
    public void onSuccess(RegisterBean registerBean) {
        if (registerBean.getStatus()==1){
            edit.putString("phoneEdit",login_editText_phone.getText().toString());
            edit.commit();
            Toast.makeText(this,"验证码发送成功",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginCheckActivity.class));
            Log.d(TAG, "onSuccess: "+registerBean.getMsg());
        }
    }

    @Override
    public void onError(RegisterBean registerBean) {
        if (registerBean.getStatus()!=1){
            Toast.makeText(this,"验证码发送失败",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: "+registerBean.getMsg());
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
