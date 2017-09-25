package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.CheckBean;
import com.miku.ktv.miku_android.model.bean.RegisterBean;
import com.miku.ktv.miku_android.presenter.RegisterPresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterView;

import java.util.HashMap;

public class CheckCodeActivity extends AppCompatActivity implements IRegisterView<CheckBean>,TextWatcher{

    public static final String TAG="CheckCodeActivity";

    private EditText edit1,edit2,edit3,edit4,edit5,edit6;
    private TextView sendAgain;
    private RegisterPresenter presenter;
    private String e1;
    private String e2;
    private String e3;
    private String e4;
    private String e5;
    private String e6;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private String phoneEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        sp = getSharedPreferences("config", MODE_PRIVATE);

        //768352
        initView();
        initListener();
        presenter = new RegisterPresenter();
        presenter.attach(this);

        phoneEdit = sp.getString("phoneEdit",null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length() == 1) {
            if(edit1.isFocused()) {
                edit1.clearFocus();
                edit2.requestFocus();
            } else if(edit2.isFocused()) {
                edit2.clearFocus();
                edit3.requestFocus();
            } else if(edit3.isFocused()) {
                edit3.clearFocus();
                edit4.requestFocus();
            }
            else if(edit4.isFocused()) {
                edit4.clearFocus();
                edit5.requestFocus();
            }
            else if(edit5.isFocused()) {
                edit5.clearFocus();
                edit6.requestFocus();


                startActivity(new Intent(this,HomeActivity.class));

                HashMap<String,String> map=new HashMap<>();
                map.put("phone",phoneEdit);
                map.put("code",e1+e2+e3+e4+e5+e6);

                presenter.get(map, RegisterBean.class);
            }
        }
    }

    private void initListener() {
        edit1.addTextChangedListener(this);
        edit2.addTextChangedListener(this);
        edit3.addTextChangedListener(this);
        edit4.addTextChangedListener(this);
        edit5.addTextChangedListener(this);
        edit6.addTextChangedListener(this);
        sendAgain.addTextChangedListener(this);
    }

    private void initView() {
        edit1 = (EditText) findViewById(R.id.Edit1);
        edit2 = (EditText) findViewById(R.id.Edit2);
        edit3 = (EditText) findViewById(R.id.Edit3);
        edit4 = (EditText) findViewById(R.id.Edit4);
        edit5 = (EditText) findViewById(R.id.Edit5);
        edit6 = (EditText) findViewById(R.id.Edit6);
        sendAgain = (TextView) findViewById(R.id.sendAgain);

        e1 = edit1.getText().toString();
        e2 = edit2.getText().toString();
        e3 = edit3.getText().toString();
        e4 = edit4.getText().toString();
        e5 = edit5.getText().toString();
        e6 = edit6.getText().toString();
    }

    @Override
    public void onSuccess(CheckBean checkBean) {
        Toast.makeText(this,"验证码验证成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
