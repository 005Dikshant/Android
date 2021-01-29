package dikshant.myapp.animations;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //int count = 0;
    public void fade(View view){
       ImageView bartImageView = (ImageView)findViewById(R.id.imageViewBart);

       ImageView homerImageView = (ImageView) findViewById(R.id.imageViewHomer);

       //bartImageView.animate().translationYBy(1000).setDuration(2000);
        //bartImageView.animate().translationXBy(-1100).setDuration(2000);

        //bartImageView.animate().rotation(1800).alpha(0).setDuration(1000);
        //bartImageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(1000);

//       if(count%2 == 0){
//           bartImageView.animate().alpha(0).setDuration(2000);
//           homerImageView.animate().alpha(1).setDuration(2000);
//       }else{
//           bartImageView.animate().alpha(1).setDuration(2000);
//           homerImageView.animate().alpha(0).setDuration(2000);
//       }
//       count++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bartImageView = (ImageView)findViewById(R.id.imageViewBart);
        bartImageView.setX(-1000);
        bartImageView.animate().translationXBy(1000).rotation(3600).setDuration(2000);
    }
}