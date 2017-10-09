package com.miku.ktv.miku_android.presenter;

import android.util.Log;

import com.miku.ktv.miku_android.base.BasePresenter;
import com.miku.ktv.miku_android.model.bean.AvatarBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.HttpUtil;
import com.miku.ktv.miku_android.view.iview.IRegisterInfoView;

import java.io.File;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by 焦帆 on 2017/9/26.
 */

public class RegisterInfoPresenter<T extends IRegisterInfoView> extends BasePresenter<T> {

    public static final String TAG="RegisterInfoPresenter";

    //post
    public <T> void postInfo(Map<String, String> map, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        HttpUtil.postInfo(sign, map, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "postInfo   "+s);
                T t =Constant.GsonToBean(s, cla);
                getIBaseView().onSuccess(t);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getIBaseView().onError(throwable);
                Log.e(TAG, "postInfo-throwable:  "+throwable.toString());
            }
        });
    }


    //postAvatar
    public <T extends AvatarBean> void postAvatar(String token, File avatar, final Class<T> cla){
        final String sign = "?timestamp="+ Constant.getTime()+"&sign="+ Constant.getSign();
        Log.e(TAG, "postAvatar sign="+sign);
        HttpUtil.postAvatar(sign, token, avatar, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "postAvatar   "+s);
                T t =Constant.GsonToBean(s, cla);
                if(getIBaseView()!=null){
                    getIBaseView().onAvatarSuccess(t);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(getIBaseView()!=null) {
                    getIBaseView().onAvatarError(throwable);
                }
                Log.e(TAG, "postAvatar-throwable:  "+throwable.toString());
            }
        });
    }
}
