package com.miku.ktv.miku_android.main;

import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/10/16.
 */

public class RoomWebSocket {
    private static final String TAG = RoomWebSocket.class.getName();

    private WebSocket mWebSocket;

    private String mRoomName;

    private String mAccount;

    private RoomWebSocketMsgInterface mCallback;

    public RoomWebSocket(RoomWebSocketMsgInterface callback, String roomName, String account) {
        mCallback = callback;
        mRoomName = roomName;
        mAccount = account;
    }

    public void joinRoom() {
        final AsyncHttpClient.WebSocketConnectCallback webSocketConnectCallback = new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }

                mWebSocket = webSocket;
                // 加入房间
                JSONObject data = new JSONObject();
                try {
                    data.put("room", mRoomName);
                    data.put("type", 1);
                    data.put("body", "");
                    mWebSocket.send(data.toString());
                } catch (JSONException e) {
                    throw new RuntimeException("json exception", e);
                }
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject body = new JSONObject(object.getString("body"));
                            if (body.has("cameraDisable")) {
                                mCallback.onUserDisableCamera(body.getString("userId"), body.getBoolean("cameraDisable"));
                            }
                            if (body.getInt("type") == 7) {
                                JSONObject message = new JSONObject(body.getString("message"));
                                long timeOver = message.getJSONObject("current_subtitle").getLong("start_time");
                                if (message.getInt("index") == -1) {
                                    timeOver = 0;
                                }
                                mCallback.onUserSing(message.getString("music_link"), message.getString("music_subtitle"), message.getString("music_info"), message.getLong("music_start_time"), timeOver);
                            }

                            Log.e(TAG, "onStringAvailable------" + body.getInt("type"));
                            if (body.getInt("type") == 6) {
                                mCallback.onStopSing();
                            }


                        } catch (JSONException e) {
                           Log.e(TAG, "onStringAvailable", e);
                        }
                    }
                });
            }
        };
        AsyncHttpClient asyncHttpClient = AsyncHttpClient.getDefaultInstance();
        asyncHttpClient.websocket("ws://182.92.187.203:8888", null, webSocketConnectCallback);
    }

    public void leaveRoom() {
        if (mWebSocket != null) {
            mWebSocket.close();
            mWebSocket = null;
        }
    }

    public void disableCamera(boolean disable) {
        try {
            JSONObject body = new JSONObject();
            body.put("cameraDisable", disable);
            body.put("userId", mAccount);
            body.put("type", 4);

            JSONObject data = new JSONObject();
            data.put("room", mRoomName);
            data.put("body", body.toString());
            mWebSocket.send(data.toString());
        } catch(JSONException e) {
            throw new RuntimeException("json exception", e);
        }
    }

    public void sendLyric(JSONObject message) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("message", message.toString());
        body.put("type", 7);
        body.put("userId", mAccount);

        JSONObject data = new JSONObject();
        data.put("room", mRoomName);
        data.put("body", body.toString());
        mWebSocket.send(data.toString());
    }

    public void stopSing() {
        try {
            JSONObject body = new JSONObject();
            body.put("type", 6);
            body.put("userId", mAccount);
            JSONObject data = new JSONObject();
            data.put("room", mRoomName);
            data.put("body", body.toString());
            mWebSocket.send(data.toString());
        }catch (Exception e) {
            Log.e(TAG, "stopSing", e);
        }
    }

    public interface RoomWebSocketMsgInterface {
        void onUserDisableCamera(String user, boolean disable);
        void onUserSing(final String mp3url, final String lyricUrl, final String musicInfo, final long startTime, final long timeOver);
        void onStopSing();
    }

}
