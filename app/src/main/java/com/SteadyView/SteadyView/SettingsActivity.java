package com.SteadyView.SteadyView;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SeekBar seekBarX = (SeekBar) findViewById(R.id.ScaleX);
        SeekBar seekBarY = (SeekBar) findViewById(R.id.ScaleY);



        System.out.println("inside settings button class boiiiiiis");
    }

    public void settingsBtnClicked(View view) {
        System.out.println("exit button Clicked AF");
        //FIXME: Ray code here..
    }

//    private SeekBar.OnSeekBarChangeListener seekBarScaleListener =
//            new SeekBar.OnSeekBarChangeListener() (
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress,
//                                              boolean fromUser){
//
//                }
//            );
}