package dikshant.myapp.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convert(View view){
        EditText editText = (EditText) findViewById(R.id.valEditText);
        double val = Double.valueOf(editText.getText().toString()) ;
        double val2 = Math.round((val * 0.77) * 100)/100.0;

        Toast.makeText(this, "Converted dollar "+val + " to pound is "+val2, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}