package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.ExitRoomBean;
import com.miku.ktv.miku_android.model.bean.JoinRoomBean;
import com.miku.ktv.miku_android.model.bean.RegisterInfoBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.ExitRoomPresenter;
import com.miku.ktv.miku_android.presenter.FetchRoomInfoPresenter;
import com.miku.ktv.miku_android.view.iview.IExitRoomView;
import com.miku.ktv.miku_android.view.iview.IFetchRoomInfoView;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.constant.AVChatResCode;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatUserRole;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KTVActivity extends Activity implements IExitRoomView<Object,ExitRoomBean>, IFetchRoomInfoView<Object, JoinRoomBean>, View.OnClickListener, AVChatStateObserver {
    private static final String TAG = KTVActivity.class.getName();

    /**
     * 返回键
     */
    private ImageView backBtn;

    /**
     *
     */
    private LinearLayout paimaillistLayout;
    /**
     * 点歌布局
     */
    private LinearLayout diangelistLayout;

    /**
     * 更多按钮
     */
    private ImageView moreIv;

    /**
     * 视频开关按钮
     */
    private ImageView videoSwitchIv;
    
    
    /**
     * 视频采集器，采用相机，可以放大缩小，也可以切换摄像头，可以聚焦
     */
    private AVChatCameraCapturer mVideoCapturer; // 视频采集模块
    
    /**
     * 房间视频画布渲染列表
     */
    Map<String, AVChatSurfaceViewRenderer> mRenderMap = null; // 自己的画布


    /**
     * 是否开启视频，true表示开启视频， false表示关闭视频
     */
    private boolean mVideoSwitch = false ;

    /**
     * 退出房间操作
     */
    private ExitRoomPresenter mExitRoomPresenter;

    /**
     * 获取最新房间信息
     */
    private FetchRoomInfoPresenter mFetchRoomInfoPresenter;

    /**
     * activity 共享数据
     */
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * 房间名称，进入网易云房间的参数
     */
    private String mRoomName;

    /**
     * self account
     */
    private String mAccount;

    private int testIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktv);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        mRoomName = sp.getString("roomname", "");
        mAccount = sp.getString("account", null);
        Log.e(TAG, "onCreate: " + mRoomName + ", " + mAccount);

        //初始化控件findViewById
        initView();
        //加载监听器
        initListener();
        //绑定ExitRoomPresenter
        bindPresenter();
        //inithead();
        //检查权限
        ccheckPermission();

        mRenderMap = new HashMap<>();
    }

    private void bindPresenter() {
        mExitRoomPresenter = new ExitRoomPresenter();
        mExitRoomPresenter.attach(this);

        mFetchRoomInfoPresenter = new FetchRoomInfoPresenter();
        mFetchRoomInfoPresenter.attach(this);
    }

    //检查版本权限问题
    private void ccheckPermission() {
        if (Build.VERSION.SDK_INT > 22){
            if (ContextCompat.checkSelfPermission(KTVActivity.this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(KTVActivity.this,
                        new String[]{android.Manifest.permission.CAMERA}, Constant.CAMERA_OK);
            }
        }
    }

//显示头像和姓名等信息。
    private void inithead() {
        RegisterInfoBean registerInfoBean = new RegisterInfoBean();
        int ID = registerInfoBean.getBody().getFullname();

    }

    private void initListener() {
        backBtn.setOnClickListener(this);
        diangelistLayout.setOnClickListener(this);
        paimaillistLayout.setOnClickListener(this);
        moreIv.setOnClickListener(this);
        videoSwitchIv.setOnClickListener(this);
    }
    private void initView() {
        //返回键的控件
        backBtn = (ImageView) findViewById(R.id.iv_back);
        //排麦的控件
        paimaillistLayout = (LinearLayout) findViewById(R.id.ll_paimailist);
        //点歌的控件
        diangelistLayout = (LinearLayout) findViewById(R.id.ll_diangelist);
        //更多按钮
        moreIv = (ImageView) findViewById(R.id.iv_more);
        //开启摄像头按钮
        videoSwitchIv = (ImageView) findViewById(R.id.iv_video);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回键的点击事件
            case R.id.iv_back:
                onBackButtonPressed();
                break;
            //点歌的点击事件
            case R.id.ll_diangelist:
                startActivity(new Intent(this,SongsActivity.class));
                break;
            //排麦的点击事件
            case R.id.ll_paimailist:

                break;
            case R.id.iv_more:
                showDialog();
                break;
            //显示摄像头的方法
            case R.id.iv_video:
                if (mVideoSwitch==false){
                    openVideo();
                    mVideoSwitch = true;
                }else{
                    closeVideo();
                    mVideoSwitch = false;
                }
                break;
        }
    }

    /**
     * 开启视频聊天
     */
    private void openVideo() {
        //1.创建房间
        AVChatManager.getInstance().createRoom(mRoomName, null, new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                Log.e(TAG, "createRoom.onSuccess");
            }

            @Override
            public void onFailed(int code) {
                Log.e(TAG, "createRoom.onFailed: " + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.e(TAG, "createRoom.onException: ", exception);
            }
        });

        //2. 注册音视频模块监听
        AVChatManager.getInstance().observeAVChatState(this, true);

        //3. 开启音视频引擎
        AVChatManager.getInstance().enableRtc();

        //4. 设置场景, 如果需要高清音乐场景，设置 AVChatChannelProfile#CHANNEL_PROFILE_HIGH_QUALITY_MUSIC
        //AVChatManager.getInstance().setChannelProfile(AVChatChannelProfile.CHANNEL_PROFILE_DEFAULT);

        //5. 设置互动直播模式，设置互动直播推流地址 [仅限互动直播] AVChatParameters.KEY_SESSION_LIVE_MODE, AVChatParameters.KEY_SESSION_LIVE_URL。

        //6. 打开视频模块
        AVChatManager.getInstance().enableVideo();

        //7. 设置视频采集模块
        AVChatCameraCapturer videoCapturer = AVChatVideoCapturerFactory.createCameraCapturer();
        AVChatManager.getInstance().setupVideoCapturer(videoCapturer);

        //8. 设置本地预览画布
        mRenderMap.put(mAccount,  new AVChatSurfaceViewRenderer(this));
        AVChatManager.getInstance().setupLocalVideoRender(mRenderMap.get(mAccount), false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);

        //9. 设置视频通话可选参数[可以不设置]
        //AVChatParameters parameters = new AVChatParameters();
        //AVChatManager.getInstance().setParameters(parameters);

        //10. 开启视频预览
        AVChatManager.getInstance().startVideoPreview();

        //11.角色设置
        AVChatManager.getInstance().setParameter(AVChatParameters.KEY_SESSION_MULTI_MODE_USER_ROLE, AVChatUserRole.NORMAL);

        //11. 加入房间
        AVChatManager.getInstance().joinRoom2(mRoomName, AVChatType.VIDEO, new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                Log.e(TAG, "join channel success, extra:" + avChatData.getExtra());
                // 设置音量信号监听, 通过AVChatStateObserver的onReportSpeaker回调音量大小
                AVChatParameters avChatParameters = new AVChatParameters();
                avChatParameters.setBoolean(AVChatParameters.KEY_AUDIO_REPORT_SPEAKER, true);
                AVChatManager.getInstance().setParameters(avChatParameters);
                Log.e(TAG, Environment.getExternalStorageDirectory().getPath() + "/test.mp4");
                AVChatManager.getInstance().startAudioMixing(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.mp3", false, false, 0, 0.5f);
            }

            @Override
            public void onFailed(int code) {
                Log.e(TAG, "joinRoom2.onFailed: " + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.e(TAG, "joinRoom2.onException", exception);
            }
        });
    }

    /**
     * 关闭视频聊天
     */
    private void closeVideo() {
        mRenderMap.clear();
        clearVideoLayout();
        AVChatManager.getInstance().setupLocalVideoRender(null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        AVChatManager.getInstance().stopVideoPreview();

        AVChatManager.getInstance().disableVideo();
        AVChatManager.getInstance().leaveRoom2(mRoomName, new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "leaveRoom.onSuccess");
            }

            @Override
            public void onFailed(int code) {
                Log.e(TAG, "leaveRoom.onFailed: " + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.e(TAG, "leaveRoom.onException", exception);
            }
        });
        //关闭音视频引擎
        AVChatManager.getInstance().disableRtc();
    }


    /**
     * 从服务器获取房间内参加者列表
     */
    private void getParticipantsFromServer() {
        //请求加入聊天室接口
        HashMap<String,String> map=new HashMap<>();
        map.put("token",sp.getString("LoginToken",""));
        mFetchRoomInfoPresenter.fetchRoomInfo(mRoomName, map, JoinRoomBean.class);
    }

    @Override
    public void onFetchRoomInfoSuccess(JoinRoomBean t) {
        List<JoinRoomBean.BodyBean.ParticipantsBean> participants = t.getBody().getParticipants();
        adjustParticipantPosition(participants);
    }

    @Override
    public void onFetchRoomInfoError(Throwable t) {
        Log.e(TAG, "onFetchInfoError", t);
    }

    /**
     * 调整每个成员的位置
     * @param participants
     */
    private void adjustParticipantPosition(List<JoinRoomBean.BodyBean.ParticipantsBean> participants) {
        clearVideoLayout();
        Resources res = getResources();
        for(int i = 0; i < participants.size(); i++) {
            Log.e(TAG, "adjustParticipantPosition" + participants.get(i).getFullname());
            AVChatSurfaceViewRenderer render = mRenderMap.get(participants.get(i).getFullname());
            if (render != null) {
                int resIdentifier = res.getIdentifier("v" + (i + 1), "id", getPackageName());
                ViewGroup vg = (ViewGroup) findViewById(resIdentifier);
                vg.addView(render);
                render.setZOrderMediaOverlay(true);
            }
        }
    }

    private void clearVideoLayout() {
        Resources res = getResources();
        for (int i = 0; i < 12; i++) {
            int resIdentifier = res.getIdentifier("v" + (i+1), "id", getPackageName());
            ViewGroup vg = (ViewGroup)findViewById(resIdentifier);
            vg.removeAllViews();
        }
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
        Log.e(TAG, "onJoinedChannel" + sp.getString("account", null));
        if (code != AVChatResCode.JoinChannelCode.OK) {
            Toast.makeText(this, "joined channel:" + code, Toast.LENGTH_SHORT).show();
        }
        getParticipantsFromServer();
    }

    //加入当前音视频频道用户帐号回调
    @Override
    public void onUserJoined(String account) {
        Log.e(TAG, "onUserJoined" + account);
        mRenderMap.put(account,  new AVChatSurfaceViewRenderer(this));
        AVChatManager.getInstance().setupRemoteVideoRender(account, mRenderMap.get(account), false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        getParticipantsFromServer();
    }
    //当前用户离开频道回调
    @Override
    public void onUserLeave(String account, int event) {
        Log.e(TAG, "onUserLeave" + account);
        mRenderMap.remove(account);
        getParticipantsFromServer();
    }
    //自己成功离开频道回调
    @Override
    public void onLeaveChannel() {
        Log.e(TAG, "onLeaveChannel");
    }
    //版本协议不兼容回调
    @Override
    public void onProtocolIncompatible(int status) {
        //Log.e(TAG, "onProtocolIncompatible");
    }
    //服务器断开回调
    @Override
    public void onDisconnectServer() {
        //Log.e(TAG, "onDisconnectServer");
    }

    //当前通话网络状况回调
    @Override
    public void onNetworkQuality(String user, int quality, AVChatNetworkStats stats) {
        //Log.e(TAG, "onNetworkQuality");
    }
    //音视频连接成功建立回调
    @Override
    public void onCallEstablished() {
        Log.e(TAG, "onCallEstablished");
    }
    //音视频设备状态通知
    @Override
    public void onDeviceEvent(int code, String desc) {
        //Log.e(TAG, "onDeviceEvent: " + code + ", " + desc);
    }
    //截图结果回调
    @Override
    public void onTakeSnapshotResult(String account, boolean success, String file) {
        //Log.e(TAG, "onTakeSnapshotResult: " + account + ", " + success + ", " + file);
    }
    //本地网络类型发生改变回调
    @Override
    public void onConnectionTypeChanged(int netType) {
        //Log.e(TAG, "onConnectionTypeChanged: " + netType);
    }

    //音视频录制回调
    //当用户录制音视频结束时回调，会通知录制的用户id和录制文件路径。
    @Override
    public void onAVRecordingCompletion(String account, String filePath) {
        //Log.e(TAG, "onAVRecordingCompletion: " + account + ", " + filePath);
    }
    //当用户录制语音结束时回调，会通知录制文件路径。
    //当用户录制语音结束时回调，会通知录制文件路径。
    @Override
    public void onAudioRecordingCompletion(String filePath) {
        //Log.e(TAG, "onAudioRecordingCompletion: "  + filePath);
    }
    //当用户录制语音结束时回调，会通知录制文件路径。
    //当存储空间不足时的警告回调,存储空间低于20M时开始出现警告，出现警告时请及时关闭所有的录制服务，当存储空间低于10M时会自动关闭所有的录制。
    @Override
    public void onLowStorageSpaceWarning(long availableSize) {
        //Log.e(TAG, "onLowStorageSpaceWarning: "  + availableSize);
    }
    //用户第一帧画面通知
    @Override
    public void onFirstVideoFrameAvailable(String account) {
        //Log.e(TAG, "onFirstVideoFrameAvailable: "  + account);
    }
    //用户视频画面分辨率改变通知
    @Override
    public void onFirstVideoFrameRendered(String user) {
        Log.e(TAG, "onFirstVideoFrameRendered: "  + user);
    }
    //用户视频画面分辨率改变通知
    @Override
    public void onVideoFrameResolutionChanged(String user, int width, int height, int rotate) {
        //Log.e(TAG, "onVideoFrameResolutionChanged: "  + user + ", " + width + ", " + height + ", " + rotate);
    }
    //用户视频帧率汇报
    @Override
    public void onVideoFpsReported(String account, int fps) {
        //Log.e(TAG, "onFirstVideoFrameRendered: "  + account + ", " + fps);
    }


    //采集视频数据回调
    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
        //Log.e(TAG, "onVideoFrameFilter: "  + maybeDualInput);
        return false;
    }
    //采集语音数据回调
    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
        //Log.e(TAG, "onAudioFrameFilter");
        return false;
    }
    //语音播放设备变化通知
    @Override
    public void onAudioDeviceChanged(int device) {
        //Log.e(TAG, "onAudioDeviceChanged");
    }



    //语音正在说话用户声音强度通知
    @Override
    public void onReportSpeaker(Map<String, Integer> speakers, int mixedEnergy) {
        Log.e(TAG, "onReportSpeaker");
    }
    //伴音事件通知
    @Override
    public void onAudioMixingEvent(int event) {
        //Log.e(TAG, "onAudioMixingEvent: " + event);
    }



    //实时统计信息汇报
    @Override
    public void onSessionStats(AVChatSessionStats sessionStats) {
        //Log.e(TAG, "onSessionStats");
    }
    //互动直播事件通知
    @Override
    public void onLiveEvent(int event) {
        //Log.e(TAG, "onLiveEvent: " + event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoSwitch) {
            closeVideo();
        }
        AVChatManager.getInstance().observeAVChatState(this, false);
    }

    //退出登录成功回调
    @Override
    public void onExitRoomSuccess(ExitRoomBean bean) {
        if (bean.getStatus()==1){
            finish();
            IsUtils.showShort(this,"退出房间成功");
        }else {
            IsUtils.showShort(this,"退出房间失败");
        }
    }

    @Override
    public void onExitRoomError(Throwable t) {

    }

    //IBaseView成功回调
    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onError(Throwable t) {

    }

    public void onBackButtonPressed() {
        Log.d(TAG, "onBackPressed()");

        AlertDialog.Builder builder = new AlertDialog.Builder(KTVActivity.this);
        builder.setTitle("退出房间");
        builder.setMessage("确定退出房间？");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                HashMap<String,String> map=new HashMap<>();
                map.put("token",sp.getString("LoginToken",""));
                mExitRoomPresenter.getExitRoom(map,ExitRoomBean.class);
                dialog.dismiss();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            Log.d(TAG, "onKeyDown()");
            onBackButtonPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

