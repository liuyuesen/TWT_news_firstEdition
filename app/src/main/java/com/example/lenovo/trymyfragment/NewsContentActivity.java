package com.example.lenovo.trymyfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsContentActivity extends AppCompatActivity {
private WebView webView;

    public static void actionStart(Context context,String newsTitle,int number){
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("newsTitle", newsTitle);
        intent.putExtra("news_number",number);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_content);
        webView = (WebView) findViewById(R.id.webView);
        //String newsTitle = getIntent().getStringExtra("newsTitle");
        int number = getIntent().getIntExtra("news_number",0);
        //NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return  false;
            }
        });
        webView.loadUrl("https://news.twt.edu.cn/?id="+number+"&c=default&a=pernews");



    }
}
