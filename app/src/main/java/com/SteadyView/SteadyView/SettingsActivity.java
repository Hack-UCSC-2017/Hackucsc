package com.SteadyView.SteadyView;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class SettingsActivity extends AppCompatActivity {
    public static double progressX = 0;
    public static double progressY = 0;
    public static boolean changed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SeekBar seekBarX = (SeekBar) findViewById(R.id.ScaleX);
        SeekBar seekBarY = (SeekBar) findViewById(R.id.ScaleY);

        seekBarX.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressX = ((double)progress/200) + 0.5;

                System.out.println(progressX);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarY.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressY = ((double)progress/200) + 0.5;
                System.out.println(progressY);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        System.out.println("inside settings button class boiiiiiis");
    }

    public void exitBtnClicked(View view) {
        System.out.println("exit button Clicked AF");
        //FIXME: Ray code here..
    }

}