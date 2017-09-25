package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
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
    public <T> void get(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.get(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, sign+"   "+s);
                T t =Constant.GsonToBean(s, cla);
                Log.d(TAG, "accept: "+t.toString());
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "-throwable: "+sign+"   "+throwable.toString());
            }
        });
    }

    //post请求
    public <T> void post(Map<String, String> map,final Class<T> cla){
        final String sign = "?timestamp="+Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.post(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", sign+"   "+s);
                T t =Constant.GsonToBean(s, cla);
                Log.d(TAG, "accept: "+t.toString());
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "-throwable: "+sign+"   "+throwable.toString());
            }
        });
    }
}
