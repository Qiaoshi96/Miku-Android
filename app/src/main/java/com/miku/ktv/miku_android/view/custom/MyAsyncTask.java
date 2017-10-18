package com.miku.ktv.miku_android.view.custom;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import com.miku.ktv.miku_android.model.bean.SongsListBean;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.DownloadCallback;
import com.othershe.dutil.download.DownloadManger;

import java.io.File;
import java.util.List;

/**
 * Created by 焦帆 on 2017/10/14.
 */

public class MyAsyncTask extends AsyncTask<SongsListBean.BodyBean.SongListBean, Integer, Void>{
    private SongsListBean.BodyBean.SongListBean bean;

    private Context context;
    private List<View> viewList;
    private Integer viewId;

    private DownloadManger downloadManger;

    public MyAsyncTask(Context context, List<View> viewList, Integer viewId) {
        this.context=context;
        this.viewList = viewList;
        this.viewId = viewId;
    }

    @Override
    protected Void doInBackground(SongsListBean.BodyBean.SongListBean... params) {
        bean=params[0];
        downloadManger= DUtil.init(context)
                .url(bean.getLink())
                .path(Environment.getExternalStorageDirectory() + "/MiDoDownUtil/")
                .name(bean.getName() + "banzou.mp3")
                .childTaskCount(3)
                .build()
                .start(new DownloadCallback() {
                    @Override
                    public void onStart(long currentSize, long totalSize, float progress) {
                        
                    }

                    @Override
                    public void onProgress(long currentSize, long totalSize, float progress) {

                    }

                    @Override
                    public void onPause() {

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onFinish(File file) {

                    }

                    @Override
                    public void onWait() {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });

        return null;
    }
}
