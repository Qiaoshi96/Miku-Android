package com.miku.ktv.miku_android.view.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LRCTextView extends RelativeLayout {

    private TextView tvDefault;
    private TextView tvSelect;

    private float percent;


    public LRCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LRCTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LRCTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        tvDefault = new TextView(getContext());
        tvDefault.setTextColor(Color.parseColor("#726463"));
        tvDefault.setEllipsize(null);
        tvDefault.setSingleLine();
        tvDefault.setTextSize(20);

        tvSelect = new TextView(getContext());
        tvSelect.setTextColor(Color.parseColor("#39DF7C"));
        tvSelect.setEllipsize(null);
        tvSelect.setSingleLine();
        tvSelect.setTextSize(20);
        addView(tvDefault);
        addView(tvSelect);
        tvSelect.setWidth(0);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    setPercent(percent);
                }
            }, 10);
        }
    }

    public void setLrc(String lrc) {
        tvDefault.setText(lrc);
        tvSelect.setText(lrc);
    }

    public void setPercent(float percent) {
        this.percent = percent;
        setSelectWidth((int) (getSelectWidth() * percent));
    }

    private int getSelectWidth() {
        return tvDefault.getWidth();
    }

    private void setSelectWidth(int pixels) {
        if (pixels <= getSelectWidth()) {
            tvSelect.setWidth(pixels);
        }
    }
}
