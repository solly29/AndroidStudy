package com.example.webviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private String url = "https://m.naver.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
        webview.loadUrl(url);
        webview.setWebChromeClient(new WebChromeClient());//크롬 세팅
        webview.setWebViewClient(new WebViewClientClass());
    }

    //ctrl+o 하면 오버로딩 가능한 메소드가 나온다.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()){//웹뷰에서 뒤로갈수 있으면
            webview.goBack();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override//현제 페이지의 url을 읽을수 있다. 새 창을 읽을수도 있다.
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
