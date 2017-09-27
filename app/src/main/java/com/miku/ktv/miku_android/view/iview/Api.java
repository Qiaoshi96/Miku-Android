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

    @GET("verify/{sign}")
    Observable<String> get(@Path("sign") String sign, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("verify/{sign}")
    Observable<String> post(@Path("sign") String sign, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("register/{sign}")
    Observable<String> postInfo(@Path("sign") String sign, @FieldMap Map<String, String> map);


    @GET("room/<room_id>/{sign}")
    Observable<String> getRoom(@Path("sign") String sign, @QueryMap Map<String, String> map);

}
