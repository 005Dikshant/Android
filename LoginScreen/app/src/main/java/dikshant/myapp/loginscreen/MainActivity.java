package dikshant.myapp.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText passEditText = (EditText) findViewById(R.id.passEditText);

        Log.i("Info", "Button Clicked");
        Log.i("name",nameEditText.getText().toString());
        Log.i("pass",passEditText.getText().toString());

        Toast.makeText(this, "Hi there!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}