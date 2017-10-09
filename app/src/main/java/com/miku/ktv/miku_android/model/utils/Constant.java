package com.miku.ktv.miku_android.model.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class Constant {
    public static final String TAG="Constant";
    public static String SYSTEM_SHARE_NAME="config";
    public static final String LINK_MAIN = "http://ktv.fibar.cn/api/v1/";
    public static final String BASE_PIC_URL = "http://ktv.fibar.cn";
    public static final String SECRET = "eKxzhDfGkvcv9MaLQdeWgSlqnX4CosiIkR17Z0oAPmNUjBOw6nlHcTfupzbFhupy";
    public static SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mSharedPreferencesEditor;
    public static Gson gson;
    private static final int DEFAULT_TIMEOUT = 20;

    //初始化
    public static void init(Context context) {
        mSharedPreferences = context.getSharedPreferences(Constant.SYSTEM_SHARE_NAME, Activity.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
        gson=new Gson();
    }
    //gson封装
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
    //通过时间戳+sercet得到sign
    public static String getSign() {

        //得到sign
        String sign = MD5Util.getStringMD5(getTime()+Constant.SECRET);

        Log.d(TAG, "sign是: "+sign);
        return sign;
    }

    //获取系统时间的10位的时间戳
    public static String getTime(){
        long time=System.currentTimeMillis()/1000;
        Log.d(TAG, "时间戳是: "+time);
        return String.valueOf(time);
    }
}