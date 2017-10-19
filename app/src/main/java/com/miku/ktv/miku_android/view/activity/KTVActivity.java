package com.miku.ktv.miku_android.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.main.AvatarImageFetchRunnable;
import com.miku.ktv.miku_android.main.RoomWebSocket;
import com.miku.ktv.miku_android.model.bean.ExitRoomBean;
import com.miku.ktv.miku_android.model.bean.JoinRoomBean;
import com.miku.ktv.miku_android.model.bean.RegisterInfoBean;
import com.miku.ktv.miku_android.model.utils.Constant;
import com.miku.ktv.miku_android.model.utils.IsUtils;
import com.miku.ktv.miku_android.presenter.ExitRoomPresenter;
import com.miku.ktv.miku_android.presenter.FetchRoomInfoPresenter;
import com.miku.ktv.miku_android.view.iview.IExitRoomView;
import com.miku.ktv.miku_android.view.iview.IFetchRoomInfoView;
import com.miku.ktv.miku_android.view.view.LRCLayout;
import com.miku.ktv.miku_android.view.view.VideoGridView;
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
import com.netease.nimlib.sdk.avchat.model.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoCapturerFactory;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KTVActivity extends AppCompatActivity implements IExitRoomView<Object, ExitRoomBean>, IFetchRoomInfoView<Object, JoinRoomBean>, View.OnClickListener, AVChatStateObserver, AvatarImageFetchRunnable.FetchAvatarImageCallBack, RoomWebSocket.RoomWebSocketMsgInterface {
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
     * 视频区域1
     */
    private LinearLayout line1Layout;

    /**
     * 视频区域2
     */
    private LinearLayout line2Layout;

    /**
     * 视频区域3
     */
    private LinearLayout line3Layout;

    /**
     * 更多按钮
     */
    private ImageView moreIv;

    /**
     * 视频开关按钮
     */
    private ImageView videoSwitchIv;

    /**
     * 歌词播放视图
     */
    private LRCLayout lrcLayout;


    /**
     * 视频采集器，采用相机，可以放大缩小，也可以切换摄像头，可以聚焦
     */
    private AVChatCameraCapturer mVideoCapturer; // 视频采集模块

    /**
     * 房间视频画布渲染列表
     */
    private Map<String, Integer> mAccount2GridMap = null;

    /**
     *
     */
    private Map<Integer, String> mGrid2AccountMap = null;

    private List<VideoGridView> mVideoGridViewList = null;

    private Map<String, Bitmap> mAccount2BitmapMap = null;

    private Set<String> mVideoEnabledUsers = null;


    /**
     * 是否开启视频，true表示开启视频， false表示关闭视频
     */
    private boolean mVideoSwitch = false;

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


    /**
     * handler
     */
    private Handler mHandler;

    private RoomWebSocket mRoomWebSocket;

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

        mHandler = new Handler();

        mAccount2GridMap = new HashMap<>();
        mGrid2AccountMap = new HashMap<>();
        mAccount2BitmapMap = new HashMap<>();
        mVideoEnabledUsers = new ArraySet<>();
        mVideoGridViewList = new ArrayList<>();
        Resources res = getResources();
        for (int i = 0; i < 12; i++) {
            int resIdentifier = res.getIdentifier("v" + i, "id", getPackageName());
            VideoGridView vg = (VideoGridView) findViewById(resIdentifier);
            mVideoGridViewList.add(vg);
        }
        mRoomWebSocket = new RoomWebSocket(this, mRoomName, mAccount);
        mRoomWebSocket.joinRoom();

        startAVChat();
    }

    private void startAVChat() {
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
        // 先默认自己在第0画布
        //mAccount2GridMap.put(mAccount, 0);
        //AVChatManager.getInstance().setupLocalVideoRender(mVideoGridViewList.get(0).getRender(), false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);

        //9. 设置视频通话可选参数[可以不设置]
        AVChatParameters parameters = new AVChatParameters();
        parameters.set(AVChatParameters.KEY_VIDEO_FPS_REPORTED, true);
        AVChatManager.getInstance().setParameters(parameters);
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
                AVChatManager.getInstance().muteLocalVideo(true);
                Log.e(TAG, Environment.getExternalStorageDirectory().getPath() + "/test.mp4");
                //AVChatManager.getInstance().startAudioMixing(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.mp3", false, false, 0, 0.5f);
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

    private void stopAVChat() {
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
        AVChatManager.getInstance().observeAVChatState(this, false);
    }

    /**
     * 开启视频聊天
     */
    private void openVideo() {
        Log.e(TAG, "openVideo");
        AVChatManager.getInstance().muteLocalVideo(false);
        mRoomWebSocket.disableCamera(false);
    }

    /**
     * 关闭视频聊天
     */
    private void closeVideo() {
        Log.e(TAG, "closeVideo");
        AVChatManager.getInstance().muteLocalVideo(true);
        mRoomWebSocket.disableCamera(true);
    }


    /**
     * 从服务器获取房间内参加者列表
     */
    private void getParticipantsFromServer() {
        //请求加入聊天室接口
        HashMap<String, String> map = new HashMap<>();
        map.put("token", sp.getString("LoginToken", ""));
        mFetchRoomInfoPresenter.fetchRoomInfo(mRoomName, map, JoinRoomBean.class);
    }

    /**
     * 点歌
     */
    private void sing() {
        Log.e(TAG, "sing");
        // mp3 location
        String mp3Location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.mp3";
        String lrcLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.bph";
        try {
            lrcLayout.loadLrcFromFile(lrcLocation);
            AVChatManager.getInstance().startAudioMixing(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/test.mp3", false, false, 0, 0.5f);
            lrcLayout.start();
        } catch (Exception e) {
            Log.e(TAG, "sing", e);
        }
    }

    @Override
    public void onFetchRoomInfoSuccess(JoinRoomBean t) {
        List<JoinRoomBean.BodyBean.ParticipantsBean> participants = t.getBody().getParticipants();

        for (int i = 0; i < participants.size(); i++) {
            String account = participants.get(i).getFullname();
            String nickname = participants.get(i).getNick();
            String avatarUrl = "http://ktv.fibar.cn" + participants.get(i).getAvatar();
            if (!mAccount2BitmapMap.containsKey(account)) {
                fetchAvatarImage(account, avatarUrl);
            }
            Log.e(TAG, "---- old index" + mAccount2GridMap.get(account) + ", account: " + account);
            if (mAccount2GridMap.get(account) == null) {
                continue;
            }
            int oldIndex = mAccount2GridMap.get(account);
            int newIndex = i;
            Log.e(TAG, "---- oldIndex" + oldIndex + ", newIndex" + newIndex + ", " + account + ", " + mGrid2AccountMap.get(newIndex));
            // replace render
            if (oldIndex != newIndex) {
                AVChatSurfaceViewRenderer render1 = mVideoGridViewList.get(oldIndex).getRender();
                AVChatSurfaceViewRenderer render2 = mVideoGridViewList.get(newIndex).getRender();
                AVChatTextureViewRenderer textureViewRenderer;
                mVideoGridViewList.get(oldIndex).removeRender();
                mVideoGridViewList.get(newIndex).removeRender();

                mVideoGridViewList.get(oldIndex).addRendeer(render2);
                mVideoGridViewList.get(newIndex).addRendeer(render1);

                // replace grid
                if (mGrid2AccountMap.containsKey(newIndex)) {
                    String otherAccount = mGrid2AccountMap.get(newIndex);
                    mAccount2GridMap.put(otherAccount, oldIndex);
                    mGrid2AccountMap.put(oldIndex, otherAccount);
                } else {
                    mGrid2AccountMap.remove(oldIndex);
                }
                mAccount2GridMap.put(account, newIndex);
                mGrid2AccountMap.put(newIndex, account);
            }
            if (mAccount2BitmapMap.containsKey(account)) {
                mVideoGridViewList.get(newIndex).setHeadImage(mAccount2BitmapMap.get(account));
            }
            mVideoGridViewList.get(i).setNameText(nickname);
            //mVideoGridViewList.get(newIndex).setVisibility(true, false);
        }
        updateGridView();
        Log.e(TAG, "-----" + mAccount2GridMap.get(mAccount));
    }

    @Override
    public void onFetchRoomInfoError(Throwable t) {
        Log.e(TAG, "onFetchInfoError", t);
    }

    public void updateGridView() {
        Log.e(TAG, "updateGridView");

        // 隐藏空出来的格子
        for (int i = 0; i < 12; i++) {
            if (!mGrid2AccountMap.containsKey(i)) {
                mVideoGridViewList.get(i).setVisibility(false, false);
            }
        }

        // 显示视频或者用户头像
        Iterator<Map.Entry<String, Integer>> it = mAccount2GridMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            boolean videoEnabled = mVideoEnabledUsers.contains(entry.getKey());
           mVideoGridViewList.get(entry.getValue()).setVisibility(videoEnabled, !videoEnabled);
        }
    }

    private void fetchAvatarImage(String account, String url) {
        AvatarImageFetchRunnable runnable = new AvatarImageFetchRunnable(account, url, this, mHandler);
        new Thread(runnable).start();
    }

    @Override
    public void onAvatarImageFetchSuccess(String account, Bitmap bitmap) {
        mAccount2BitmapMap.put(account, bitmap);
        if (mAccount2GridMap.containsKey(account)) {
            int index = mAccount2GridMap.get(account);
            mVideoGridViewList.get(index).setHeadImage(bitmap);
        }
    }

    @Override
    public void onAvatarImageFetchFailed(String account, String path, String message) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_qq);
        mAccount2BitmapMap.put(account, bitmap);
        if (mAccount2GridMap.containsKey(account)) {
            int index = mAccount2GridMap.get(account);
            mVideoGridViewList.get(index).setHeadImage(bitmap);
        }
    }


    //当前音视频服务器连接回调
    @Override
    public void onJoinedChannel(int code, String audioFile, String videoFile, int elapsed) {
        Log.e(TAG, "----onJoinedChannel" + sp.getString("account", null));
        if (code != AVChatResCode.JoinChannelCode.OK) {
            Toast.makeText(this, "joined channel:" + code, Toast.LENGTH_SHORT).show();
        }
        int index = -1;
        for (int i = 0; i < 12; i++) {
            if (!mGrid2AccountMap.containsKey(i)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.e(TAG, "no grid for new user");
            throw new RuntimeException("no grid for new user");
        }
        mAccount2GridMap.put(mAccount, index);
        mGrid2AccountMap.put(index, mAccount);
        AVChatManager.getInstance().setupLocalVideoRender(mVideoGridViewList.get(index).getRender(), false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);

        getParticipantsFromServer();
    }

    //加入当前音视频频道用户帐号回调
    @Override
    public void onUserJoined(String account) {
        Log.e(TAG, "----onUserJoined" + account);
        int index = -1;
        for (int i = 0; i < 12; i++) {
            if (!mGrid2AccountMap.containsKey(i)) {
                index = i;
                break;
            }
        }
        Log.e(TAG, "----onUserJoined" + index);
        if (index == -1) {
            Log.e(TAG, "no grid for new user");
            throw new RuntimeException("no grid for new user");
        }
        mAccount2GridMap.put(account, index);
        mGrid2AccountMap.put(index, account);
        AVChatManager.getInstance().setupRemoteVideoRender(account, mVideoGridViewList.get(index).getRender(), false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);

        getParticipantsFromServer();
    }

    //当前用户离开频道回调
    @Override
    public void onUserLeave(String account, int event) {
        Log.e(TAG, "onUserLeave" + account);
        if (mAccount2GridMap.containsKey(account)) {
            mVideoGridViewList.get(mAccount2GridMap.get(account)).setVisibility(false, false);
            mGrid2AccountMap.remove(mAccount2GridMap.get(account));
            mAccount2GridMap.remove(account);
        }
        //AVChatManager.getInstance().setupRemoteVideoRender(account, null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        mVideoEnabledUsers.remove(account);
        getParticipantsFromServer();
    }

    //自己成功离开频道回调
    @Override
    public void onLeaveChannel() {
        Log.e(TAG, "onLeaveChannel");
        mGrid2AccountMap.remove(mAccount2GridMap.get(mAccount));
        mAccount2GridMap.remove(mAccount);
        mVideoEnabledUsers.remove(mAccount);
        AVChatManager.getInstance().setupLocalVideoRender(null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
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
        Log.e(TAG, "onFirstVideoFrameAvailable: "  + account);
        if (!account.equals(mAccount)) {
            mVideoEnabledUsers.add(account);
            updateGridView();
        }
    }

    //用户视频画面分辨率改变通知
    @Override
    public void onFirstVideoFrameRendered(String user) {
        Log.e(TAG, "onFirstVideoFrameRendered: " + user);
    }

    //用户视频画面分辨率改变通知
    @Override
    public void onVideoFrameResolutionChanged(String user, int width, int height, int rotate) {
        //Log.e(TAG, "onVideoFrameResolutionChanged: "  + user + ", " + width + ", " + height + ", " + rotate);
    }

    //用户视频帧率汇报
    @Override
    public void onVideoFpsReported(String account, int fps) {
        Log.e(TAG, "onVideoFpsReported: "  + account + ", " + fps);
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
        Iterator<Map.Entry<String, Integer>> it = speakers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            if (mAccount2GridMap.containsKey(entry.getKey())) {
                Log.e(TAG, "------- valume" + entry.getValue());
                int percent = entry.getValue() / 50;
                if (percent > 100) percent = 100;
                mVideoGridViewList.get(mAccount2GridMap.get(entry.getKey())).setVolume(percent);
            }
        }
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
        stopAVChat();
        mRoomWebSocket.leaveRoom();
    }

    //退出登录成功回调
    @Override
    public void onExitRoomSuccess(ExitRoomBean bean) {
        if (bean.getStatus() == 1) {
            finish();
            IsUtils.showShort(this, "退出房间成功");
        } else {
            IsUtils.showShort(this, "退出房间失败");
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
    public void onError(Throwable throwable) {

    }

    public void onBackButtonPressed() {
        Log.d(TAG, "onBackPressed()");

        AlertDialog.Builder builder = new AlertDialog.Builder(KTVActivity.this);
        builder.setTitle("退出房间");
        builder.setMessage("确定退出房间？");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                HashMap<String, String> map = new HashMap<>();
                map.put("token", sp.getString("LoginToken", ""));
                mExitRoomPresenter.getExitRoom(map, ExitRoomBean.class);
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


    private void bindPresenter() {
        mExitRoomPresenter = new ExitRoomPresenter();
        mExitRoomPresenter.attach(this);

        mFetchRoomInfoPresenter = new FetchRoomInfoPresenter();
        mFetchRoomInfoPresenter.attach(this);
    }

    //检查版本权限问题
    private void ccheckPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(KTVActivity.this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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

        line1Layout = (LinearLayout) findViewById(R.id.ll_1);
        line2Layout = (LinearLayout) findViewById(R.id.ll_2);
        line3Layout = (LinearLayout) findViewById(R.id.ll_3);

        lrcLayout = (LRCLayout) findViewById(R.id.lrc_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回键的点击事件
            case R.id.iv_back:
                onBackButtonPressed();
                break;
            //点歌的点击事件
            case R.id.ll_diangelist:
                sing();
                break;
            //排麦的点击事件
            case R.id.ll_paimailist:

                break;
            case R.id.iv_more:
                showDialog();
                break;
            //显示摄像头的方法
            case R.id.iv_video:
                if (mVideoSwitch == false) {
                    openVideo();
                    mVideoSwitch = true;
                } else {
                    closeVideo();
                    mVideoSwitch = false;
                }
                break;

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
                Intent intent = new Intent(KTVActivity.this, SuggestionsActivity.class);
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

    @Override
    public void onUserDisableCamera(String user, boolean disable) {
        if (disable) {
            mVideoEnabledUsers.remove(user);
        } else {
            mVideoEnabledUsers.add(user);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateGridView();
            }
        });
    }
}

