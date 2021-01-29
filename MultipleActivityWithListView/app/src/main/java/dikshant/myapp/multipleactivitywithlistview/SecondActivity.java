package dikshant.myapp.multipleactivitywithlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    Intent intent;
    TextView textView,redirectTextView;
    public void destroyCurrentIntent(View view){
        new CountDownTimer(10100,1000){

            @Override
            public void onTick(long seconds ) {
                int sec = (int) (seconds / 1000);
                String output = "Redirecting in " + sec;
                redirectTextView.setText(output);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        intent = getIntent();
         textView = findViewById(R.id.textView);
         redirectTextView = findViewById(R.id.redirectTextView);

        String name =  intent.getStringExtra("name");
        textView.setText("Welcome "+name);
    }
}