package com.miku.ktv.miku_android.main;

import android.util.Log;
import android.widget.Toast;

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
                        Log.e("TAG", "onStringAvailable: " + s);
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject body = new JSONObject(object.getString("body"));
                            if (body.has("cameraDisable")) {
                                mCallback.onUserDisableCamera(body.getString("userId"), body.getBoolean("cameraDisable"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public interface RoomWebSocketMsgInterface {
        void onUserDisableCamera(String user, boolean disable);
    }
}
