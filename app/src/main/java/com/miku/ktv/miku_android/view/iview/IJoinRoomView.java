package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/9/26.
 */

public interface IJoinRoomView<T,E,R> extends IBaseView<T>{

    void onJoinSuccess(E t);
    void onJoinError(Throwable t);

    void onCreateSuccess(R t);
    void onCreateError(Throwable t);
}
