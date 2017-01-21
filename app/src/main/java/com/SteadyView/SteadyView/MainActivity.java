package com.SteadyView.SteadyView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView web = (WebView)findViewById(R.id.webview);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("http://reddit.com");

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float inchesY = metrics.heightPixels / metrics.ydpi;
        float inchesX = metrics.widthPixels / metrics.xdpi;
        System.out.println("X pixels: "+metrics.widthPixels);
        System.out.println("Y pixels: "+metrics.heightPixels);
        System.out.println("X inches: "+inchesX);
        System.out.println("Y inches: "+inchesY);
        float metersX = inchesX * 0.0254f;
        float metersY = inchesY * 0.0254f;
    }
}
