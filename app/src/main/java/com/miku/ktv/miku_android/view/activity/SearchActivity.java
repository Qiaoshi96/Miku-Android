package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.main.GlobalInstance;
import com.miku.ktv.miku_android.model.bean.AddBean;
import com.miku.ktv.miku_android.model.bean.SearchBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.SearchPresenter;
import com.miku.ktv.miku_android.view.adapter.MySearchAdapter;
import com.miku.ktv.miku_android.view.custom.RefreshListView;
import com.miku.ktv.miku_android.view.iview.ISearchView;
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

import static com.miku.ktv.miku_android.model.utils.Constant.gson;
import static com.miku.ktv.miku_android.view.fragment.HotFragment.REQUEST_SONGS_URL;

/**
 * 搜索页面
 */
public class SearchActivity extends Activity implements ISearchView<SearchBean>,OnRefreshListener {
    public static final String TAG="SearchActivity";

    private SearchView searchview;
    private RefreshListView refreshLVSearch;
    private ImageView back;
    private int index=1;
    private SearchPresenter presenter;
    private SearchBean searchBean1;

    private List<SearchBean.BodyBean.SongListBean> searchList=new ArrayList<>();
    private List<SearchBean.BodyBean.SongListBean> searchListAll=new ArrayList<>();

    private MySearchAdapter adapter;
    private int newIndex;
    private String str;
    private SearchBean searchListBean;


//Toy 创建刷新的控件

    private RelativeLayout itemLayout;
    private TextView downTV;
    private TextView paimaiTV;
    private DownloadManger downloadManger=null;

    private AlertDialog builder;
    private TextView dialogMusicTV;
    private TextView dialogTimeTV;
    private TextView dialogGiveupTV;
    private TextView dialogNowTV;
//    使用mContext来代替上下文
        private Context mContext=SearchActivity.this;
//    用SP把值存取过去
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (searchListAll != null) {
                        searchListAll.addAll(searchList);
                        adapter.notifyDataSetChanged();
                        refreshLVSearch.hideFooterView();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sp = getSharedPreferences("joyconfig", MODE_PRIVATE);
        edit = sp.edit();
        initView();
        setTextListener();
    }

    private void setTextListener() {
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                str = newText;
                if (TextUtils.isEmpty(newText)){
                    searchListAll.clear();
                }else {
                    //请求搜索歌曲接口
                    HashMap<String,String> map=new HashMap<>();
                    map.put("page",""+index);
                    map.put("query",str);
                    presenter.getSearch(map,SearchBean.class);
                }
                return false;
            }
        });
    }

    @Override
    public void onSuccess(SearchBean searchBean) {
        if (searchBean.getStatus()==1){
            newIndex = index+1;
            IsUtils.showShort(this,"搜索接口请求成功");
            Log.d(TAG, "onSuccess:  "+searchBean.getMsg());

            String searchJson = gson.toJson(searchBean);
            searchBean1 = gson.fromJson(searchJson,SearchBean.class);
            searchList=searchBean1.getBody().getSong_list();
            searchListAll.addAll(searchList);

            setMySearchAdapter();

        }else {
            IsUtils.showShort(this,"搜索接口请求失败");
        }
    }



    //设置适配器
    private void setMySearchAdapter() {
        adapter = new MySearchAdapter(this,searchListAll);
        refreshLVSearch.setAdapter(adapter);
        adapter.setOnSearchItemListener(itemListener);
        refreshLVSearch.setOnRefreshListener(this);
    }


    //缓冲和排麦点击事件
    MySearchAdapter.onSearchItemListener itemListener=new MySearchAdapter.onSearchItemListener() {
        @Override
        public void onSearchItemClick(final int i) {
//            在这里给页面设置点击监听 第一步实现的逻辑是点击下载

            for (int t = 0; t <  refreshLVSearch.getChildCount(); t++) {
                itemLayout = (RelativeLayout) refreshLVSearch.getChildAt(i - refreshLVSearch.getFirstVisiblePosition() + 1);
                downTV = (TextView) itemLayout.findViewById(R.id.Search_item_TextView_DownLoad);
                paimaiTV = (TextView) itemLayout.findViewById(R.id.Search_item_TextView_Paimai);
            }
//                开启子线程下载歌曲
                new Thread(){
                    @Override
                    public void run() {
                        Looper.prepare();
                        String name = System.currentTimeMillis() + "";
                        MessageDigest md5;
                        try {
                            md5=MessageDigest.getInstance("MD5");
                            name=new String(md5.digest(searchListAll.get(i).getLrc().getBytes()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                            downloadManger= DUtil.init(SearchActivity.this)
                                    .url(searchListAll.get(i).getLrc())
                                    .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
                                    .name(name+searchListAll.get(i).getLrc().substring(searchListAll.get(i).getLrc().lastIndexOf(".")))
                                    .childTaskCount(3)
                                    .build()
                                    .start(new DownloadCallback() {
                                        @Override
                                        public void onStart(long currentSize, long totalSize, float progress) {
//                                            开始下载
                                        }

                                        @Override
                                        public void onProgress(long currentSize, long totalSize, final float progress) {
//                                           设置下载进度
                                            downTV.post(new Runnable() {
                                                @Override
                                                public void run() {
//                                设置下载的进度
                                                    downTV.setText("正在缓冲" + (int) progress + "%");
                                                }
                                            });
                                        }

                                        @Override
                                        public void onPause() {
//                                      暂停下载
                                        }

                                        @Override
                                        public void onCancel() {
//                                              取消下载
                                        }

                                        @Override
                                        public void onFinish(File file) {

                                            downLoadMp3(i);
//                                                实现下载歌曲的方法   开启downTV的子线程进行下载
                                            downTV.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    downTV.setVisibility(View.GONE);
                                                    paimaiTV.setVisibility(View.VISIBLE);
                                                    paimaiTV.setText("排麦");
//                                                    下载成功后点击排麦弹出的对话框
                                                    paimaiTV.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
//                                                            点击排麦向里面传递数据
                                                            edit.putString("musicname", searchListAll.get(i).getName());


                                                            showSuccessDialog();
                                                        }
                                                    });
                                                }
                                            });


                                            IsUtils.showShort(SearchActivity.this,"下载完成"+i);

                                        }

                                        @Override
                                        public void onWait() {

                                        }

                                        @Override
                                        public void onError(String error) {
                                            IsUtils.showShort(SearchActivity.this,"下载失败，检查网络状态");
                                            return;
                                        }
                                    });
                        Looper.loop();
                    }
                }.start();
        }
    };
