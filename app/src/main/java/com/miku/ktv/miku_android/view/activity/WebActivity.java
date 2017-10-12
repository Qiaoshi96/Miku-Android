package com.miku.ktv.miku_android.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.miku.ktv.miku_android.R;

public class WebActivity extends Activity {

    private WebView webView1;
    private ProgressBar progressBar1;
    private LinearLayout web_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        webView1.loadUrl("file:///android_asset/服务协议.html");
    }

    private void initView() {
        webView1 = (WebView) findViewById(R.id.webView1);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        web_back = (LinearLayout) findViewById(R.id.Web_Back);
        web_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView1.setWebViewClient(new WebViewClient(){
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting=webView1.getSettings();
        //设置webview支持javascript脚本
        seting.setJavaScriptEnabled(true);
        webView1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    //加载完网页进度条消失
                    progressBar1.setVisibility(View.GONE);
                }
                else{
                    //开始加载网页时显示进度条
                    progressBar1.setVisibility(View.VISIBLE);
                    //设置进度值
                    progressBar1.setProgress(newProgress);
                }
            }
        });
        //自适应屏幕
        seting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        seting.setLoadWithOverviewMode(true);
        //设置可以支持缩放
        seting.setSupportZoom(true);
        //扩大比例的缩放
        seting.setUseWideViewPort(true);
        //设置是否出现缩放工具
        seting.setBuiltInZoomControls(true);
    }
}
