package com.SteadyView.SteadyView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static WebView web;
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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



        urlbar.setText("http://reddit.com");
        urlbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                //FIXME: ADD HTTP IN FRONT IF NOT ALREADY THERE!
                    String url = prependHTTP(urlbar.getText().toString());
                    System.out.println("url parsed:" + url);
                    web.loadUrl(url);


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

    public String prependHTTP(String url){
        if((url.substring(0,7)).equals("http://") ||
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
        String temp = urlizeText(spokenText);
        String url = prependHTTP(temp);
        web.loadUrl(url);
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
