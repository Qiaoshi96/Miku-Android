package com.miku.ktv.miku_android.lrc_parser;

import android.util.Log;

/**
 * Created by lenovo on 2017/10/13.
 */

public class BphLrcParser extends LrcParser {

    private static final String TAG = LrcParser.class.getName();

    public  Lrc parse(String content) throws Exception {
        Log.v(TAG, "start to parse");
        Lrc lrc = new Lrc();
        String[] list = content.split("\\[");
        for (int i = 0; i < list.length; i++) {
            String line = list[i];
            int i1 = line.indexOf(",");
            int i2 = line.indexOf("]");
            int i3 = line.indexOf("(");
            int i4 = line.indexOf(")");

            if (i1 != -1 && i1 < i2 && i2 < i3 && i3 < i4) {
                lrc.sentences.add(lrc.sentences.size(), parseLine(line));
            }

        }
        Log.v(TAG, "end parse: " + lrc.sentences.size());
        return lrc;
    }


    private Sentence parseLine(String line) {
        Sentence sentence = new Sentence();
        String tmp = line.substring(0, line.indexOf(']'));
        String[] tmp2 = tmp.split(",");
        sentence.timestamp = Integer.parseInt(tmp2[0]);
        sentence.duration = Integer.parseInt(tmp2[1]);

        tmp2 = line.substring(line.indexOf(']') + 1).split("\\)");
        Log.e(TAG, "parseLine content: " + line);
        for (int i = 0; i < tmp2.length; i++) {
            Slice slice = new Slice();
            if (tmp2[i].indexOf("(") == -1 || tmp2[i].indexOf(",") == -1) {
                break;
            }
            slice.word = tmp2[i].substring(0, tmp2[i].indexOf("("));
            slice.timestamp = Integer.parseInt(tmp2[i].substring(tmp2[i].indexOf("(") + 1, tmp2[i].indexOf(",")));
            slice.duration = Integer.parseInt(tmp2[i].substring(tmp2[i].indexOf(",") + 1));
            sentence.slices.add(i, slice);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentence.slices.size(); i++) {
            sb.append(sentence.slices.get(i).word + ", " + sentence.slices.get(i).timestamp + ", " + sentence.slices.get(i).duration);
        }
        Log.e("parseLine result: ", sb.toString());

        return sentence;
    }
}
