package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IFetchRoomInfoView;

import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by lenovo on 2017/10/11.
 */


public class FetchRoomInfoPresenter<T extends IFetchRoomInfoView> extends BasePresenter<T> {
    public static final String TAG = "FetchRoomInfoPresenter";
    public <T> void fetchRoomInfo(String roomid, Map<String, String> map, final Class<T> cla) {
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        Log.e(TAG, "fetchRoomInfo sign=" + sign);
        HttpUtil.getRoom_id(roomid, sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "fetchRoomInfo.accept   " + s);
                T t =Constant.GsonToBean(s, cla);
                if(getIBaseView()!=null){
                    getIBaseView().onFetchRoomInfoSuccess(t);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(getIBaseView()!=null) {
                    getIBaseView().onFetchRoomInfoError(throwable);
                }
                Log.e(TAG, "getRoom_id-throwable:  " + throwable.toString());
            }
        });
    }
}
