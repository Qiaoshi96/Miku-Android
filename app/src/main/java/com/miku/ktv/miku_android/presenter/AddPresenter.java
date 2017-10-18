package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IAddView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/10/9.
 */

public class AddPresenter<T extends IAddView> extends BasePresenter<T> {
    public static final String TAG="AddPresenter";

    //postAdd上麦请求
    public <T> void postAdd(String roomid, Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.postAdd(roomid, sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "postAdd   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onError(throwable);
                Log.e(TAG, "postAdd-throwable:  "+throwable.toString());
            }
        });
    }

    //delete下麦请求
    public <T> void delete(String roomid, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.delete(roomid, sign, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "delete   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onDeleteSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onDeleteError(throwable);
                Log.e(TAG, "delete-throwable:  "+throwable.toString());
            }
        });
    }
}
