package com.miku.ktv.miku_android.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.AddBean;
import com.miku.ktv.miku_android.model.bean.AddListBean;
import com.miku.ktv.miku_android.model.bean.DeleteBean;
import com.miku.ktv.miku_android.model.bean.SearchBean;
import com.miku.ktv.miku_android.view.iview.IAddView;

public class TextActivity extends AppCompatActivity implements IAddView<AddBean, DeleteBean, SearchBean>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
    }

    @Override
    public void onSuccess(AddBean addBean) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onDeleteSuccess(DeleteBean t) {

    }

    @Override
    public void onDeleteError(Throwable throwable) {

    }

    @Override
    public void onAddListSuccess(SearchBean t) {

    }

    @Override
    public void onAddListError(Throwable throwable) {

    }
}
