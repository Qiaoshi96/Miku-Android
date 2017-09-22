package com.miku.ktv.miku_android.view.iview;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public interface Api {

    @GET()
    Observable<String> get(@Url String url, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, String> map);

}
