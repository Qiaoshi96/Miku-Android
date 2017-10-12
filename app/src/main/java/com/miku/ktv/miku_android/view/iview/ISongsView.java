package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/9/26.
 */

public interface ISongsView<T,E> extends IBaseView<T>{

    void onSongsListSuccess(E t);
    void onSongsListError(Throwable t);

}
