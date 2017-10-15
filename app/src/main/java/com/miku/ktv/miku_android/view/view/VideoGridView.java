package com.miku.ktv.miku_android.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;

/**
 * Created by lenovo on 2017/10/14.
 */

public class VideoGridView extends FrameLayout {

    /**
     *  surface view render
     */
    private AVChatSurfaceViewRenderer mSurfaceViewRender;

    /**
     *  non-video layout
     */
    private ConstraintLayout mNonVideoLayout;

    /**
     * head image
     */
    private ImageView mHeadIv;

    /**
     * volume image
     */
    private ImageView mVolumeIv;

    /**
     * name text
     */
    private TextView mNameTv;


    public VideoGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // add render
        FrameLayout.LayoutParams renderLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mSurfaceViewRender = new AVChatSurfaceViewRenderer(context);
        mSurfaceViewRender.setLayoutParams(renderLayoutParams);
        mSurfaceViewRender.setVisibility(GONE);
        this.addView(mSurfaceViewRender);

        // add mNonVideoLayout
        mNonVideoLayout = new ConstraintLayout(context);
        FrameLayout.LayoutParams nonVideoLayout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mNonVideoLayout.setLayoutParams(nonVideoLayout);
        mNonVideoLayout.setVisibility(GONE);
        this.addView(mNonVideoLayout);

        // add head image view to nonVideoLayout
        mHeadIv = new ImageView(context);
        mHeadIv.setImageResource(R.mipmap.icon_qq);
        ConstraintLayout.LayoutParams mHeadLayoutParams = new ConstraintLayout.LayoutParams(45, 45);
        mHeadLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;;
        mHeadLayoutParams.bottomMargin = 20;
        //mHeadLayoutParams.width = 45;
        //mHeadLayoutParams.height = 45;
        mHeadIv.setLayoutParams(mHeadLayoutParams);
        mNonVideoLayout.addView(mHeadIv);

        // add volume image view to nonVideoLayout
        mVolumeIv = new ImageView(context);
        mVolumeIv.setImageResource(R.drawable.bg_identify_code_normal);
        ConstraintLayout.LayoutParams mVolumeLayoutParams = new ConstraintLayout.LayoutParams(10, 50);
        mVolumeLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mVolumeLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;;
        mVolumeLayoutParams.bottomMargin = 5;
        mVolumeLayoutParams.rightMargin = 5;
        mVolumeIv.setLayoutParams(mVolumeLayoutParams);
        mNonVideoLayout.addView(mVolumeIv);

        // add name text view to nonVideoLayout
        mNameTv = new TextView(context);
        mNameTv.setText("hello");
        mNameTv.setTextColor(Color.WHITE);
        ConstraintLayout.LayoutParams mNameLayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        mNameLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameLayoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameTv.setLayoutParams(mNameLayoutParams);
        mNameLayoutParams.topMargin = 50;
        mNonVideoLayout.addView(mNameTv);
    }


    public AVChatSurfaceViewRenderer getRender() {
        return mSurfaceViewRender;
    }

    public void setHeadImage(Bitmap bitmap) {
        mHeadIv.setImageBitmap(bitmap);
    }

    public void setNameText(String name) {
        mNameTv.setText(name);
    }

    public void setVolumeImage(Bitmap bitmap) {
        mVolumeIv.setImageBitmap(bitmap);
    }

    public void setVisibility(boolean video, boolean nonVideo) {
        if (video) {
            mSurfaceViewRender.setVisibility(VISIBLE);
        } else {
            mSurfaceViewRender.setVisibility(GONE);
        }

        if (nonVideo) {
            mNonVideoLayout.setVisibility(VISIBLE);
        } else {
            mNonVideoLayout.setVisibility(GONE);
        }
    }

    public void removeRender() {
        this.removeView(mSurfaceViewRender);
    }

    public void addRendeer(AVChatSurfaceViewRenderer render) {
        mSurfaceViewRender = render;
        this.addView(mSurfaceViewRender);
    }
}
