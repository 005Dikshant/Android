package dikshant.myapp.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton,playAgainButton;
    TextView timerTextView, scoreTextView,sumTextView, resultTextView;
    GridLayout grid;
    MediaPlayer mediaPlayer;

    int ans,correctAnswer = 0,totalQuestion = 0;

    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        grid.setVisibility(View.VISIBLE);
        mediaPlayer = MediaPlayer.create(this,R.raw.getmeout);

        mediaPlayer.start();

        frameQuestion();
        timer();
    }

    public void playAgain(View view){
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        correctAnswer = 0;
        totalQuestion = 0;
        playAgainButton.setVisibility(View.INVISIBLE);
        timer();
    }

    public void timer(){
        new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long l) {
                String text = String.valueOf(l/1000) + "s";
                timerTextView.setText(text);
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                resultTextView.setText("Done!");
                Log.i("Check","Indeed");
            }
        }.start();
    }

    public void frameQuestion(){
        int a,b;
        Random random = new Random();
        a = random.nextInt(100) + 1;
        b = random.nextInt(100) + 1;

        ans = a + b;
        String question = a + " + " + b;
        sumTextView.setText(question);

        int store = random.nextInt(4);
        for(int i = 0; i < grid.getChildCount(); i++){
            Button button = (Button) grid.getChildAt(i);
            if(i == store){
                button.setText(String.valueOf(ans));
            }else{
                int r = random.nextInt(200) + 1;
                while(r == ans){
                    r = random.nextInt(200) + 1;
                }
                button.setText(String.valueOf(r));
            }
        }

    }
    public void chooseAnswer(View view){
//        Log.i("Button CLicked","Indeed it's clicked");
        Button button = (Button) view;
        String text = button.getText().toString();

        if(ans == Integer.valueOf(text)){
            resultTextView.setText("Correct!");
            correctAnswer++;

        }else{
            resultTextView.setText("Incorrect!");
        }
        resultTextView.setVisibility(View.VISIBLE);
        frameQuestion();
        totalQuestion++;

        scoreTextView.setText(correctAnswer + "/" + totalQuestion);

    }

    public void init(){
        goButton = (Button) findViewById(R.id.goButton);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        grid = (GridLayout) findViewById(R.id.grid);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }
}