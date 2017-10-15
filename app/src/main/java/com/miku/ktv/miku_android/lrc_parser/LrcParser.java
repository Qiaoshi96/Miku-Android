package com.miku.ktv.miku_android.lrc_parser;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/10/12.
 */

public class LrcParser {

    public Lrc parse(String content) throws Exception {
        throw new RuntimeException("unimplemented");
    }

    public class Lrc {
        public ArrayList<Sentence> sentences;
        public Lrc() {
            sentences = new ArrayList<>();
        }
    }

    public class Sentence {
        public int timestamp;
        public int duration;
        public ArrayList<Slice> slices;

        public Sentence() {
            slices = new ArrayList<>();
        }
    }

    public class Slice {
        public int timestamp;
        public int duration;
        public String word;
    }
}
