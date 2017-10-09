package com.miku.ktv.miku_android.view.iview;

/**
 * Created by 焦帆 on 2017/9/24.
 */

public interface IRegisterCheckView<T, E> extends IBaseView<T>{

    void onSendAgainSuccess(E t);
    void onSendAgainError(E t);

}
