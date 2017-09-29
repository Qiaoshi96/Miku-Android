package com.miku.ktv.miku_android.view.iview;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public interface Api {
    //获取注册验证码
    @GET("verify/{sign}")
    Observable<String> get(@Path("sign") String sign, @QueryMap Map<String, String> map);
    //效验注册验证码
    @FormUrlEncoded
    @POST("verify/{sign}")
    Observable<String> post(@Path("sign") String sign, @FieldMap Map<String, String> map);
    //获取注册信息：头像，昵称，性别，token
    @FormUrlEncoded
    @POST("register/{sign}")
    Observable<String> postInfo(@Path("sign") String sign, @FieldMap Map<String, String> map);
    //获取登录验证码
    @GET("sms_login/{sign}")
    Observable<String> getSms_login(@Path("sign") String sign, @QueryMap Map<String, String> map);
    //效验登录验证码
    @FormUrlEncoded
    @POST("sms_login/{sign}")
    Observable<String> postSms_login(@Path("sign") String sign, @FieldMap Map<String, String> map);
    //获取全部房间列表
    @GET("rooms/{sign}")
    Observable<String> getRooms(@Path("sign") String sign, @QueryMap Map<String, String> map);

}
