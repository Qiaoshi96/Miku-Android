package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IExitRoomView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/10/10.
 */

public class ExitRoomPresenter<T extends IExitRoomView> extends BasePresenter<T> {
    public static final String TAG="ExitRoomPresenter";

    //getExit_Room
    public <T> void getExitRoom(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.getExitRoom(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "getExitRoom   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onExitRoomSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onExitRoomError(throwable);
                Log.e(TAG, "getExitRoom-throwable:  "+throwable.toString());
            }
        });
    }
}
