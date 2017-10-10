package com.miku.ktv.miku_android.view.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.miku.ktv.miku_android.R;


/**
 * Created by 焦帆 on 2017/10/10.
 */

public class ProgressDialogLoading extends ProgressDialog {
    public ProgressDialogLoading(Context context) {
        super(context);
    }

    public ProgressDialogLoading(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog(getContext());
    }

    private void initDialog(Context context) {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_progress);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
