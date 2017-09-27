package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.utils.IsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView home_lv;
    private ImageView home_imageView_menu;
    private ImageView home_imageView_add;
    private TextView dialog_textView_cancel;
    private TextView dialog_textView_ok;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private EditText dialog_editText;
    private ArrayList<Map<String, Object>> arr_data;
    private SimpleAdapter simp_ada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();

        initView();
        initData();
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Home_ImageView_Add:
                createRoom();
                break;
            case R.id.Home_ImageView_Menu:
                startActivity(new Intent(this,PersonalActivity.class));
                break;

            default:
                break;
        }
    }

    private void createRoom() {
        final AlertDialog builder=new AlertDialog.Builder(HomeActivity.this).create();
        View dialogView=View.inflate(HomeActivity.this, R.layout.home_dialog,null);
        builder.setView(dialogView);
        builder.show();

        dialog_editText = (EditText) dialogView.findViewById(R.id.Dialog_EditText);
        dialog_textView_cancel = (TextView) dialogView.findViewById(R.id.Dialog_TextView_Cancel);
        dialog_textView_ok = (TextView) dialogView.findViewById(R.id.Dialog_TextView_Ok);
        dialog_textView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        dialog_textView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dialog_editText.getText().toString())){
                    IsUtils.showShort(HomeActivity.this,"请输入房间名");
                }else {
                    editor.putString("roomname",dialog_editText.getText().toString());
                    editor.commit();
                    startActivity(new Intent(HomeActivity.this,KTVActivity.class));
                    IsUtils.showShort(HomeActivity.this,"创建房间成功");
                }

            }
        });
    }

    private void initListener() {
        home_imageView_menu.setOnClickListener(this);
        home_imageView_add.setOnClickListener(this);
    }

    private void initData() {
        //每一行数据就是一个Map，指定由Map组成的List，
        arr_data = new ArrayList<>();
        // 新增数据
        for (int i = 0; i < 5; i++) {
            Map map = new HashMap<>();
            //map放入两个键值对，键名与from对应，
            map.put("text", ""+i);
            //往list添加数据
            arr_data.add(map);
        }

        // 新建适配器 ，绑定数据
        String[] from = {"text"};
        int[] to = {R.id.Item_Home_TextView_Num };
        simp_ada = new SimpleAdapter(this, arr_data, R.layout.item_home_listview,from,to);
        // 加载适配器
        home_lv.setAdapter(simp_ada);
    }

    private void initView() {
        home_lv = (ListView) findViewById(R.id.Home_lv);
        home_imageView_menu = (ImageView) findViewById(R.id.Home_ImageView_Menu);
        home_imageView_add = (ImageView) findViewById(R.id.Home_ImageView_Add);

    }

}
