package com.p2p.bawei.p2pinvest1801;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bw.common.FinanCilalConstant;
import com.bw.framwork.base.BaseActivity;

import static android.view.KeyEvent.KEYCODE_BACK;

public class BannerMsgActivity extends BaseActivity {
    private WebView webView;
    private String weburl;

    @Override
    protected int bandlayout() {
        return R.layout.activity_banner_msg;
    }

    @Override
    protected void initview() {
        Bundle bundle = getIntent().getBundleExtra(FinanCilalConstant.BUNDEL);
        weburl = bundle.getString("bannerurl");
        webView = (WebView) findViewById(R.id.web_view);
        //启动时判断网络状态
        boolean netConnect = this.isNetConnect();
        if (netConnect) {
            webView.setVisibility(View.VISIBLE);
            findViewById(R.id.errimage).setVisibility(View.GONE);
        } else {
            //没有网络
            findViewById(R.id.errimage).setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initdata() {
        //是否可以后退
        webView.canGoBack();
//后退网页
        webView.goBack();

//是否可以前进
        webView.canGoForward();
//前进网页
        webView.goForward();
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

//支持插件

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.loadUrl(weburl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }



}
