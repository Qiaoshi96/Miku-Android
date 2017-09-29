package com.miku.ktv.miku_android.model.utils;


import com.miku.ktv.miku_android.view.iview.Api;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public class HttpUtil {
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(Constant.LINK_MAIN)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    //get封装
    public static void get(String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.get(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //post封装
    public static void post(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.post(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }
    //postInfo
    public static void postInfo(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.postInfo(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //getSms_login
    public static void getSms_login(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getSms_login(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //postSms_login
    public static void postSms_login(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.postSms_login(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //get封装
    public static void getRooms(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getRooms(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }
}
