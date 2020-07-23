package com.example.demo_grid;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.newquizapp.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrect;
    int scores = 0;
    TextView correcttextview;
    TextView questiontextview;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    MediaPlayer mplayer2;
    MediaPlayer mplayer1;
    int numberOfQuest = 0;
    TextView scoretextview;
    TextView timertextview;

    public void press(View view){
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        scores=0;
        numberOfQuest=0;
        scoretextview.setText("0/0");
        timertextview.setText("30s");
        correcttextview.setText("");

        button4.setVisibility(View.INVISIBLE);
        generateQuestion();
        new CountDownTimer(30000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timertextview.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mplayer2.start();
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                questiontextview.setText("");
                scoretextview.setText("0/0");
                button4.setVisibility(View.VISIBLE);
                timertextview.setText("0s");
                correcttextview.setText("Your Score: " + Integer.toString(scores) + "/" + Integer.toString(numberOfQuest));
            }
        }.start();
    }

    public void chooseanswer(View view){
        mplayer1.start();
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrect))) {
            scores++;
            correcttextview.setText("Correct!");
        } else {
            correcttextview.setText("Incorrect!");
        }
        numberOfQuest++;
        scoretextview.setText(Integer.toString(scores) + "/" + Integer.toString(numberOfQuest));
        generateQuestion();
    }
    public void generateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        questiontextview.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrect = rand.nextInt(4);
        answers.clear();
        int incorrectAnswer;

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrect) {
                answers.add(a + b);
            } else {
                incorrectAnswer = rand.nextInt(41);
                while (incorrectAnswer == a + b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questiontextview = (TextView) findViewById(R.id.questiontextview);
        mplayer2=MediaPlayer.create(getApplicationContext(),R.raw.funnyboi);
        mplayer1=MediaPlayer.create(getApplicationContext(),R.raw.tick);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.press);
        timertextview=(TextView)findViewById(R.id.timertextview);
        correcttextview = (TextView) findViewById(R.id.correcttextview);
        scoretextview = (TextView) findViewById(R.id.scoretextview);
        press(findViewById(R.id.press));
    }

}
