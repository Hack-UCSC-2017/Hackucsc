package com.SteadyView.SteadyView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends GvrActivity implements GvrView.StereoRenderer, SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastSensorTimestamp = -1;

    public static WebView web;
    private static final int SPEECH_REQUEST_CODE = 0;

    int width = 1080;
    int height = 1920;

    double widthMeters;
    double heightMeters;

    double xdpm = 1;
    double ydpm = 1;

    double[] v = {0,0};
    double[] p = {0,0};

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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                    String url = prependHTTP(urlbar.getText().toString());
                    System.out.println("url parsed:" + url);
                    web.loadUrl(url);


                }
                return true;
            }
        });

        findViewById(R.id.buttonTalk).bringToFront();
        findViewById(R.id.buttonSettings).bringToFront();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        width = size.x;
        height = size.y;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthMeters = 0.0254*(double)width/metrics.xdpi;
        heightMeters = 0.0254*(double)height/metrics.ydpi;
        xdpm = metrics.xdpi/0.0254;
        ydpm = metrics.ydpi/0.0254;

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_FASTEST);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setImmersive(false);

    }

    double[] A = {1,1};
    double[] f = {0,0};
    double[] phase = {0,0};

    double[] lastAcceleration = {0,0};
    double[] lastZeroTime = {-1,-1};


    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println(event.values[0] + ","+event.values[1]+","+event.values[2]);
        //0 = x, 1 = y, 2 = z


        double[] acc = {event.values[0], event.values[1]};
        if(lastSensorTimestamp != -1) {

            double dt = (double)(event.timestamp-lastSensorTimestamp)/1.0e9;
            double timestamp = event.timestamp/1.0e9;
            for(int i = 0; i < 2; i++) {
                /*
                if (Math.signum(acc[i]) != Math.signum(lastAcceleration[i])){

                    if(lastZeroTime[i] != -1) {
                        halfperiods[i].push(timestamp - lastZeroTime[i]);
                        double fold = f[i];
                        f[i] = 2.0/halfperiods[i].average();
                        phase[i] = ((phase[i] +timestamp*fold)%(2*Math.PI)) - (f[i]*timestamp % (2*Math.PI));
                        //Calculate period
                    }
                    lastZeroTime[i] = timestamp;
                }

                if(Math.abs(lastAcceleration[i]) > Math.abs(acc[i])){
                    amplitudes[i].push(Math.abs(acc[i]));
                    A[i] = amplitudes[i].average();
                }

                double offset = -A[i]*Math.sin(f[i]*timestamp + phase[i])/(f[i]*f[i]);
                System.out.println(offset);
                if(i == 0){
                    web.setX((float)(-xdpm*offset));
                } else {
                    web.setY((float)(ydpm*offset));
                }
                */

                v[i] += (acc[i])*dt;
                v[i] *=0.95;


                p[i] += v[i]*dt;
                p[i]*=0.95;

            }

            System.out.println(dt +",("+ p[0] +","+ p[1] +"),("+v[0] +","+ v[1]+")");

        }
        float scaleup = 1.05f;
        web.setX((float)(-xdpm*p[0]*scaleup));
        web.setY((float)(ydpm*p[1]*scaleup));

        lastAcceleration[0] = acc[0];
        lastAcceleration[1] = acc[1];

        lastSensorTimestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        lastSensorTimestamp = -1;
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
    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            finish();
        }
    }

    public String prependHTTP(String url){
        if(url.length() < 8){
            StringBuffer tmp = new StringBuffer("http://");
            tmp.append(url);
            return tmp.toString();
        } else if((url.substring(0,7)).equals("http://") ||
                (url.substring(0,8)).equals("https://")){
            return url;
        }else{
            StringBuffer tmp = new StringBuffer("http://");
            tmp.append(url);
            return tmp.toString();
        }
    }

    public void speechBtnClicked(View view) {
        System.out.println("Hello World!");

//        Intent speechIntent = new Intent(this, SpeechActivity.class);
//        PendingIntent settingsPI = new PendingIntent(this, 0, settingsIntent, 0);
//        startActivity(speechIntent);

        displaySpeechRecognizer();
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        System.out.println("spokenText inside displatSpeechRecog");
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        String spokenText = "";
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            spokenText = results.get(0);
        }
        System.out.println("spokenText: " + spokenText);
        if(spokenText != "") {
            String temp = urlizeText(spokenText);
            String url = prependHTTP(temp);
            web.loadUrl(url);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public String urlizeText(String text){
        String newURL = "";
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) != ' '){
                newURL += text.charAt(i);
            }
        }
        int lastI = newURL.lastIndexOf('.');
        if(newURL.length() - lastI == 3 ||
                newURL.length() - lastI == 4){
            return newURL;
        }else{
            StringBuffer tmp = new StringBuffer(newURL);
            tmp.append(".com");
            return tmp.toString();
        }
    }
}
