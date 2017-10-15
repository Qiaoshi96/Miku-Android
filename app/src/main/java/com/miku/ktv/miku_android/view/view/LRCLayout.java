package com.miku.ktv.miku_android.view.view;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.lrc_parser.LrcParser;
import com.miku.ktv.miku_android.lrc_parser.LrcParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lenovo on 2017/10/13.
 */

public class LRCLayout extends RelativeLayout {
    private static final String TAG = LRCLayout.class.getName();

    private boolean isStart = false;

    private long startTimestamp;

    private Handler handler = new Handler();

    private LrcParser.Lrc lrc = null;

    private int lineIndex = 0;

    private int sliceIndex = 0;

    private LRCTextView mTopLRCTextView;

    private LRCTextView mBottomLRCTextView;

    private LayoutParams mTopLayout;

    private LayoutParams mBottomLayout;

    private UIRunnable mUIRunnable;

    private int mWidth;

    private float mScale;

    private Paint mPaint;

    public LRCLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScale = context.getResources().getDisplayMetrics().density;
        Log.v(TAG, "scale is " + mScale);
        mTopLRCTextView = new LRCTextView(context);
         mBottomLRCTextView = new LRCTextView(context);
        mTopLayout = new LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        mTopLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
        mTopLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);//与父容器的上侧对齐
        mTopLayout.leftMargin = 30;
        mTopLRCTextView.setLayoutParams(mTopLayout);//设置布局参数
        addView(mTopLRCTextView);//RelativeLayout添加子View

        mBottomLayout = new LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        mBottomLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//与父容器的左侧对齐
        mBottomLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//与父容器的上侧对齐
        mBottomLayout.rightMargin = 30;
        mBottomLRCTextView.setLayoutParams(mBottomLayout);
        addView(mBottomLRCTextView);//RelativeLayout添加子View
        DisplayMetrics dm =getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;

        mPaint = new Paint();
        mPaint.setTextSize(20);

        mUIRunnable = new UIRunnable();
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadLrcFromFile(String fileName) throws Exception {
        LrcParser parser = LrcParserFactory.createParserByFileName(fileName);
        String content = readFile(fileName);
        lrc = parser.parse(content);
    }

    public void start() throws Exception {
        isStart = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startTimestamp = System.currentTimeMillis();
                    while (true) {
                        // 歌词播放完毕
                        if (lineIndex >= lrc.sentences.size()) {
                            Log.v(TAG, "lrc finished");
                            break;
                        }
                        long currentTimestamp = System.currentTimeMillis() - startTimestamp;
                        LrcParser.Sentence sentence = lrc.sentences.get(lineIndex);
                        if (currentTimestamp >= sentence.timestamp + sentence.duration) {
                            Log.v(TAG, "sentence finished");
                            lineIndex++;
                            continue;
                        }

                        // 寻找当前片段
                        sliceIndex = -1;
                        for (int i = 0; i < sentence.slices.size(); i++) {
                            if (sentence.slices.get(i).timestamp + sentence.slices.get(i).duration > currentTimestamp) {
                                sliceIndex = i;
                                break;
                            }
                        }
                        if (sliceIndex == -1) {
                            Log.v(TAG, "slice finished");
                            lineIndex++;
                            continue;
                        }

                        // 整句歌词
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < sentence.slices.size(); i++) {
                            sb.append(lrc.sentences.get(lineIndex).slices.get(i).word);
                        }
                        String lyric = sb.toString();

                        // 已唱歌词
                        sb = new StringBuilder();
                        for (int i = 0; i < sliceIndex; i++) {
                            sb.append(lrc.sentences.get(lineIndex).slices.get(i).word);
                        }
                        String previous  = sb.toString();

                        // 计算 margin percent
                        float previousWidth = mPaint.measureText(previous);
                        LrcParser.Slice slice = lrc.sentences.get(lineIndex).slices.get(sliceIndex);
                        float currentWidth = mPaint.measureText(slice.word);
                        float totalWidth = mPaint.measureText(lyric);
                        float percent = (previousWidth + currentWidth * (currentTimestamp - slice.timestamp) / slice.duration) / totalWidth;
                        int margin = (int)(mWidth / 3 - totalWidth * mScale / 2);
                        if (margin < 0) {
                            margin = 0;
                        }

                        // 计算下语句歌词
                        String nextLyric = "";
                        int nextMargin = 0;
                        if (lineIndex < lrc.sentences.size() - 1) {
                            LrcParser.Sentence nextSentence = lrc.sentences.get(lineIndex+1);
                            for (int i = 0; i < nextSentence.slices.size(); i++) {
                                nextLyric += nextSentence.slices.get(i).word;
                            }
                            float nextWidth = mPaint.measureText(nextLyric);
                            nextMargin = (int)(mWidth / 3 - nextWidth * mScale / 2);
                            if (nextMargin < 0) {
                                nextMargin = 0;
                            }
                        }
                        mUIRunnable.set(lineIndex %2 == 0, lyric, percent, margin, nextLyric, nextMargin);
                        handler.post(mUIRunnable);
                        Thread.sleep(50);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                isStart = false;
            }

        }).start();
    }

    /**
     * 判断文件的编码格式,并读取文件
     * @throws Exception
     */
    public String readFile(String fileName) throws Exception{
        FileInputStream inputStream = new FileInputStream(fileName);
        BufferedInputStream in = new BufferedInputStream(inputStream);
        BufferedReader reader;
        in.mark(4);
        byte[] first3bytes = new byte[3];
        in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
        in.reset();
        if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB && first3bytes[2] == (byte) 0xBF) {// utf-8
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFE) {
            reader = new BufferedReader(new InputStreamReader(in, "unicode"));
        } else if (first3bytes[0] == (byte) 0xFE && first3bytes[1] == (byte) 0xFF) {
            reader = new BufferedReader(new InputStreamReader(in, "utf-16be"));
        } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFF) {
            reader = new BufferedReader(new InputStreamReader(in, "utf-16le"));
        } else {
            reader = new BufferedReader(new InputStreamReader(in, "GBK"));
        }
        StringBuilder sb = new StringBuilder();
        String str = reader.readLine();
        while (str != null) {
            sb.append(str);
            str = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    public void stop() {
        isStart = false;
    }

    public class UIRunnable implements Runnable {
        public boolean isTop;
        public String sentence;
        public float percent;
        public int margin;

        public String nextSentence;
        public int nextMargin;

        public void set(boolean isTop, String sentence, float percent, int margin, String nextSentence, int nextMargin) {
            this.isTop = isTop;
            this.sentence = sentence;
            this.percent = percent;
            this.margin = margin;

            this.nextSentence = nextSentence;
            this.nextMargin = nextMargin;
        }

        @Override
        public void run() {
            if (isTop) {
                mTopLRCTextView.setLrc(sentence);
                mTopLRCTextView.setPercent(percent);
                mTopLayout.leftMargin = margin;
                mTopLRCTextView.setLayoutParams(mTopLayout);

                if (nextSentence != "") {
                    mBottomLRCTextView.setLrc(nextSentence);
                    mBottomLRCTextView.setPercent(0);
                    mBottomLayout.rightMargin = nextMargin;
                    mBottomLRCTextView.setLayoutParams(mBottomLayout);
                }
            } else {
                mBottomLRCTextView.setLrc(sentence);
                mBottomLRCTextView.setPercent(percent);
                mBottomLayout.rightMargin = margin;
                mBottomLRCTextView.setLayoutParams(mBottomLayout);

                if (nextSentence != "") {
                    mTopLRCTextView.setLrc(nextSentence);
                    mTopLRCTextView.setPercent(0);
                    mTopLayout.leftMargin = nextMargin;
                    mTopLRCTextView.setLayoutParams(mTopLayout);
                }
            }
        }
    }
}
