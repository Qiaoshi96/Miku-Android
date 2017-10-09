package com.miku.ktv.miku_android.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.RegisterInfoBean;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.constant.AVChatResCode;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;

import java.util.Map;


public class KTVActivity extends AppCompatActivity  implements View.OnClickListener ,AVChatStateObserver{

    private ImageView mIvback;
    private LinearLayout mLlPaimailist;
    private LinearLayout mLlDiangelist;
    private ImageView mIvmore;

    private AVChatCameraCapturer videoCapturer; // 视频采集模块
    AVChatSurfaceViewRenderer masterRender; // 自己的画布

    private ViewGroup MyVideoLayout;
    private ImageView mIvVideo;

    private boolean isOpen = false ;
    private final int CAMERA_OK = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktv);

        //RoomBean roomBean = new RoomBean();
        //String room_id = roomBean.getBody().getRoom_id();
        //加入房间
        //enterRoom(room_id);


        //初始化控件findViewById
        initView();
        //加载监听器
        initListener();
        //inithead();
        //检查权限
        ccheckPermission();

    }

    private void enterRoom(String roomId) {
        AVChatManager.getInstance().joinRoom2(roomId, AVChatType.VIDEO, new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                // 设置音量信号监听, 通过AVChatStateObserver的onReportSpeaker回调音量大小
                AVChatParameters avChatParameters = new AVChatParameters();
                avChatParameters.setBoolean(AVChatParameters.KEY_AUDIO_REPORT_SPEAKER, true);
                AVChatManager.getInstance().setParameters(avChatParameters);
            }

            @Override
            public void onFailed(int i) {

                Toast.makeText(KTVActivity.this, "join channel failed, code:" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    //检查版本权限问题
    private void ccheckPermission() {
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(KTVActivity.this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(KTVActivity.this,
                        new String[]{android.Manifest.permission.CAMERA}, CAMERA_OK);
            }
        }
    }

//显示头像和姓名等信息。
    private void inithead() {
        RegisterInfoBean registerInfoBean = new RegisterInfoBean();
        int ID = registerInfoBean.getBody().getFullname();

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
            //显示摄像头的方法
            case R.id.iv_video:
                if (isOpen==false){
                    openVideo();
                    isOpen = true;
                }else{
                    closeVideo();
                    isOpen = false;
                }
                break;

        }
    }

    //关闭摄像头
    private void closeVideo() {
        AVChatManager.getInstance().stopVideoPreview();
        AVChatManager.getInstance().disableVideo();
        /*//关闭音视频引擎
        AVChatManager.getInstance().disableRtc();*/
        //移除画布
        MyVideoLayout.removeAllViews();
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

//当前音视频服务器连接回调
    @Override
    public void onJoinedChannel(int code, String audioFile, String videoFile, int elapsed) {
        if (code != AVChatResCode.JoinChannelCode.OK) {
            Toast.makeText(this, "joined channel:" + code, Toast.LENGTH_SHORT).show();
        }
    }

//加入当前音视频频道用户帐号回调
    @Override
    public void onUserJoined(String account) {

    }
//当前用户离开频道回调
    @Override
    public void onUserLeave(String account, int event) {

    }
//自己成功离开频道回调
    @Override
    public void onLeaveChannel() {

    }
//版本协议不兼容回调
    @Override
    public void onProtocolIncompatible(int status) {

    }
//服务器断开回调
    @Override
    public void onDisconnectServer() {

    }
//当前通话网络状况回调
    @Override
    public void onNetworkQuality(String user, int quality, AVChatNetworkStats stats) {

    }
//音视频连接成功建立回调
    @Override
    public void onCallEstablished() {

    }
//音视频设备状态通知
    @Override
    public void onDeviceEvent(int code, String desc) {

    }
//截图结果回调
    @Override
    public void onTakeSnapshotResult(String account, boolean success, String file) {

    }
//本地网络类型发生改变回调
    @Override
    public void onConnectionTypeChanged(int netType) {

    }
//音视频录制回调
    //当用户录制音视频结束时回调，会通知录制的用户id和录制文件路径。
    @Override
    public void onAVRecordingCompletion(String account, String filePath) {

    }
//当用户录制语音结束时回调，会通知录制文件路径。
    //当用户录制语音结束时回调，会通知录制文件路径。
    @Override
    public void onAudioRecordingCompletion(String filePath) {

    }
//当用户录制语音结束时回调，会通知录制文件路径。
    //当存储空间不足时的警告回调,存储空间低于20M时开始出现警告，出现警告时请及时关闭所有的录制服务，当存储空间低于10M时会自动关闭所有的录制。
    @Override
    public void onLowStorageSpaceWarning(long availableSize) {

    }
//用户第一帧画面通知
    @Override
    public void onFirstVideoFrameAvailable(String account) {

    }
//用户视频画面分辨率改变通知
    @Override
    public void onFirstVideoFrameRendered(String user) {

    }
//用户视频画面分辨率改变通知
    @Override
    public void onVideoFrameResolutionChanged(String user, int width, int height, int rotate) {

    }
//用户视频帧率汇报
    @Override
    public void onVideoFpsReported(String account, int fps) {

    }
//采集视频数据回调
    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
        return false;
    }
//采集语音数据回调
    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
        return false;
    }
//语音播放设备变化通知
    @Override
    public void onAudioDeviceChanged(int device) {

    }
//语音正在说话用户声音强度通知
    @Override
    public void onReportSpeaker(Map<String, Integer> speakers, int mixedEnergy) {

    }
//伴音事件通知
    @Override
    public void onAudioMixingEvent(int event) {

    }
//实时统计信息汇报
    @Override
    public void onSessionStats(AVChatSessionStats sessionStats) {

    }
//互动直播事件通知
    @Override
    public void onLiveEvent(int event) {

    }
}

