package com.miku.ktv.miku_android.lrc_parser;

/**
 * Created by lenovo on 2017/10/13.
 */

public class LrcParserFactory {
    public static LrcParser createParserByFileName(String filename) throws Exception {
        if (filename.contains(".bph")) {
            return new BphLrcParser();
        } else if (filename.contains(".kas")) {
            return new KasLrcParser();
        } else if (filename.contains(".txt")) {
            return new KrcLrcParser();
        }

        throw new Exception("format not supported: " + filename.substring(filename.indexOf(".")));
    }
}
