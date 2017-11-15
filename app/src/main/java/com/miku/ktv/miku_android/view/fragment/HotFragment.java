package com.miku.ktv.miku_android.view.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.main.GlobalInstance;
import com.miku.ktv.miku_android.model.bean.AddBean;
import com.miku.ktv.miku_android.model.bean.AddListBean;
import com.miku.ktv.miku_android.model.bean.DeleteBean;
import com.miku.ktv.miku_android.model.bean.SongsListBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.DbManager;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.AddPresenter;
import com.miku.ktv.miku_android.view.adapter.MySongsListAdapter;
import com.miku.ktv.miku_android.view.custom.RefreshListView;
import com.miku.ktv.miku_android.view.iview.IAddView;
import com.miku.ktv.miku_android.view.iview.OnRefreshListener;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.DownloadCallback;
import com.othershe.dutil.download.DownloadManger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 焦帆 on 2017/10/11.
 * 热门页面
 */

public class HotFragment extends Fragment implements IAddView<AddBean, DeleteBean, AddListBean>, OnRefreshListener {
    public static final String TAG = "HotFragment";
    public static final String REQUEST_SONGS_URL = "http://ktv.fibar.cn/api/v1/songs";

    private View inflateView;
    private RefreshListView refreshLV;
    private Gson gson;
    private SongsListBean songsListBean;
    private List<SongsListBean.BodyBean.SongListBean> songsList;
    private List<SongsListBean.BodyBean.SongListBean> songsListAll = new ArrayList<>();
    private MySongsListAdapter songsListAdapter;
    private int index = 1;
    private DownloadManger downloadManger;
    private Context mContext;
    private TextView downTV;
    private TextView paimaiTV;




//设置页面的刷新效果
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (songsListAll != null) {
                    songsListAll.addAll(songsList);
                    songsListAdapter.notifyDataSetChanged();
                    // 脚布局隐藏
                    refreshLV.hideFooterView();
                }
            }
            if (msg.what == 2) {
                songsListAdapter.notifyDataSetChanged();
                // 头布局隐藏
                refreshLV.hideHeaderView();
            }
        }
    };

    private AddPresenter addPresenter;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private TextView dialogMusicTV;
    private TextView dialogTimeTV;
    private TextView dialogGiveupTV;
    private TextView dialogNowTV;
    private AlertDialog builder;

    private SQLiteDatabase db;
    private boolean isSomeOneSing;
    private RelativeLayout itemLayout;
    public boolean ISPAIMAI=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        sp = mContext.getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        db = DbManager.getInstance().openDatabase();
        //视图初始化
        initView();
        return inflateView;
    }



    private void initView() {
        inflateView = View.inflate(mContext, R.layout.fragment_hot, null);
        refreshLV = (RefreshListView) inflateView.findViewById(R.id.refreshLV);

        addPresenter = new AddPresenter();
        addPresenter.attach(HotFragment.this);
        //请求歌曲列表
        getSongsList();

        refreshLV.setOnRefreshListener(this);
    }
//OK的网络请求 设置网络请求

    private void getSongsList() {
        OkHttpUtils.get()
                .url(REQUEST_SONGS_URL)
//                通过添加的这两个参数来设置成功

                .addParams("timestamp", Constant.getTime())
                .addParams("sign", Constant.getSign())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d(TAG, "onError: " + e.toString());

                    }

                    @Override
                    public void onResponse(Call call, String s) {
                        gson = new Gson();
                        songsListBean = gson.fromJson(s, SongsListBean.class);
                        songsList = songsListBean.getBody().getSong_list();
                        songsListAll.addAll(songsList);

                        if (songsListAll != null) {
                            //设置适配器
                            songsListAdapter = new MySongsListAdapter(mContext, songsListAll);
                            refreshLV.setAdapter(songsListAdapter);

                            //item的子控件
                            songsListAdapter.setOnmItemListener(itemListener);
                        } else {
                            Log.d(TAG, "songsListAll是空的");
                        }

                    }
                });
    }
