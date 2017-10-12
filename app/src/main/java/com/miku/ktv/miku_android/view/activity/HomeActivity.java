package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.JoinRoomBean;
import com.miku.ktv.miku_android.model.bean.RoomsBean;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.CreateRoomPresenter;
import com.miku.ktv.miku_android.view.adapter.RoomsAdapter;
import com.miku.ktv.miku_android.view.iview.IJoinRoomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.miku.ktv.miku_android.model.utils.Constant.gson;

public class HomeActivity extends Activity implements IJoinRoomView<RoomsBean,JoinRoomBean>,View.OnClickListener {
    public static final String TAG="HomeActivity";

    private ListView home_lv;
    private ImageView home_imageView_menu;
    private ImageView home_imageView_add;
    private TextView dialog_textView_cancel;
    private TextView dialog_textView_ok;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private EditText dialog_editText;
    private CreateRoomPresenter roomPresenter;
    private List<RoomsBean.BodyBean.RoomListBean> list=new ArrayList<>();
    private RoomsAdapter roomsAdapter;
    private RoomsBean roomsBean1;

    private String mRoomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initState();
        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();
        binPresenter();
        initView();
        initListener();

    }

    private void binPresenter() {
        roomPresenter = new CreateRoomPresenter();
        roomPresenter.attach(this);
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

                    builder.dismiss();
                    IsUtils.showShort(HomeActivity.this,"创建房间成功");
                }
            }
        });
    }

    @Override
    public void onSuccess(RoomsBean roomsBean) {
        if (roomsBean.getStatus()==1){
            IsUtils.showShort(this,"请求房间列表成功");

            String roomsJson = gson.toJson(roomsBean);
            roomsBean1 = gson.fromJson(roomsJson, RoomsBean.class);
            list=roomsBean1.getBody().getRoom_list();


            initData();
            Log.e(TAG,"onSuccess: "+roomsBean.getMsg());
        }
    }

    @Override
    public void onError(RoomsBean roomsBean) {
        if (roomsBean.getStatus()!=1){
            Log.e(TAG,"onError: "+roomsBean.getMsg());
        }
    }


    private void initListener() {
        home_imageView_menu.setOnClickListener(this);
        home_imageView_add.setOnClickListener(this);
    }

    private void initData() {
        roomsAdapter = new RoomsAdapter(HomeActivity.this,list);
        home_lv.setAdapter(roomsAdapter);

        home_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRoomName = roomsBean1.getBody().getRoom_list().get(position).getRoom_id();
                editor.putString("roomname",mRoomName);
                editor.commit();
                Log.e(TAG, "onItemClick:  ---  "+mRoomName);
                //请求加入聊天室接口
                HashMap<String,String> map=new HashMap<>();
                map.put("token",sp.getString("LoginToken",""));
                roomPresenter.getRoom_id(mRoomName, map, JoinRoomBean.class);
            }
        });
    }

    private void initView() {
        home_lv = (ListView) findViewById(R.id.Home_lv);
        home_imageView_menu = (ImageView) findViewById(R.id.Home_ImageView_Menu);
        home_imageView_add = (ImageView) findViewById(R.id.Home_ImageView_Add);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> map=new HashMap<>();
                map.put("token",sp.getString("tokenKey","null"));
                map.put("page","1");
                roomPresenter.getRooms(map,RoomsBean.class);
            }
        });
        thread.start();
    }

    @Override
    public void onJoinSuccess(JoinRoomBean bean) {
        if (bean.getStatus()==1){
            Log.e(TAG,"onJoinSuccess: "+bean.getBody().getParticipants().get(0).getNick() + mRoomName);
            startActivity(new Intent(this,KTVActivity.class));
            IsUtils.showShort(this,"加入聊天室成功");
            Intent intent=new Intent(HomeActivity.this, KTVActivity.class);
            intent.putExtra("roomName", mRoomName);
            //关闭之前所有Activity
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            IsUtils.showShort(this,"加入聊天室失败");
        }
    }

    @Override
    public void onJoinError(Throwable t) {

    }
}
