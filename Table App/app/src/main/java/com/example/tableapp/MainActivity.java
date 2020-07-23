package com.example.tableapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    public void generateTimesTable(int timesTable){
        ArrayList<String> timestableContent=new ArrayList<String>();
        for(int i=1;i<=10;i++){
            timestableContent.add(Integer.toString(i*timesTable));
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,timestableContent);
        listview.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SeekBar timetableSeekBar=(SeekBar)findViewById(R.id.seekBar);

        listview=(ListView)findViewById(R.id.listview);

        timetableSeekBar.setMax(100);
        timetableSeekBar.setProgress(50);

        timetableSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min=1;
                int timesTable;
                if(progress<min){
                    timesTable=min;
                    timetableSeekBar.setProgress(min);
                }else{
                    timesTable=progress;
                }
                generateTimesTable(timesTable);

                       }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateTimesTable(50);

    }
}