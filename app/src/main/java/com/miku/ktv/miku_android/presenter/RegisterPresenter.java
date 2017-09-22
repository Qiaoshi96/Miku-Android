package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IBaseView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public class RegisterPresenter extends BasePresenter<IBaseView> {

    public static final String TAG="RegisterPresenter";
    //get请求
    public <T> void get(final String url, Map<String, String> map, final Class<T> cla){
        HttpUtil.get(url, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, url+"   "+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "-throwable: "+url+"   "+throwable.toString());
            }
        });
    }

    //post请求
    public <T> void post(final String url, Map<String, String> map,final Class<T> cla){
        HttpUtil.post(url, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", url+"   "+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "-throwable: "+url+"   "+throwable.toString());
            }
        });
    }
}
