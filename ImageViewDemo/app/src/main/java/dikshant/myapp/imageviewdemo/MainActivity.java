package dikshant.myapp.imageviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    public void changeBackground(View view){

       ImageView imageView = (ImageView) findViewById(R.id.imageView);
       if(count%2 == 0){
           imageView.setImageResource(R.drawable.anime_cat);
       }else{
           imageView.setImageResource(R.drawable.cat1);
       }
        count++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}