package dikshant.myapp.showingandhidinguielements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    boolean check = false;
    TextView textView;
    public void runFunction(View view){
        if(check == false){ // hide -> show
            check = true;
            textView.setVisibility(View.VISIBLE);
        }else{ // show -> hide
            check = false;
            textView.setVisibility(View.INVISIBLE);
            
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setVisibility(textView.INVISIBLE);
    }
}