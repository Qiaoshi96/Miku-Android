package com.miku.ktv.miku_android.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;

/**
 * Created by lenovo on 2017/10/14.
 */

public class VideoGridView extends FrameLayout {

    /**
     * surface view render
     */
    private AVChatSurfaceViewRenderer mSurfaceViewRender;

    /**
     * non-video layout
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
     * volume shade
     */
    private LinearLayout mVolumeShade;

    /**
     * volume shade layoutParams
     */
    private FrameLayout.LayoutParams mVolumeShadeLayoutParams;

    /**
     * name text
     */
    private TextView mNameTv;

    private float mScale;

    private Bitmap mVolumeBitmap;


    public VideoGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mScale = context.getResources().getDisplayMetrics().density;
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
        ConstraintLayout.LayoutParams mHeadLayoutParams = new ConstraintLayout.LayoutParams((int) (30 * mScale), (int) (30 * mScale));
        mHeadLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        mHeadLayoutParams.bottomMargin = (int) (10 * mScale);
        mHeadIv.setLayoutParams(mHeadLayoutParams);
        mNonVideoLayout.addView(mHeadIv);

        // add name text view to nonVideoLayout
        mNameTv = new TextView(context);
        mNameTv.setText("hello");
        mNameTv.setTextColor(Color.WHITE);
        mNameTv.setTextSize(9);
        ConstraintLayout.LayoutParams mNameLayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        mNameLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameLayoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        mNameTv.setLayoutParams(mNameLayoutParams);
        mNameLayoutParams.topMargin = (int) (30 * mScale);
        mNonVideoLayout.addView(mNameTv);


        // volume Layout
        FrameLayout fl = new FrameLayout(context);
        ConstraintLayout.LayoutParams flLayoutParams = new ConstraintLayout.LayoutParams((int) (9 * mScale), (int) (30 * mScale));
        flLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        flLayoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        flLayoutParams.bottomMargin = (int) (5 * mScale);
        flLayoutParams.rightMargin = (int) (5 * mScale);
        fl.setLayoutParams(flLayoutParams);
        mNonVideoLayout.addView(fl);


        // add volume image view to frame layout
        mVolumeIv = new ImageView(context);
        mVolumeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.volume);
        mVolumeIv.setImageBitmap(mVolumeBitmap);
        FrameLayout.LayoutParams volumeLayoutParams  = new FrameLayout.LayoutParams((int)(9 * mScale), (int) (30 * mScale));
        mVolumeIv.setLayoutParams(volumeLayoutParams);
        fl.addView(mVolumeIv);

        // add volume shader to frame layout
        mVolumeShade = new LinearLayout(context);
        ColorDrawable colorDrawable= (ColorDrawable) this.getBackground();
        mVolumeShade.setBackgroundColor(colorDrawable.getColor());
        mVolumeShadeLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mVolumeShade.setLayoutParams(mVolumeShadeLayoutParams);
        fl.addView(mVolumeShade);
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

    public void setVolume(int percent) {
        mVolumeShadeLayoutParams.height = mVolumeIv.getHeight() * (100 - percent) / 100;
        mVolumeShade.setLayoutParams(mVolumeShadeLayoutParams);
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
