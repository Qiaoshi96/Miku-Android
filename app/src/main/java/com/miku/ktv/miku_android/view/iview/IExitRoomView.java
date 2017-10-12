package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/9/26.
 */

public interface IExitRoomView<T,E> extends IBaseView<T>{

    void onExitRoomSuccess(E t);
    void onExitRoomError(Throwable t);

}
