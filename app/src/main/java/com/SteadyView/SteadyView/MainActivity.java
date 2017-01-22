package com.SteadyView.SteadyView;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends GvrActivity implements GvrView.StereoRenderer {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //enable VR
        GvrView gvrView = (GvrView)findViewById(R.id.gvr_view);
        gvrView.setRenderer(this);


        web = (WebView)findViewById(R.id.webview);
        final EditText urlbar = (EditText)findViewById(R.id.editText);
        web.getSettings().setJavaScriptEnabled(true);
        class MyClient extends WebViewClient {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                urlbar.setText(url);
            }
        };
        web.setWebViewClient(new MyClient());
        web.loadUrl("http://reddit.com");
        web.bringToFront();



        urlbar.setText("http://reddit.com");
        urlbar.bringToFront();
        urlbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    web.loadUrl(urlbar.getText().toString());
                }
                return true;
            }
        });


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float inchesY = metrics.heightPixels / metrics.ydpi;
        float inchesX = metrics.widthPixels / metrics.xdpi;
        float metersX = inchesX * 0.0254f;
        float metersY = inchesY * 0.0254f;
    }


    @Override
    public void onNewFrame(HeadTransform headTransform) {}
    @Override
    public void onDrawEye(Eye eye) {}
    @Override
    public void onFinishFrame(Viewport viewport) {}
    @Override
    public void onSurfaceChanged(int i, int i1) {}
    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {}
    @Override
    public void onRendererShutdown() {}
}
