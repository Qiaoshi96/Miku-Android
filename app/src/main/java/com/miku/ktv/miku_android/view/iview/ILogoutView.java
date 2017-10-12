package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/9/26.
 */

public interface ILogoutView<T,E> extends IBaseView<T>{

    void onLogoutSuccess(E t);
    void onLogoutError(Throwable t);

}
