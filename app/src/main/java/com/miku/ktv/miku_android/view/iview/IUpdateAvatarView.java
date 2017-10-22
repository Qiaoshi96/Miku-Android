package com.miku.ktv.miku_android.view.iview;

import com.miku.ktv.miku_android.model.bean.AvatarBean;

/**
 * Created by 焦帆 on 2017/10/22.
 */

public interface IUpdateAvatarView<T> extends IBaseView<T> {
    void onAvatarSuccess(AvatarBean bean);
    void onAvatarError(Throwable t);
}
