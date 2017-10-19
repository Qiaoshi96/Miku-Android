package com.miku.ktv.miku_android.model.utils;


import com.miku.ktv.miku_android.view.iview.Api;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    //get验证注册验证码
    public static void get(String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.get(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //post获取注册验证码
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

    //getRooms获取房间列表
    public static void getRooms(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getRooms(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //postAvatar上传头像到服务器
    public static void postAvatar(String sign, String token, File avatar, Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("avatar", avatar.getName(), RequestBody.create(MediaType.parse("image/*"), avatar))
                .build();
        Observable<String> observable = api.postAvatar(sign,requestBody);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //room/room_id进入聊天室
    public static void getRoom_id(String roomid, String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError){
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getRoom_id(roomid,sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //getExitRoom退出房间
    public static void getExitRoom(String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getExitRoom(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //getLogout退出登录
    public static void getLogout(String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getLogout(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //postNick
    public static void postNick(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.postNick(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //getSongsList获取歌曲列表
    public static void getSongsList(String sign, Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getSongsList(sign);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //getSearch
    public static void getSearch(String sign, Map<String, String> map,Consumer<String> onNext, Consumer<Throwable> onError) {
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getSearch(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //room/room_id/singer/create上麦
    public static void postAdd(String roomid, String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError){
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.postAdd(roomid,sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //room/room_id/singers下麦
    public static void delete(String roomid, String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError){
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.delete(roomid,sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //room/room_id/singers获取排麦列表
    public static void getAddList(String roomid, String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError){
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getAddList(roomid,sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

    //room/room_id/singers获取排麦列表
    public static void getHeart(String sign, Map<String, String> map, Consumer<String> onNext, Consumer<Throwable> onError){
        Api api = retrofit.create(Api.class);
        Observable<String> observable = api.getHeart(sign,map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError);
    }

}
