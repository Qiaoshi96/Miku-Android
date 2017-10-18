package com.miku.ktv.miku_android.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.HistroyBean;
import com.miku.ktv.miku_android.view.adapter.HistroyAdapter;
import com.miku.ktv.miku_android.view.custom.RefreshListView;
import com.miku.ktv.miku_android.view.iview.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by 焦帆 on 2017/10/11.
 */

public class HistroyFragment extends Fragment  {
    public static final String TAG="HistroyFragment";

    private View inflateView;
    private RefreshListView refreshLVHistroy;
    private Context mContext;
    private HistroyAdapter adapter;
    private ArrayList<HistroyBean> listAll=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        initView();
        return inflateView;
    }

    public void setData(ArrayList<HistroyBean> list) {
        for(int x=0; x< list.size(); x++){
            if (list.size() >0 && list != null) {
                Log.d(TAG, "setData: "+list.get(x).getName());
                Log.d(TAG, "setData: "+list.get(x).getLink());

                listAll.addAll(list);
                adapter = new HistroyAdapter(mContext,listAll);
                refreshLVHistroy.setAdapter(adapter);
                refreshLVHistroy.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onDownPullRefresh() {
                        new AsyncTask<Void,Void,Void>(){
                            @Override
                            protected Void doInBackground(Void... params) {
                                SystemClock.sleep(1000);
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                adapter.notifyDataSetChanged();
                                refreshLVHistroy.hideHeaderView();
                            }
                        }.execute(new Void[]{});
                    }

                    @Override
                    public void onLoadingMore() {
                        new AsyncTask<Void,Void,Void>(){
                            @Override
                            protected Void doInBackground(Void... params) {
                                SystemClock.sleep(1000);
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                adapter.notifyDataSetChanged();
                                refreshLVHistroy.hideFooterView();
                            }
                        }.execute(new Void[]{});
                    }
                });

            }else {
                Log.d(TAG, "setData: + list为空");

            }
        }
    }

    private void initView() {
        inflateView = View.inflate(mContext, R.layout.fragment_histroy, null);
        refreshLVHistroy = (RefreshListView) inflateView.findViewById(R.id.refreshLVHistroy);
        setData(listAll);
    }
}
