package com.SteadyView.SteadyView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.preference.PreferenceFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView web = (WebView)findViewById(R.id.webview);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("http://reddit.com");



        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                          new MainSettingsFragment()).commit();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float inchesY = metrics.heightPixels / metrics.ydpi;
        float inchesX = metrics.widthPixels / metrics.xdpi;
        float metersX = inchesX * 0.0254f;
        float metersY = inchesY * 0.0254f;
    }
    public static class MainSettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstaanceState){
            super.onCreate(savedInstaanceState);
            System.out.println(R.xml.preferences);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
