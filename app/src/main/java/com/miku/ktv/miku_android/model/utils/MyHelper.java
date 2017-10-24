package com.miku.ktv.miku_android.model.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 焦帆 on 2017/10/23.
 */

public class MyHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyHelper";

    public MyHelper(Context context) {
        super(context, "songs_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL="create table songs_table(_id integer primary key autoincrement, songname text, author text, link text, lrc text, mode integer)";
        Log.d(TAG, "onCreate:  "+createSQL);
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }
}
