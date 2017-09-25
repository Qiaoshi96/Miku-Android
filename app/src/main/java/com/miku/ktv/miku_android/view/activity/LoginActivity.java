package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.RegisterBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.RegisterPresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterView;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements IRegisterView<RegisterBean>,View.OnClickListener {

    public static final String TAG="LoginActivity";


    private TextView login_textView_send;
    private EditText login_editText_phone;
    private RegisterPresenter presenter;
    private String phone;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();

        login_textView_send = (TextView) findViewById(R.id.Login_TextView_Send);
        login_textView_send.setOnClickListener(this);
        login_editText_phone = (EditText) findViewById(R.id.Login_EditText_Phone);
        phone = login_editText_phone.getText().toString();

        presenter = new RegisterPresenter();
        presenter.attach(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login_TextView_Send:
                if(phone.length() != 0){
//                    if(!IsUtils.validatePhoneNumber(phone)){
                        HashMap<String,String> map=new HashMap<>();
                        map.put("phone",phone);
                        presenter.post(map, RegisterBean.class);
//                    }else {
//                        IsUtils.Tos(this,"手机号不正确");
//                    }
                }else {
                    IsUtils.Tos(this,"请输入手机号");
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(RegisterBean registerBean) {
        Toast.makeText(this,"验证码发送成功",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,CheckCodeActivity.class));

        edit.putString("phoneEdit",phone);
        edit.commit();

        Log.d(TAG, "onSuccess: "+registerBean.getMsg());
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(this,"验证码发送失败",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSuccess: "+throwable.toString());
    }
}
