package dikshant.myapp.toastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void greetFunction(View view){
        Log.i("Info","Button Pressed");

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        String username = nameEditText.getText().toString();

        Toast.makeText(this, "Welcome "+username, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}