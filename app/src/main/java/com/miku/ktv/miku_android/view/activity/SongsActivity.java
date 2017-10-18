package com.miku.ktv.miku_android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miku.ktv.miku_android.R;
import com.miku.ktv.miku_android.model.bean.HistroyBean;
import com.miku.ktv.miku_android.view.adapter.MyFragmentPagerAdapter;
import com.miku.ktv.miku_android.view.fragment.HistroyFragment;
import com.miku.ktv.miku_android.view.fragment.HotFragment;

import java.util.ArrayList;

public class SongsActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "SongsActivity";

    private ViewPager songs_vp;
    private ImageView songs_imageView_back;
    private TextView songs_textView_hot;
    private TextView songs_textView_histroy;
    private ImageView songs_imageView_cursor;
    private RelativeLayout songs_relativeLayout_search;
    private ArrayList<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    //当前页卡编号
    private int currIndex = 0;
    //动画图片宽度
    private int bmpW;
    //动画图片偏移量
    private int offset = 0;
    private int position_one;
    private int position_two;
    private HotFragment hotFragment;
    private HistroyFragment histroyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        initView();
        InitTextView();
        InitImageView();
        InitFragment();
        InitViewPager();

        hotFragment.setOnDataTransmissionListener(new HotFragment.OnDataTransmissionListener() {
            @Override
            public void dataTransmission(ArrayList<HistroyBean> list) {
                histroyFragment.setData(list);
            }
        });
    }

    private void InitImageView() {
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 获取分辨率宽度
        int screenW = dm.widthPixels;
        bmpW = (screenW/2);

        //设置动画图片宽度
        setBmpW(songs_imageView_cursor, bmpW);
        offset = 0;

        //动画图片偏移量赋值
        position_one = (int) (screenW / 2.0);
        position_two = position_one * 2;
    }

    /**
     * 设置动画图片宽度
     * @param mWidth
     */
    private void setBmpW(ImageView imageView,int mWidth){
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        para.width = mWidth;
        imageView.setLayoutParams(para);
    }

    private void InitViewPager() {
        songs_vp.setAdapter(new MyFragmentPagerAdapter(fragmentManager, fragmentList));
        //设置默认打开第一页
        songs_vp.setCurrentItem(0);
        //将顶部文字恢复默认值
        resetTextViewTextColor();

        songs_textView_hot.setTextColor(getResources().getColor(R.color.yellow));
        //设置viewpager页面滑动监听事件
        songs_vp.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Songs_ImageView_Back:
                finish();
                break;
            case R.id.Songs_RelativeLayout_Search:
                startActivity(new Intent(this,SearchActivity.class));
                break;

            default:
                break;
        }
    }

    /**
     * 页卡切换监听
     * @author weizhi
     * @version 1.0
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null ;
            switch (position){
                case 0:
                    if(currIndex == 1){
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        resetTextViewTextColor();
                        songs_textView_hot.setTextColor(getResources().getColor(R.color.yellow));
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        resetTextViewTextColor();
                        songs_textView_histroy.setTextColor(getResources().getColor(R.color.yellow));
                    }
                    break;
            }
            currIndex = position;
            animation.setFillAfter(true);// true:图片停在动画结束位置
            animation.setDuration(300);
            songs_imageView_cursor.startAnimation(animation);
        }
    }

    private void resetTextViewTextColor() {
        songs_textView_hot.setTextColor(getResources().getColor(R.color.gray));
        songs_textView_histroy.setTextColor(getResources().getColor(R.color.gray));
    }

    private void InitFragment() {
        fragmentList = new ArrayList<>();
        hotFragment = new HotFragment();
        histroyFragment = new HistroyFragment();
        fragmentList.add(hotFragment);
        fragmentList.add(histroyFragment);

        fragmentManager = getSupportFragmentManager();
    }

    private void InitTextView() {
        songs_textView_hot.setOnClickListener(new MyOnClickListener(0));
        songs_textView_histroy.setOnClickListener(new MyOnClickListener(1));
    }

    private void initView() {
        songs_vp = (ViewPager) findViewById(R.id.Songs_vp);
        songs_imageView_back = (ImageView) findViewById(R.id.Songs_ImageView_Back);
        songs_textView_hot = (TextView) findViewById(R.id.Songs_TextView_Hot);
        songs_textView_histroy = (TextView) findViewById(R.id.Songs_TextView_Histroy);
        songs_imageView_cursor = (ImageView) findViewById(R.id.Songs_ImageView_Cursor);
        songs_relativeLayout_search = (RelativeLayout) findViewById(R.id.Songs_RelativeLayout_Search);
        songs_imageView_back.setOnClickListener(this);
        songs_relativeLayout_search.setOnClickListener(this);
    }

    /**
     * 头标点击监听
     * @author weizhi
     * @version 1.0
     */
    public class MyOnClickListener implements View.OnClickListener{
        private int index = 0 ;
        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            songs_vp.setCurrentItem(index);
        }
    }
}
