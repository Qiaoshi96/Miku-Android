package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/10/18.
 */

public interface IAddView<T,E,R> extends IBaseView<T> {

    void onDeleteSuccess(E t);
    void onDeleteError(Throwable throwable);

    void onAddListSuccess(R t);
    void onAddListError(Throwable throwable);
}
