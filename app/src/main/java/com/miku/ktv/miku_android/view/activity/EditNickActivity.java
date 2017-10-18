package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.NickBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.EditPresenter;
import com.miku.ktv.miku_android.view.iview.IRegisterView;

import java.util.HashMap;

public class EditNickActivity extends Activity implements IRegisterView<NickBean>,View.OnClickListener {

    private ImageView en_imageView_back;
    private TextView en_textView_save;
    private EditText en_editText_nick;
    private EditPresenter editPresenter;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nick);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        initState();
        initView();
        initListener();
        bindPresenter();
    }

    private void bindPresenter() {
        editPresenter = new EditPresenter();
        editPresenter.attach(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.EN_ImageView_Back:
                finish();
                break;
            case R.id.EN_TextView_Save:
                if (TextUtils.isEmpty(en_editText_nick.getText().toString())){
                    IsUtils.showShort(this,"请输入昵称");
                }else {
                    //请求接口
                    HashMap<String,String> map=new HashMap<>();
                    map.put("token",sp.getString("LoginToken","null"));
                    map.put("nick",en_editText_nick.getText().toString());
                    map.put("fullname",sp.getString("id","null"));
                    editPresenter.postNick(map,NickBean.class);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(NickBean nickBean) {
        if (nickBean.getStatus()==1){
            Intent intent=new Intent(this,EditActivity.class);
//            intent.putExtra("nickIntent",nickBean.getBody().getNick());
            edit.putString("nickEdit",nickBean.getBody().getNick());
            edit.commit();
            startActivity(intent);
            finish();
            IsUtils.showShort(this,"昵称修改成功");
        }else {
            IsUtils.showShort(this,"昵称修改失败");
        }
    }

    @Override
    public void onError(Throwable t) {

    }

    private void initListener() {
        en_imageView_back.setOnClickListener(this);
        en_textView_save.setOnClickListener(this);
    }

    private void initView() {
        en_imageView_back = (ImageView) findViewById(R.id.EN_ImageView_Back);
        en_textView_save = (TextView) findViewById(R.id.EN_TextView_Save);
        en_editText_nick = (EditText) findViewById(R.id.EN_EditText_Nick);
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
