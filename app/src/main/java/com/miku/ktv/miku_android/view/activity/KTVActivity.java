package com.miku.ktv.miku_android.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miku.ktv.miku_android.R;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;

public class KTVActivity extends AppCompatActivity  implements View.OnClickListener{

    private ImageView mIvback;
    private LinearLayout mLlPaimailist;
    private LinearLayout mLlDiangelist;
    private ImageView mIvmore;

    private AVChatCameraCapturer videoCapturer; // 视频采集模块
    AVChatSurfaceViewRenderer masterRender; // 自己的画布

    private ViewGroup MyVideoLayout;
    private ImageView mIvVideo;

    private boolean isOpen = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktv);

        //初始化控件findViewById
        initView();
        //加载监听器
        initListener();
    }

    private void initListener() {
        mIvback.setOnClickListener(this);
        mLlDiangelist.setOnClickListener(this);
        mLlPaimailist.setOnClickListener(this);
        mIvmore.setOnClickListener(this);
        mIvVideo.setOnClickListener(this);
    }
    private void initView() {
        //返回键的控件
        mIvback = (ImageView) findViewById(R.id.iv_back);
        //排麦的控件
        mLlPaimailist = (LinearLayout) findViewById(R.id.ll_paimailist);
        //点歌的控件
        mLlDiangelist = (LinearLayout) findViewById(R.id.ll_diangelist);
        //更多按钮
        mIvmore = (ImageView) findViewById(R.id.iv_more);
        //开启摄像头按钮
        mIvVideo = (ImageView) findViewById(R.id.iv_video);
        //第一个摄像头区域
        MyVideoLayout = (ViewGroup) findViewById(R.id.v1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回键的点击事件
            case R.id.iv_back:
                finish();
                break;
            //排麦的点击事件
            case R.id.ll_diangelist:

                break;
            //点歌的点击事件
            case R.id.ll_paimailist:

                break;
            case R.id.iv_more:
                showDialog();
                break;
            case R.id.iv_video:

               /* if (isOpen = false){
                openVideo();
                    isOpen = !isOpen;
                }else{
                    closeVideo();
                    isOpen = !isOpen;
                }*/
               openVideo();
                break;

        }
    }

    private void closeVideo() {
        //关闭视频预览
        AVChatManager.getInstance().stopVideoPreview();
        //关闭音视频引擎
        AVChatManager.getInstance().disableRtc();
    }

    private void openVideo() {
        AVChatManager.getInstance().enableRtc();
        // 打开视频模块
        AVChatManager.getInstance().enableVideo();
        // 设置视频采集模块
        if (videoCapturer == null) {
            videoCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
            AVChatManager.getInstance().setupVideoCapturer(videoCapturer);
        }
        //
        if (masterRender == null) {
            masterRender = new AVChatSurfaceViewRenderer(this);
        }
        addIntoMasterPreviewLayout(masterRender);
        AVChatManager.getInstance().setupLocalVideoRender(masterRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        //打开视频预览
        AVChatManager.getInstance().startVideoPreview();

    }
    //把自己添加到画布
    private void addIntoMasterPreviewLayout(SurfaceView surfaceView) {
        if (surfaceView.getParent()!=null)
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
            MyVideoLayout.addView(surfaceView);
        surfaceView.setZOrderMediaOverlay(true);

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KTVActivity.this);
        builder.setMessage("告诉我们你需要的功能，我们会使这款产品更加完善哦~");
        builder.setTitle("你希望这里有什么功能");
        builder.setPositiveButton("反馈", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(KTVActivity.this,SuggestionsActivity.class);
                startActivity(intent);

            }

        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });

        builder.create().show();

    }
}

