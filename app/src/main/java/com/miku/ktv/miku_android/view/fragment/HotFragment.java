package com.miku.ktv.miku_android.view.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AddBean;
import com.miku.ktv.miku_android.model.bean.AddListBean;
import com.miku.ktv.miku_android.model.bean.DeleteBean;
import com.miku.ktv.miku_android.model.bean.HistroyBean;
import com.miku.ktv.miku_android.model.bean.SongsListBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.model.utils.MyHelper;
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

import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 焦帆 on 2017/10/11.
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
    private int pressed = 1;
    private DownloadManger downloadManger;
    private Context mContext;
    private TextView downTV;
    private TextView paimaiTV;

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
    //true表示已经排麦，false表示缓冲
    public static boolean ISPAIMAI=false;
    private MyHelper myHelper;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        sp = mContext.getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
        myHelper = new MyHelper(getActivity());
        db = myHelper.getWritableDatabase();
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

    private void getSongsList() {
        OkHttpUtils.get()
                .url(REQUEST_SONGS_URL)
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

    MySongsListAdapter.onmItemListener itemListener = new MySongsListAdapter.onmItemListener() {
        @Override
        public void onmItemClick(final int i) {
            for (int x = 0; x < refreshLV.getChildCount(); x++) {
                RelativeLayout itemLayout = (RelativeLayout) refreshLV.getChildAt(i - refreshLV.getFirstVisiblePosition() + 1);
                downTV = (TextView) itemLayout.findViewById(R.id.HotFragment_item_TextView_DownLoad);
                paimaiTV = (TextView) itemLayout.findViewById(R.id.HotFragment_item_TextView_Paimai);
            }
            IsUtils.showShort(mContext, "点击了缓冲，位置是： " + i);

            //下载歌词　　　　　
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    String name = System.currentTimeMillis() + "";
                    MessageDigest md5 = null;
                    try {
                        md5 = MessageDigest.getInstance("MD5");
                        name = new String(md5.digest(songsListAll.get(i).getLrc().getBytes()));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    downloadManger = DUtil.init(mContext)
                            .url(songsListAll.get(i).getLrc())
                            .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
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
                    Looper.loop();
                }

            }.start();
        }
    };
    //下载Mp3
    private void downLoadMp3(final int position) {
        String name = System.currentTimeMillis() + "";
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            name = new String(md5.digest(songsListAll.get(position).getLrc().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        downloadManger = DUtil.init(mContext)
                .url(songsListAll.get(position).getLink())
                .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
                .name(name + ".mp3")
                .childTaskCount(3)
                .build()
                .start(new DownloadCallback() {
                    @Override
                    public void onStart(long currentSize, long totalSize, float progress) {
                        Log.d(TAG, "downLoadMp3---onStart:");

                    }

                    @Override
                    public void onProgress(long currentSize, long totalSize, final float progress) {
                        downTV.post(new Runnable() {
                            @Override
                            public void run() {
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

                    @Override
                    public void onFinish(File file) {
                            downTV.post(new Runnable() {
                                @Override
                                public void run() {
                                    downTV.setVisibility(View.GONE);
                                    paimaiTV.setVisibility(View.VISIBLE);
                                    //添加到数据库中
                                    ContentValues cv=new ContentValues();
                                    cv.put("_id", songsListAll.get(position).getId());
                                    cv.put("songname", songsListAll.get(position).getName());
                                    cv.put("author", songsListAll.get(position).getAuthor());
                                    cv.put("link", songsListAll.get(position).getLink());
                                    cv.put("lrc", songsListAll.get(position).getLrc());
                                    cv.put("mode", 1);
                                    db.insert("songs_table", null, cv);
                                    db.close();
                                    Log.i(TAG, "ContentValues: "+cv.toString());

                                    paimaiTV.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            IsUtils.showShort(mContext,"点了排麦"+ position);
                                            edit.putString("musicname", songsListAll.get(position).getName());
                                            edit.putString("musiclink", songsListAll.get(position).getLink());
                                            edit.putString("singer", songsListAll.get(position).getAuthor());
                                            edit.putString("lyric", songsListAll.get(position).getLrc());
                                            String name = System.currentTimeMillis() + "";
                                            MessageDigest md5 = null;
                                            try {
                                                md5 = MessageDigest.getInstance("MD5");
                                                name = new String(md5.digest(songsListAll.get(position).getLrc().getBytes()));
                                            } catch (NoSuchAlgorithmException e) {
                                                e.printStackTrace();
                                            }

                                            edit.putString("mp3Location", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" +  name + ".mp3");
                                            edit.putString("lyricLocation", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" + name + songsListAll.get(position).getLrc().substring(songsListAll.get(position).getLrc().lastIndexOf(".")));
                                            edit.commit();
                                            Log.d(TAG, "歌名为："+songsListAll.get(position).getName());

                                            HashMap<String,String> map=new HashMap<>();
                                            map.put("token",sp.getString("LoginToken",""));
                                            map.put("sid",songsListAll.get(position).getId()+"");
                                            Log.d(TAG, "歌曲ID:  "+songsListAll.get(position).getId()+"");
                                            Log.d(TAG, "登录的token:  "+sp.getString("LoginToken",""));
                                            Log.d(TAG, "roomId:  "+sp.getString("JFmRoomName",""));
                                            addPresenter.postAdd(sp.getString("JFmRoomName",""), map, AddBean.class);
                                        }
                                    });
                                }
                            });
                            Log.d(TAG, "downLoadMp3---onFinish: ");

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayList<HistroyBean> list = new ArrayList<>();
                                    HistroyBean histroyBean = new HistroyBean();
                                    histroyBean.setId(songsListAll.get(position).getId());
                                    histroyBean.setName(songsListAll.get(position).getName());
                                    histroyBean.setAuthor(songsListAll.get(position).getAuthor());
                                    histroyBean.setLrc(songsListAll.get(position).getLrc());
                                    histroyBean.setOriginal(songsListAll.get(position).getOriginal());
                                    histroyBean.setLink(songsListAll.get(position).getLink());
                                    list.add(histroyBean);

                                    if (mListener != null) {
                                        mListener.dataTransmission(list);
                                    }
                                }
                            });
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
            //排麦成功dialog
            showSuccessDialog();
            Log.d(TAG, "onSuccess: "+addBean.getMsg());
        }else {
            //排麦重复
            if (addBean.getStatus()==6){
                showTwoDialog();
            }
            Log.d(TAG, "onSuccess: "+addBean.getMsg());
        }
    }

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

    private void showSuccessDialog() {
        AlertDialog.Builder builderBig = new AlertDialog.Builder(getActivity());
        Log.d(TAG, "showSuccessDialog: ");
        builderBig.setMessage("排麦成功");
        builderBig.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder = new AlertDialog.Builder(getActivity()).create();

                View dialogView=View.inflate(getActivity(), R.layout.paimai_dialog,null);
                dialogMusicTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Music);
                dialogTimeTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Time);
                dialogGiveupTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Giveup);
                dialogNowTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Now);

                dialogMusicTV.setText(sp.getString("musicname",""));
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

                dialogNowTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IsUtils.showShort(getActivity(),"立即上麦");

                        Intent intent=new Intent();

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

    //接口回调到historyFragment
    public interface OnDataTransmissionListener {
        void dataTransmission(ArrayList<HistroyBean> list);
    }
    private OnDataTransmissionListener mListener;
    public void setOnDataTransmissionListener(OnDataTransmissionListener mListener) {
        this.mListener = mListener;
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
    //上拉加载
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
}
