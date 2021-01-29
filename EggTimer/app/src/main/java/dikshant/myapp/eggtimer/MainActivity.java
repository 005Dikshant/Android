package dikshant.myapp.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView;
    SeekBar timeSeekBar;
    Boolean isButtonActive = false;
    Button goBtn;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        isButtonActive = false;
        goBtn.setText("GO!");
        timeSeekBar.setEnabled(true);
        timeTextView.setText("0:30");
        timeSeekBar.setProgress(30);
        countDownTimer.cancel();
    }
    public void countDown(View view){

        if(isButtonActive){
            resetTimer();
        }else {
            isButtonActive = true;
            goBtn.setText("STOP!");
            timeSeekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) (l / 1000));
                }

                @Override
                public void onFinish() {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.end);
                    mediaPlayer.start();
                    resetTimer();

                }
            }.start();
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateTimer(int secondsLeft){

        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;

        String secondString = Integer.toString(seconds);
        if(seconds <= 9){
            secondString = "0" + secondString ;
        }
        timeTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        goBtn = (Button) findViewById(R.id.goButton);

        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
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