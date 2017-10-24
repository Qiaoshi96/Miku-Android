package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public interface IBaseView<T> {
    void onSuccess(T t);
    void onError(Throwable t);
}