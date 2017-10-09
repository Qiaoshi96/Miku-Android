package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IRegisterCheckView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/9/27.
 */

public class LoginCodePresenter<T extends IRegisterCheckView> extends BasePresenter<T> {
    public static final String TAG="LoginCodePresenter";

    //get请求
    public <T> void getSms_login(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.getSms_login(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "getSms_login   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onSendVetifyCodeSuccess(t);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onSendVetifyCodeError(throwable);

                Log.e(TAG, "getSms_login-throwable:  "+throwable.toString());
            }
        });
    }

    //postSms_login请求
    public <T> void postSms_login(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.postSms_login(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "postSms_login   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onError(throwable);
                Log.e(TAG, "postSms_login-throwable:  "+throwable.toString());
            }
        });
    }

}
