package com.example.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SeekBar timerseekbar;
    TextView timertextview;
    MediaPlayer mplayer;
    MediaPlayer mplayer1;

    public  void implement(int progress){
        int minutes=(int)progress/60;
        int seconds=progress-(minutes*60);
        String secondString=Integer.toString(seconds);
        if(seconds<=9){
            secondString="0"+ Integer.toString(seconds);
        }
        timertextview.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void reset(View view){
        timertextview.setText("00:30");
        mplayer.stop();
        mplayer1.stop();
    }

    public void counttimer(View view){
        new CountDownTimer(timerseekbar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                implement((int)millisUntilFinished/1000);
                mplayer1=MediaPlayer.create(getApplicationContext(),R.raw.tick);
                mplayer1.start();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"FINISH",Toast.LENGTH_LONG).show();
                mplayer=MediaPlayer.create(getApplicationContext(),R.raw.tune);
                mplayer.start();
            }
        }.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerseekbar=(SeekBar)findViewById(R.id.timerseekbar);
        timertextview=(TextView)findViewById(R.id.timertextview);
        timerseekbar.setMax(600);
        timerseekbar.setProgress(30);

        timerseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                implement(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

}