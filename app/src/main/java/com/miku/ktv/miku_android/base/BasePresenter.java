package com.miku.ktv.miku_android.base;


/**
 * Created by 焦帆 on 2017/9/22.
 */

//public class BasePresenter<T extends IBaseView> {
public class BasePresenter<T> {
    private T t;

    //关联activity
    public void attach(T t){
        this.t=t;
    }

//    public IBaseView getIBaseView() {
    public T getIBaseView() {
        return t;
    }

    //取消关联activity
    public void detach(){
        if(this.t!=null){
            t=null;
        }
    }
}
