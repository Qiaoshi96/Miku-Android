package com.miku.ktv.miku_android.base;


import com.miku.ktv.miku_android.view.iview.IBaseView;

/**
 * Created by 焦帆 on 2017/9/22.
 */

public class BasePresenter<T extends IBaseView> {
    private T t;

    //关联activity
    public void attach(T t){
        this.t=t;
    }

    public IBaseView getIBaseView() {
        return t;
    }

    //取消关联activity
    public void detach(){
        if(this.t!=null){
            t=null;
        }
    }
}
