package com.miku.ktv.miku_android.view.iview;

public interface IFetchRoomInfoView<T,E> extends IBaseView<T> {
    void onFetchRoomInfoSuccess(E t);
    void onFetchRoomInfoError(Throwable t);
}
