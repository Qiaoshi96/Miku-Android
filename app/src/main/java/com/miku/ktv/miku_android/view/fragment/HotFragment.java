package com.miku.ktv.miku_android.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
//            if (msg.what == 2) {
//
//            }
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        sp = mContext.getSharedPreferences("config", MODE_PRIVATE);
        edit = sp.edit();
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
                            songsListAdapter.setOnItemDeleteClickListener(listener);
                            refreshLV.setAdapter(songsListAdapter);
                        } else {
                            Log.d(TAG, "songsListAll是空的");
                        }

                    }
                });
    }

    MySongsListAdapter.onItemDeleteListener listener = new MySongsListAdapter.onItemDeleteListener() {
        @Override
        public void onDeleteClick(final int i) {
            for (int x = 0; x < refreshLV.getChildCount(); x++) {
                RelativeLayout itemLayout = (RelativeLayout) refreshLV.getChildAt(i - refreshLV.getFirstVisiblePosition() + 1);
                downTV = (TextView) itemLayout.findViewById(R.id.HotFragment_item_TextView_DownLoad);
                paimaiTV = (TextView) itemLayout.findViewById(R.id.HotFragment_item_TextView_Paimai);
            }
            IsUtils.showShort(mContext, "点击了缓冲，位置是： " + i);
            //下载歌曲
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    downloadManger = DUtil.init(mContext)
                            .url(songsListAll.get(i).getLink())
                            .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
                            .name(songsListAll.get(i).getName() + "banzou.mp3")
                            .childTaskCount(3)
                            .build()
                            .start(new DownloadCallback() {
                                @Override
                                public void onStart(long currentSize, long totalSize, float progress) {
                                    Log.d(TAG, "onStart:");

                                }

                                @Override
                                public void onProgress(long currentSize, long totalSize, final float progress) {
                                    downTV.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            downTV.setText("正在缓冲" + progress + "%");

                                        }
                                    });
                                    Log.d(TAG, "onProgress:  " + progress + "%");

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

                                            paimaiTV.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    IsUtils.showShort(mContext,"点了排麦"+i);
                                                    edit.putString("musicname", songsListAll.get(i).getName());
                                                    edit.putString("musiclink", songsListAll.get(i).getLink());
                                                    edit.putString("singer", songsListAll.get(i).getAuthor());
                                                    edit.putString("lyric", songsListAll.get(i).getLrc());
                                                    edit.commit();
                                                    Log.d(TAG, "歌名为："+songsListAll.get(i).getName());

                                                    HashMap<String,String> map=new HashMap<>();
                                                    map.put("token",sp.getString("LoginToken",""));
                                                    Log.d(TAG, "登录的token:  "+sp.getString("LoginToken",""));
                                                    map.put("sid",songsListAll.get(i).getId()+"");
                                                    Log.d(TAG, "歌曲ID:  "+songsListAll.get(i).getId()+"");
                                                    Log.d(TAG, "roomId:  "+sp.getString("roomid",""));
                                                    addPresenter.postAdd(sp.getString("roomid",""), map, AddBean.class);
                                                }
                                            });
                                        }
                                    });
                                    Log.d(TAG, "onFinish: ");

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ArrayList<HistroyBean> list = new ArrayList<>();
                                            HistroyBean histroyBean = new HistroyBean();
                                            histroyBean.setId(songsListAll.get(i).getId());
                                            histroyBean.setName(songsListAll.get(i).getName());
                                            histroyBean.setAuthor(songsListAll.get(i).getAuthor());
                                            histroyBean.setLrc(songsListAll.get(i).getLrc());
                                            histroyBean.setOriginal(songsListAll.get(i).getOriginal());
                                            histroyBean.setLink(songsListAll.get(i).getLink());
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
                                    Log.d(TAG, "onError: ");

                                }
                            });
                    Looper.loop();
                }

            }.start();

            //下载歌词
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    downloadManger = DUtil.init(mContext)
                            .url(songsListAll.get(i).getLrc())
                            .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
                            .name(songsListAll.get(i).getName() + "_lrc.kas")
                            .childTaskCount(3)
                            .build()
                            .start(new DownloadCallback() {
                                @Override
                                public void onStart(long currentSize, long totalSize, float progress) {
                                    Log.d(TAG, "onStart:");

                                }

                                @Override
                                public void onProgress(long currentSize, long totalSize, final float progress) {
                                    downTV.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            downTV.setText("正在缓冲" + progress + "%");

                                        }
                                    });
                                    Log.d(TAG, "onProgress:  " + progress + "%");

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

                                            paimaiTV.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    IsUtils.showShort(mContext,"点了排麦"+i);
                                                    edit.putString("musicname", songsListAll.get(i).getName());
                                                    edit.putString("musiclink", songsListAll.get(i).getLink());
                                                    edit.putString("singer", songsListAll.get(i).getAuthor());
                                                    edit.putString("lyric", songsListAll.get(i).getLrc());
                                                    edit.commit();
                                                    Log.d(TAG, "歌名为："+songsListAll.get(i).getName());

                                                    HashMap<String,String> map=new HashMap<>();
                                                    map.put("token",sp.getString("LoginToken",""));
                                                    Log.d(TAG, "登录的token:  "+sp.getString("LoginToken",""));
                                                    map.put("sid",songsListAll.get(i).getId()+"");
                                                    Log.d(TAG, "歌曲ID:  "+songsListAll.get(i).getId()+"");
                                                    Log.d(TAG, "roomId:  "+sp.getString("roomid",""));
                                                    addPresenter.postAdd(sp.getString("roomid",""), map, AddBean.class);
                                                }
                                            });
                                        }
                                    });
                                    Log.d(TAG, "onFinish: ");

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ArrayList<HistroyBean> list = new ArrayList<>();
                                            HistroyBean histroyBean = new HistroyBean();
                                            histroyBean.setId(songsListAll.get(i).getId());
                                            histroyBean.setName(songsListAll.get(i).getName());
                                            histroyBean.setAuthor(songsListAll.get(i).getAuthor());
                                            histroyBean.setLrc(songsListAll.get(i).getLrc());
                                            histroyBean.setOriginal(songsListAll.get(i).getOriginal());
                                            histroyBean.setLink(songsListAll.get(i).getLink());
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
                                    Log.d(TAG, "onError: ");

                                }
                            });
                    Looper.loop();
                }

            }.start();


        }
    };
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
                        addPresenter.delete(sp.getString("roomid",""), map, DeleteBean.class);
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
                        addPresenter.delete(sp.getString("roomid",""), map, DeleteBean.class);
                        timer.cancel();
                    }
                });

                dialogNowTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IsUtils.showShort(getActivity(),"立即上麦");

                        Intent intent=new Intent();
                        intent.putExtra("musicIntent",sp.getString("musicname",""));
                        intent.putExtra("linkIntent",sp.getString("musiclink",""));
                        intent.putExtra("singerIntent",sp.getString("singer",""));
                        intent.putExtra("lyricIntent",sp.getString("lyric",""));
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


    public interface OnDataTransmissionListener {
        public void dataTransmission(ArrayList<HistroyBean> list);
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
                            }
                        });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                songsListAdapter.notifyDataSetChanged();
                refreshLV.hideHeaderView();
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
                                Log.d(TAG, "onResponse: "+songsList.get(0).getName());

                            }
                        });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                handler.sendEmptyMessage(1);
            }

        }.execute(new Void[]{});
    }
}
