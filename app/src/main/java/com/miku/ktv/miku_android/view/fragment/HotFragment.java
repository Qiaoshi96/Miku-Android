package com.miku.ktv.miku_android.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.SongsListBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.presenter.SongsPresenter;
import com.miku.ktv.miku_android.pulltorefreshlistview.PullToRefreshBase;
import com.miku.ktv.miku_android.pulltorefreshlistview.PullToRefreshListView;
import com.miku.ktv.miku_android.view.adapter.MySongsListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 焦帆 on 2017/10/11.
 */

public class HotFragment extends Fragment {
    public static final String TAG="HotFragment";
    public static final String REQUEST_SONGS_URL="http://ktv.fibar.cn/api/v1/songs";

    private View inflateView;
    private PullToRefreshListView pullToRefreshListView;

    private SongsPresenter songsPresenter;
    private Gson gson;
    private SongsListBean songsListBean;
    private List<SongsListBean.BodyBean.SongListBean> songsList;
    private List<SongsListBean.BodyBean.SongListBean> songsListAll=new ArrayList<>();
    private MySongsListAdapter songsListAdapter;
    private int index=1;

    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {

            if(msg.what==98){
                songsListAll.addAll(songsList);
                songsListAdapter.notifyDataSetChanged();
                //关闭刷新的动画
                pullToRefreshListView.onRefreshComplete();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //视图初始化
        initView();
        //设定刷新监听
        setRefreshListener();
        return inflateView;
    }

    private void initView() {
        inflateView = View.inflate(getActivity(), R.layout.fragment_hot, null);
        pullToRefreshListView=(PullToRefreshListView) inflateView.findViewById(R.id.pull_to_refresh_listview);

//        songsPresenter = new SongsPresenter();
//        songsPresenter.attach(getActivity());
//        songsPresenter.getSongsList(SongsListBean.class);
        new Thread(){
            @Override
            public void run() {
                //请求歌曲列表
                getSongsList();
            }
        }.start();

    }

    private void getSongsList() {
        OkHttpUtils.get()
                .url(REQUEST_SONGS_URL)
                .addParams("timestamp", Constant.getTime())
                .addParams("sign",Constant.getSign())
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d(TAG, "onError: "+e.toString());

            }

            @Override
            public void onResponse(Call call, String s) {
                gson = new Gson();
                songsListBean = gson.fromJson(s, SongsListBean.class);
                songsList = songsListBean.getBody().getSong_list();
                songsListAll.addAll(songsList);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (songsListAll!=null){
                            //设置适配器
                            songsListAdapter = new MySongsListAdapter(getActivity(),songsListAll);
                            pullToRefreshListView.setAdapter(songsListAdapter);
                        }else {
                            Log.d(TAG, "songsListAll是空的");
                        }
                    }
                });
            }
        });
    }


    private void setRefreshListener() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME  | DateUtils.FORMAT_SHOW_DATE  | DateUtils.FORMAT_ABBREV_ALL);
                //显示最后更新的时间
                refreshView.getLoadingLayoutProxy() .setLastUpdatedLabel(label);

                //代表下拉刷新
                if(refreshView.getHeaderLayout().isShown()){
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

                //代表上拉加载更多
                if(refreshView.getFooterLayout().isShown()){
                    new Thread(){
                        public void run() {
                            try {
                                sleep(1000);
                                int newIndex=index+1;
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
                                            }
                                        });
                                index=newIndex;
                                handler.sendEmptyMessage(98);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

            }
        });
    }
}
