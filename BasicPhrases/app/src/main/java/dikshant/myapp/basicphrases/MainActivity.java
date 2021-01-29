package dikshant.myapp.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void Translate(View view){
        Button btn = (Button) view;
        String text = btn.getTag().toString();
        //Log.i("Button Pressed",text);
        MediaPlayer mediaPlayer = MediaPlayer.create(this,getResources().getIdentifier(text,"raw",getPackageName()));
        mediaPlayer.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}