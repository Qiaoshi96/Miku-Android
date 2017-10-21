package com.miku.ktv.miku_android.lrc_parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/10/13.
 */

public class KasLrcParser extends LrcParser {

    @Override
    public LrcParser.Lrc parse(String content) throws Exception {
        LrcParser.Lrc lrc = new LrcParser.Lrc();
        JSONObject root = new JSONObject(content);
        JSONArray rows = root.getJSONObject("lyrics").getJSONArray("rows");
        for (int i = 0; i < rows.length(); i++) {
            JSONObject row = rows.getJSONObject(i);
            Sentence sentence = new Sentence();
            sentence.timestamp = row.getInt("start_time");
            sentence.duration = 0;
            JSONArray words = row.getJSONObject("lyric").getJSONArray("element");
            for (int j = 0; j < words.length(); j++) {
                JSONObject word = words.getJSONObject(j);
                if (word.has("string")) {
                    Slice slice = sentence.slices.get(sentence.slices.size() - 1);
                    slice.word += word.getString("string");
                    continue;
                }
                sentence.duration += word.getInt("duration");
                Slice slice = new Slice();
                slice.duration = word.getInt("duration");
                slice.timestamp = sentence.timestamp;
                if (word.has("offset_time")) {
                    slice.timestamp += word.getInt("offset_time");
                }
                if (j > 0) {
                    sentence.slices.get(j - 1).duration = slice.timestamp - sentence.slices.get(j-1).timestamp;
                }
                Log.v("KasLrcParser", "slice: " + slice.word + ", " + slice.timestamp + ", " + slice.duration);
                slice.word = word.getString("words");
                sentence.slices.add(j, slice);
            }
            sentence.duration = sentence.slices.get(sentence.slices.size() - 1).timestamp + sentence.slices.get(sentence.slices.size() - 1).duration - sentence.timestamp;

            lrc.sentences.add(i, sentence);
        }
        return lrc;
    }
}
