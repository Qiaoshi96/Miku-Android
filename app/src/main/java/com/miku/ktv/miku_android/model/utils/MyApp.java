package com.miku.ktv.miku_android.model.utils;

import android.app.Application;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constant.init(this);
    }
}