// 给热门里面具体的某个条目设置缓存

    MySongsListAdapter.onmItemListener itemListener = new MySongsListAdapter.onmItemListener() {
        @Override
        public void onmItemClick(final int i) {
            for (int x = 0; x < refreshLV.getChildCount(); x++) {
                itemLayout = (RelativeLayout) refreshLV.getChildAt(i-refreshLV.getFirstVisiblePosition()+1);
                downTV = (TextView) itemLayout.findViewById(R.id.HotFragment_item_TextView_DownLoad);
                paimaiTV = (TextView) itemLayout.findViewById(R.id.HotFragment_item_TextView_Paimai);
            }
            IsUtils.showShort(mContext, "点击了缓冲，position为： " + i);

            //下载歌
            new Thread(){
                @Override
                public void run() {

                    Looper.prepare();
                    String name = System.currentTimeMillis() + "";
                    MessageDigest md5;
                    try {
                        md5 = MessageDigest.getInstance("MD5");
                        name = new String(md5.digest(songsListAll.get(i).getLrc().getBytes()));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    downloadManger = DUtil.init(mContext)
                    .url(songsListAll.get(i).getLrc())
                    .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")//歌曲下载的位置
                    .name(name + songsListAll.get(i).getLrc().substring(songsListAll.get(i).getLrc().lastIndexOf(".")))
                    .childTaskCount(3)
                    .build()
                    .start(new DownloadCallback() {
                        @Override
                        public void onStart(long currentSize, long totalSize, float progress) {
                            Log.d(TAG, "downLoadLrc---onStart:");

                        }

                        @Override
                        public void onProgress(long currentSize, long totalSize, final float progress) {
                            Log.d(TAG, "downLoadLrc---onProgress:  " + (int) progress + "%");

                        }

                        @Override
                        public void onPause() {

                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onFinish(File file) {
                            //下载MP3
                            downLoadMp3(i);
                            Log.d(TAG, "downLoadLrc---onFinish: ");
                        }

                        @Override
                        public void onWait() {

                        }

                        @Override
                        public void onError(String error) {
                            Log.d(TAG, "downLoadLrc---onError: ");

                        }
                    });

//                    downLoadMp3(i);
                    Looper.loop();
                }

            }.start();
        }
    };



    //点击缓冲下载歌曲的方法
    private void downLoadMp3(final int position) {
//        获取系统当前的时间
        String name = System.currentTimeMillis() + "";
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            name = new String(md5.digest(songsListAll.get(position).getLrc().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


//下载保存到数据库
        downloadManger = DUtil.init(mContext)
                .url(songsListAll.get(position).getLink())
                .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
                .name(name + ".mp3")
                .childTaskCount(3)
                .build()
                .start(new DownloadCallback() {//重写下载的方法
                    @Override
                    public void onStart(long currentSize, long totalSize, float progress) {
                        Log.d(TAG, "downLoadMp3---onStart:");

                    }

                    @Override
                    public void onProgress(long currentSize, long totalSize, final float progress) {
                        downTV.post(new Runnable() {
                            @Override
                            public void run() {
//                                设置下载的进度
                                downTV.setText("正在缓冲" + (int) progress + "%");
                            }
                        });
                        Log.d(TAG, "downLoadMp3---onProgress:  " + progress + "%");

                    }

                    @Override
                    public void onPause() {

                    }

                    @Override
                    public void onCancel() {

                    }
//                    下载完成后存数据到数据库
                    @Override
                    public void onFinish(File file) {
                            downTV.post(new Runnable() {
                                @Override
                                public void run() {
//                                    下载完成后字段变成排麦下进入还是排麦
//                                    判断一下在数据库中是否存在这个数据


                                    songsList.get(position).getName();


                                    downTV.setVisibility(View.GONE);
                                    edit.putString("paimai","排麦");
                                    edit.commit();

//                                    从sp中取出来
                                    String string = sp.getString("paimai", "缓冲");
                                    paimaiTV.setVisibility(View.VISIBLE);
                                    paimaiTV.setText(string);

                                    //添加到数据库中的相关字段
                                    songsListAll.get(position).setStatecode(1);

                                    ContentValues cv=new ContentValues();
                                    cv.put("songid", songsListAll.get(position).getId());
                                    cv.put("songname", songsListAll.get(position).getName());
                                    cv.put("author", songsListAll.get(position).getAuthor());
                                    cv.put("link", songsListAll.get(position).getLink());
                                    cv.put("lrc", songsListAll.get(position).getLrc());
                                    cv.put("mode", 1);
//                                    创建数据存数据库歌曲的表
                                    db.insert("songs_table", null, cv);

                                    Cursor  cursor = db.query("songs_table", null, null, null, null, null, null);
                                            while (cursor.moveToNext()){

                                                int songid= cursor.getInt(cursor.getColumnIndex("songid"));
                                                String songName= cursor.getString(cursor.getColumnIndex("songname"));
                                                String author= cursor.getString(cursor.getColumnIndex("author"));
                                                String link= cursor.getString(cursor.getColumnIndex("link"));
                                                String lrc= cursor.getString(cursor.getColumnIndex("lrc"));
                                                int mode= cursor.getInt(cursor.getColumnIndex("mode"));
//                                                遍历集合判断值是否符合要求


//                                        Toast.makeText(getActivity(),songName+"GGG",Toast.LENGTH_SHORT).show();
//                                        Log.e(TAG, "initView: "+songid+"\n"+songName+"\n"+author+"\n"+link+"\n"+lrc+"\n"+mode);
                                            }

                                    paimaiTV.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            点击排麦向里面传值

                                            IsUtils.showShort(mContext,"点了排麦"+ position);
                                            edit.putString("musicname", songsListAll.get(position).getName());
                                            edit.putString("musiclink", songsListAll.get(position).getLink());
                                            edit.putString("singer", songsListAll.get(position).getAuthor());
                                            edit.putString("lyric", songsListAll.get(position).getLrc());
                                            String name = System.currentTimeMillis() + "";
                                            MessageDigest md5 ;

                                            try {
                                                md5 = MessageDigest.getInstance("MD5");
//                                                获取歌的名称
                                                name = new String(md5.digest(songsListAll.get(position).getLrc().getBytes()));
                                            } catch (NoSuchAlgorithmException e) {
                                                e.printStackTrace();
                                            }
//                                          保存到本地的磁盘中
                                            edit.putString("mp3Location", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" +  name + ".mp3");
                                            edit.putString("lyricLocation", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" + name + songsListAll.get(position).getLrc().substring(songsListAll.get(position).getLrc().lastIndexOf(".")));
                                            edit.commit();
                                            Log.e(TAG, "歌名为："+songsListAll.get(position).getName());

                                            isSomeOneSing = GlobalInstance.getInstance().getKTVActivity().isSomeOneSing();

                                            HashMap<String,String> map=new HashMap<>();
                                            map.put("token",sp.getString("LoginToken",""));
                                            map.put("sid",songsListAll.get(position).getId()+"");

                                            Log.e(TAG, "歌曲ID:  "+songsListAll.get(position).getId()+"");
                                            Log.e(TAG, "登录的token:  "+sp.getString("LoginToken",""));
                                            Log.e(TAG, "roomId:  "+sp.getString("JFmRoomName",""));


                                            addPresenter.postAdd(sp.getString("JFmRoomName",""), map, AddBean.class);
                                        }
                                    });
                                }
                            });
                            Log.d(TAG, "downLoadMp3---onFinish: ");
                    }

                    @Override
                    public void onWait() {

                    }

                    @Override
                    public void onError(String error) {
                        Log.d(TAG, "downLoadMp3---onError: ");

                    }
                });
    }

    //上麦成功的回调
    @Override
    public void onSuccess(AddBean addBean) {
        if (addBean.getStatus()==1){
            if (isSomeOneSing==true) {
//                点击排麦后调用了这个方法

                IsUtils.showShort(getActivity(),"排麦成功，请耐心等待~");
                showSuccessDialog();
            }else {
                //排麦成功dialog
//                showSuccessDialog();
                IsUtils.showShort(getActivity(),"排麦失败，请您重新尝试！");
            }

            Log.d(TAG, "onSuccess: "+addBean.getMsg());
        }else {
            //排麦重复
            if (addBean.getStatus()==6){
                showTwoDialog();
            }
            Log.d(TAG, "onSuccess: "+addBean.getMsg());
        }
    }

    //唱完才能点歌
    private void showTwoDialog() {
        final AlertDialog.Builder builderTwo = new AlertDialog.Builder(getActivity());
        builderTwo.setMessage("唱完才能点歌哦~");
        builderTwo.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderTwo.create().show();
    }




//一个十五秒设置是否去上麦的模块
  private void showSuccessDialog() {
        AlertDialog.Builder builderBig = new AlertDialog.Builder(getActivity());
        Log.e(TAG, "showSuccessDialog: ");
        builderBig.setMessage("排麦成功");

//排麦成功后点击确定后继续弹出获取条目的值选择上麦还是放弃

        builderBig.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder = new AlertDialog.Builder(getActivity()).create();

                View dialogView=View.inflate(getActivity(), R.layout.paimai_dialog,null);
                dialogMusicTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Music);
                dialogTimeTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Time);
                dialogGiveupTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Giveup);
                dialogNowTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Now);


