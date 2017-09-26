package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class RegisterCodeActivity extends AppCompatActivity implements IRegisterView<RegisterBean>,TextWatcher,View.OnClickListener{

    public static final String TAG="RegisterCodeActivity";

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private EditText register_editText_phone;
    private LinearLayout register_linearLayout_back;
    private TextView register_textView_phoneError;
    private TextView register_textView_send;
    private RegisterPresenter presenter;
    private TextView dialogPhone;
    private TextView dialogCancel;
    private TextView dialogJustLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initView();
        initListener();
        bindPresenter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Register_LinearLayout_Back:
                finish();
                break;
            case R.id.Register_TextView_Send:
                if(TextUtils.isEmpty(register_editText_phone.getText().toString())){
                    IsUtils.showShort(this,"请输入手机号");
                }else {
                    if (IsUtils.validatePhoneNumber(register_editText_phone.getText().toString())){
                        HashMap<String,String> map=new HashMap<>();
                        map.put("phone",register_editText_phone.getText().toString());
                        presenter.post(map, RegisterBean.class);
                    }else {
                        register_textView_phoneError.setVisibility(View.VISIBLE);
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
            edit.putString("phoneEdit",register_editText_phone.getText().toString());
            edit.commit();
            Toast.makeText(this,"验证码发送成功",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterCheckActivity.class));
            Log.d(TAG, "onSuccess: "+registerBean.getMsg());
        }else if(registerBean.getStatus()==4 &&registerBean.getMsg().equals("该用户已注册")){
            IsUtils.showShort(this,"该用户已注册");
            jumpDialog();
        }
    }

    private void jumpDialog() {
        View dialogView=View.inflate(RegisterCodeActivity.this, R.layout.rc_dialog,null);

        dialogPhone = (TextView) dialogView.findViewById(R.id.RC_Dialog_TV_phone);
        dialogPhone.setText(register_editText_phone.getText().toString());

        dialogCancel = (TextView) dialogView.findViewById(R.id.RC_Dialog_TV_Cancel);
        dialogJustLogin = (TextView) dialogView.findViewById(R.id.RC_Dialog_TV_JustLogin);

        final AlertDialog builder=new AlertDialog.Builder(RegisterCodeActivity.this).create();
        builder.setView(dialogView);
        builder.show();

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        dialogJustLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterCodeActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    public void onError(RegisterBean registerBean) {
        if (registerBean.getStatus()!=1){
            Toast.makeText(this,"验证码发送失败",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: "+registerBean.getMsg());
        }
    }


    private void bindPresenter() {
        presenter = new RegisterPresenter();
        presenter.attach(this);
    }

    private void initListener() {
        register_linearLayout_back.setOnClickListener(this);
        register_textView_send.setOnClickListener(this);
        register_editText_phone.addTextChangedListener(this);
    }

    private void initView() {
        register_editText_phone = (EditText) findViewById(R.id.Register_EditText_Phone);
        register_linearLayout_back = (LinearLayout) findViewById(R.id.Register_LinearLayout_Back);
        register_textView_phoneError = (TextView) findViewById(R.id.Register_TextView_PhoneError);
        register_textView_send = (TextView) findViewById(R.id.Register_TextView_Send);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(register_editText_phone.getText().toString())){
            register_textView_phoneError.setVisibility(View.GONE);
        }
    }
}
