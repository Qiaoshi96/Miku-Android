package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.ISongsView;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/10/12.
 */

public class SongsPresenter<T extends ISongsView> extends BasePresenter<T> {
    public static final String TAG="SongsPresenter";

    //get请求
    public <T> void getSongsList(final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.getSongsList(sign, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "getSongsList   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onError(throwable);
                Log.e(TAG, "getSongsList-throwable:  "+throwable.toString());
            }
        });
    }

}
