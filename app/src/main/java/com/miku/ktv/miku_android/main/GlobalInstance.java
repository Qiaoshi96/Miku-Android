package com.miku.ktv.miku_android.main;

import com.miku.ktv.miku_android.view.activity.KTVActivity;

/**
 * Created by lenovo on 2017/10/25.
 */

public class GlobalInstance {
    private KTVActivity mKTVActivity;

    private static class GlobalInstanceHolder {
        private static final GlobalInstance INSTANCE = new GlobalInstance();
    }

    private GlobalInstance() {
        mKTVActivity = null;
    }

    public static final GlobalInstance getInstance() {
        return GlobalInstanceHolder.INSTANCE;
    }

    public KTVActivity getKTVActivity() {
        return mKTVActivity;
    }

    public void setKTVActivity(KTVActivity activity) {
        mKTVActivity = activity;
    }

}
