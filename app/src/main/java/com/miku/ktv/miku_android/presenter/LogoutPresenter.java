package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.ILogoutView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/10/10.
 */

public class LogoutPresenter<T extends ILogoutView> extends BasePresenter<T> {
    public static final String TAG="LogoutPresenter";

    //getExit_Room
    public <T> void getLogout(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.getLogout(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "getLogout   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onLogoutSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onLogoutError(throwable);
                Log.e(TAG, "getLogout-throwable:  "+throwable.toString());
            }
        });
    }
}
