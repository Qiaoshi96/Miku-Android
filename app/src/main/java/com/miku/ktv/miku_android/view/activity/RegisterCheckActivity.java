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
import com.miku.ktv.miku_android.model.bean.CheckBean;
import com.miku.ktv.miku_android.model.bean.RegisterBean;
import com.miku.ktv.miku_android.model.utils.CountDownTimerUtil;
import com.miku.ktv.miku_android.presenter.RegisterPresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterCheckView;

import java.util.HashMap;

public class RegisterCheckActivity extends AppCompatActivity implements IRegisterCheckView<CheckBean, RegisterBean>, TextWatcher, View.OnClickListener {

    public static final String TAG = "RegisterCheckActivity";

    private EditText rc_edit1, rc_edit2, rc_edit3, rc_edit4, rc_edit5, rc_edit6;
    private TextView rc_sendAgain;
    private RegisterPresenter presenter;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private LinearLayout rc_back;
    private TextView rc_codeError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        initView();
        setTimer();
        initListener();
        bindPresenter();

    }

    private void setTimer() {
        CountDownTimerUtil timerUtil = new CountDownTimerUtil(rc_sendAgain, 60000, 1000);
        timerUtil.start();
    }

    private void bindPresenter() {
        presenter = new RegisterPresenter();
        presenter.attach(this);
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
            if (rc_edit1.isFocused()) {
                rc_edit1.clearFocus();
                rc_edit2.requestFocus();
            } else if (rc_edit2.isFocused()) {
                rc_edit2.clearFocus();
                rc_edit3.requestFocus();
            } else if (rc_edit3.isFocused()) {
                rc_edit3.clearFocus();
                rc_edit4.requestFocus();
            } else if (rc_edit4.isFocused()) {
                rc_edit4.clearFocus();
                rc_edit5.requestFocus();
            } else if (rc_edit5.isFocused()) {
                rc_edit5.clearFocus();
                rc_edit6.requestFocus();
            } else if (rc_edit6.isFocused()) {
                rc_edit6.clearFocus();

                //自动效验 验证码
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", sp.getString("phoneEdit", "null"));
                map.put("code", rc_edit1.getText().toString()
                        + rc_edit2.getText().toString()
                        + rc_edit3.getText().toString()
                        + rc_edit4.getText().toString()
                        + rc_edit5.getText().toString()
                        + rc_edit6.getText().toString()
                );
                presenter.get(map, CheckBean.class);
            }
        }
        if (TextUtils.isEmpty(rc_edit1.getText().toString())
          &&TextUtils.isEmpty(rc_edit2.getText().toString())
          &&TextUtils.isEmpty(rc_edit3.getText().toString())
          &&TextUtils.isEmpty(rc_edit4.getText().toString())
          &&TextUtils.isEmpty(rc_edit5.getText().toString())
          &&TextUtils.isEmpty(rc_edit6.getText().toString())
          ){
            rc_codeError.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RC_Back:
                finish();
                break;
            case R.id.RC_sendAgain:
                /////////////////////////////////////////////////////
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", sp.getString("phoneEdit", "null"));
                presenter.post(map, RegisterBean.class);
                break;

            default:
                break;
        }
    }

    private void initListener() {
        rc_edit1.addTextChangedListener(this);
        rc_edit2.addTextChangedListener(this);
        rc_edit3.addTextChangedListener(this);
        rc_edit4.addTextChangedListener(this);
        rc_edit5.addTextChangedListener(this);
        rc_edit6.addTextChangedListener(this);
        rc_sendAgain.setOnClickListener(this);
        rc_back.setOnClickListener(this);
    }

    private void initView() {
        rc_edit1 = (EditText) findViewById(R.id.RC_Edit1);
        rc_edit2 = (EditText) findViewById(R.id.RC_Edit2);
        rc_edit3 = (EditText) findViewById(R.id.RC_Edit3);
        rc_edit4 = (EditText) findViewById(R.id.RC_Edit4);
        rc_edit5 = (EditText) findViewById(R.id.RC_Edit5);
        rc_edit6 = (EditText) findViewById(R.id.RC_Edit6);
        rc_sendAgain = (TextView) findViewById(R.id.RC_sendAgain);
        rc_back = (LinearLayout) findViewById(R.id.RC_Back);
        rc_codeError = (TextView) findViewById(R.id.RC_CodeError);
    }

    @Override
    public void onSuccess(CheckBean checkBean) {
        if (checkBean.getStatus() == 1) {
            Toast.makeText(this, "验证码验证成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterSettingActivity.class));
            Log.d(TAG, "onSuccess: " + checkBean.getMsg());
        }else {
            Toast.makeText(this, "验证码验证失败", Toast.LENGTH_SHORT).show();
            rc_codeError.setVisibility(View.VISIBLE);
            Log.d(TAG, "onError: " + checkBean.getMsg());
        }
    }

    @Override
    public void onError(CheckBean checkBean) {
    }

    @Override
    public void onSendVetifyCodeSuccess(RegisterBean bean) {
        Log.d(TAG, "onSendAgainSuccess: " + bean.getMsg());
    }

    @Override
    public void onSendVetifyCodeError(Throwable throwable) {
        throwable.printStackTrace();
        Log.d(TAG, "onSendAgainError: ");
    }
}