//                给立即上麦设置设置传过去的值
//                从数据库里面取出数据
//                Cursor cursor = db.rawQuery("select * from songs_table", null);
//                                    while (cursor.moveToNext()){
//                                        String name = cursor.getString(cursor.getColumnIndex("songname"));
//                                        Toast.makeText(getActivity(),name+"GGG",Toast.LENGTH_SHORT).show();
//                                    }

                dialogMusicTV.setText(sp.getString("musicname",""));
//                设置要显示的时间
                final CountDownTimer timer=new CountDownTimer(15000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        dialogTimeTV.setText((millisUntilFinished / 1000)+"秒后不上麦则自动放弃");
                    }

                    @Override
                    public void onFinish() {
                        dialogTimeTV.setText("已自动放弃");
                        //请求下麦接口
                        HashMap<String,String> map=new HashMap<>();
                        map.put("token",sp.getString("LoginToken",""));

                        Log.d(TAG, "自动放弃"+sp.getString("JFmRoomName",""));

                        addPresenter.delete(sp.getString("JFmRoomName",""), map, DeleteBean.class);
                    }
                };
                timer.start();

                builder.setView(dialogView);
                builder.show();

                dialogGiveupTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //请求下麦接口
                        HashMap<String,String> map=new HashMap<>();
                        map.put("token",sp.getString("LoginToken",""));
                        Log.d(TAG, "手动放弃"+sp.getString("JFmRoomName",""));
                        addPresenter.delete(sp.getString("JFmRoomName",""), map, DeleteBean.class);
                        timer.cancel();
                    }
                });
