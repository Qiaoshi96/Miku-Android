package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.SearchBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.SearchPresenter;
import com.miku.ktv.miku_android.view.adapter.MySearchAdapter;
import com.miku.ktv.miku_android.view.custom.RefreshListView;
import com.miku.ktv.miku_android.view.iview.ISearchView;
import com.miku.ktv.miku_android.view.iview.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

import static com.miku.ktv.miku_android.model.utils.Constant.gson;
import static com.miku.ktv.miku_android.view.fragment.HotFragment.REQUEST_SONGS_URL;

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
        refreshLVSearch.setOnRefreshListener(this);
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
