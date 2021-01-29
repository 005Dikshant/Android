package dikshant.myapp.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class Number{

        int number;
        public boolean isTriangular(){
            int val = 1;
            for(int i = 1;;){
                if(val > this.number){
                    return false;
                }
                if(val == this.number){
                    return true;
                }
                i++;
                val += i;
            }
        }

        public boolean isSquare(){
            for(int i = 1; i*i<=this.number; i++){
                if(i*i == this.number){
                    return true;
                }
            }
            return false;
        }
    }

    Number n = new Number();
    public void check(View view){
//        Log.i("Info","Button Pressed");

        String message;
        EditText editText = (EditText) findViewById(R.id.editTextNumber);

        if(editText.getText().toString().isEmpty()){
            message = "Please enter a number";
        } else {
            int userValue = Integer.parseInt(editText.getText().toString());

            n.number = userValue;

            if (n.isSquare() && n.isTriangular()) {
                message = "Both Triangular and Square Number";
            } else if (n.isSquare()) {
                message = "Square Number";
            } else if (n.isTriangular()) {
                message = "Triangular Number";
            } else {
                message = "Neither Triangular and Square Number";
            }
        }
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}