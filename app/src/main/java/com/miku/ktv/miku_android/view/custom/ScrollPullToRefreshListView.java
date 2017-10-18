package com.miku.ktv.miku_android.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.miku.ktv.miku_android.pulltorefreshlistview.PullToRefreshListView;

/**
 * Created by 焦帆 on 2017/10/14.
 */

public class ScrollPullToRefreshListView extends PullToRefreshListView {
    public ScrollPullToRefreshListView(Context context) {
        super(context);
    }

    public ScrollPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollPullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
