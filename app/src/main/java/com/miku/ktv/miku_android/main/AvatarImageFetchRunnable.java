package com.miku.ktv.miku_android.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.miku.ktv.miku_android.view.activity.KTVActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2017/10/15.
 */

public class AvatarImageFetchRunnable implements Runnable {
    String account;
    String path;
    FetchAvatarImageCallBack callBack;
    Handler handler;

    public AvatarImageFetchRunnable(String account, String path, FetchAvatarImageCallBack callBack, Handler handler) {
        this.account = account;
        this.path = path;
        this.callBack = callBack;
        this.handler = handler;
    }
    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            final String message = conn.getResponseMessage();
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onAvatarImageFetchSuccess(account, bitmap);
                    }
                });

            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onAvatarImageFetchFailed(account, path, message);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            final String message = e.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onAvatarImageFetchFailed(account, path, message);
                }
            });
        }
    }

    public interface FetchAvatarImageCallBack {
        void onAvatarImageFetchSuccess(String account, Bitmap bitmap);
        void onAvatarImageFetchFailed(String account, String path, String message);
    }
}

