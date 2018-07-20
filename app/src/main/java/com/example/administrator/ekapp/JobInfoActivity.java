package com.example.administrator.ekapp;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by YunJungsu on 2017-06-26.
 */

public class JobInfoActivity extends AppCompatActivity {
    private WebView mWebView;
    String uri="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobinfo_ek);

        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");

        mWebView = (WebView)findViewById(R.id.info_job);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        WebSettings mWebSetting = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            mWebSetting.setAllowUniversalAccessFromFileURLs(true);
            mWebSetting.setAllowFileAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            mWebSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl(uri);
        mWebView.setWebViewClient(new WebViewClientClass());
        mWebView.clearCache(true);
    }

        private class WebViewClientClass extends WebViewClient{
            public boolean shouldOverideUrlLoading(WebView view, String url) {
                view.loadUrl(uri);
                return  true;
            }
            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed() ;
            }
        }

    }