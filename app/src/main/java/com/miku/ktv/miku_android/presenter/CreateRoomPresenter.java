package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IJoinRoomView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/9/28.
 */

public class CreateRoomPresenter<T extends IJoinRoomView> extends BasePresenter<T> {
    public static final String TAG="CreateRoomPresenter";

    //get请求
    public <T> void getRooms(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.getRooms(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "getRooms   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onError(throwable);
                Log.e(TAG, "getRooms-throwable:  "+throwable.toString());
            }
        });
    }

    //getRoom_id
    public <T> void getRoom_id(String roomid, Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        Log.e(TAG, "getRoom_id sign="+sign);
        HttpUtil.getRoom_id(roomid, sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "getRoom_id   "+s);
                T t =Constant.GsonToBean(s, cla);
                if(getIBaseView()!=null){
                    getIBaseView().onJoinSuccess(t);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(getIBaseView()!=null) {
                    getIBaseView().onJoinError(throwable);
                }
                Log.e(TAG, "getRoom_id-throwable:  "+throwable.toString());
            }
        });
    }

    //post创建房间请求
    public <T> void postCreate(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.postCreate(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "postCreate   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onCreateSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onCreateError(throwable);
                Log.e(TAG, "postCreate-throwable:  "+throwable.toString());
            }
        });
    }

}