// 设置排麦成功的对话框

    private void showSuccessDialog(){
        AlertDialog.Builder builderBig = new AlertDialog.Builder(SearchActivity.this);
        builderBig.setMessage("排麦成功");
        //排麦成功后点击确定后继续弹出获取条目的值选择上麦还是放弃
        builderBig.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                点击排麦后跳转到是否上麦的详情页面
                builder = new AlertDialog.Builder(SearchActivity.this).create();
//                复用那个弹出框的布局
                View dialogView=View.inflate(SearchActivity.this, R.layout.paimai_dialog,null);
                dialogMusicTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Music);
                dialogTimeTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Time);
                dialogGiveupTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Giveup);
                dialogNowTV = (TextView) dialogView.findViewById(R.id.PaimaiDialog_TextView_Now);
//                给歌名设置名称（未获取到值）
                dialogMusicTV.setText(sp.getString("musicname",""));
//                创建内部弹出框
                builder.setView(dialogView);
                builder.show();
            }
        });
        builderBig.create().show();
    }





    //点击缓冲下载歌曲的方法
    private void downLoadMp3(final int position) {
//        获取系统当前的时间
        String name = System.currentTimeMillis() + "";
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            name = new String(md5.digest(searchListAll.get(position).getLrc().getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//下载保存到数据库
        downloadManger = DUtil.init(SearchActivity.this)
                .url(searchListAll.get(position).getLink())
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
                                downTV.setVisibility(View.GONE);
                                edit.putString("paimai","排麦");
                                edit.commit();

//                                    从sp中取出来
                                String string = sp.getString("paimai", "缓冲");
                                paimaiTV.setVisibility(View.VISIBLE);
                                paimaiTV.setText(string);


                                paimaiTV.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        IsUtils.showShort(mContext,"点了排麦"+ position);

                                        String name = System.currentTimeMillis() + "";

                                        MessageDigest md5 ;

                                        try {
                                            md5 = MessageDigest.getInstance("MD5");
//                                                获取歌的名称
                                            name = new String(md5.digest(searchListAll.get(position).getLrc().getBytes()));
                                        } catch (NoSuchAlgorithmException e) {
                                            e.printStackTrace();
                                        }
//                                          保存到本地的磁盘中
                                        edit.putString("mp3Location", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" +  name + ".mp3");
                                        edit.putString("lyricLocation", Environment.getExternalStorageDirectory() + "/MiDoDownUtil/" + name + searchListAll.get(position).getLrc().substring(searchListAll.get(position).getLrc().lastIndexOf(".")));
                                        edit.commit();
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







    @Override
    public void onError(Throwable t) {
        Log.d(TAG, "onError: "+t.getMessage());
    }

    //下拉刷新
    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                refreshLVSearch.hideHeaderView();
            }
        }.execute(new Void[]{});
    }
    //上拉加载
    @Override
    public void onLoadingMore() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(1000);

                OkHttpUtils.get()
                        .url(REQUEST_SONGS_URL)
                        .addParams("timestamp", Constant.getTime())
                        .addParams("sign",Constant.getSign())
                        .addParams("page",""+ newIndex)
                        .addParams("query",str)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(Call call, String s) {
                                gson = new Gson();
                                searchListBean = gson.fromJson(s, SearchBean.class);
                                searchList = searchListBean.getBody().getSong_list();
                                newIndex=newIndex+1;
                                handler.sendEmptyMessage(2);
                            }
                        });

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {

            }
        }.execute(new Void[]{});
    }

    private void initView() {
        searchview = (SearchView) findViewById(R.id.searchView);
        refreshLVSearch = (RefreshListView) findViewById(R.id.refreshLVSearch);
        back = (ImageView) findViewById(R.id.Search_ImageView_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter = new SearchPresenter();
        presenter.attach(this);
    }

}