//上麦
                dialogNowTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        IsUtils.showShort(getActivity(),"立即上麦");
//                        跳转到showShor Activity
                        Intent intent=new Intent();
//                        传递了
                        intent.putExtra("mp3Url",sp.getString("musiclink",""));
                        intent.putExtra("lyricUrl",sp.getString("lyric",""));

                        //本地地址
                        intent.putExtra("mp3Location",sp.getString("mp3Location",""));
                        intent.putExtra("lyricLocation",sp.getString("lyricLocation",""));

                        intent.putExtra("musicName",sp.getString("musicname",""));
                        intent.putExtra("singer",sp.getString("singer",""));

                        getActivity().setResult(2,intent);

                        builder.dismiss();
                        timer.cancel();
                        getActivity().finish();
                    }
                });
                dialog.dismiss();
            }

        });
        builderBig.create().show();
    }

    @Override
    public void onError(Throwable t) {
        Log.d(TAG, "onError: "+t.getMessage());
    }

    @Override
    public void onDeleteSuccess(DeleteBean bean) {
        if (bean.getStatus()==1){
            builder.dismiss();
            IsUtils.showShort(getActivity(),"放弃");
        }else {
            IsUtils.showShort(getActivity(),"放弃失败");
        }
    }

    @Override
    public void onDeleteError(Throwable throwable) {
        Log.d(TAG, "onDeleteError: "+throwable.getMessage());
    }

    @Override
    public void onAddListSuccess(AddListBean bean) {

    }

    @Override
    public void onAddListError(Throwable throwable) {

    }

    //下拉刷新
    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(1000);
                OkHttpUtils.get()
                        .url(REQUEST_SONGS_URL)
                        .addParams("timestamp", Constant.getTime())
                        .addParams("sign",Constant.getSign())
                        .addParams("page","1")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(Call call, String s) {
                                gson = new Gson();
                                songsListBean = gson.fromJson(s, SongsListBean.class);
                                songsList = songsListBean.getBody().getSong_list();
                                handler.sendEmptyMessage(2);
                                Log.d(TAG, "下拉---执行onResponse: ");
                            }
                        });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(TAG, "下拉---执行onPostExecute: ");
            }
        }.execute(new Void[]{});
    }
    //加载更多
    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {
            final int newIndex=index+1;
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(1000);

                OkHttpUtils.get()
                        .url(REQUEST_SONGS_URL)
                        .addParams("timestamp", Constant.getTime())
                        .addParams("sign",Constant.getSign())
                        .addParams("page",""+ newIndex)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(Call call, String s) {
                                gson = new Gson();
                                songsListBean = gson.fromJson(s, SongsListBean.class);
                                songsList = songsListBean.getBody().getSong_list();
                                index=newIndex;
                                handler.sendEmptyMessage(1);
                                Log.d(TAG, "onResponse: "+songsList.get(0).getName());
                                Log.d(TAG, "上拉---执行onResponse: ");

                            }
                        });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                Log.d(TAG, "上拉---执行onPostExecute: ");
            }

        }.execute(new Void[]{});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        关闭数据库
        DbManager.getInstance().closeDatabase();
    }
}
