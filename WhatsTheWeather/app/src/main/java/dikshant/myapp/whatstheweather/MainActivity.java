package dikshant.myapp.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    String description, temp;
    TextView resultTextView;
    public class DownloadJson extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                String result = "";
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char currentData = (char) data;
                    result += currentData;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "Could not find weather :(", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");

                JSONObject jsonObject1 = jsonObject.getJSONObject("main");
                temp = jsonObject1.getString("temp");


                JSONArray arr = new JSONArray(weatherInfo);
                for(int i = 0; i < arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    description = jsonPart.getString("description");
                }
                String result  = "Description: "+ description + "\n" + "Temperature: "+temp +".F";
                resultTextView.setText(result);

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Could not find weather :(", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public void showDetails(View view){

        EditText cityEditText = findViewById(R.id.cityeditText);
        String cityName = cityEditText.getText().toString();

        try {
            String encodedCityName = URLEncoder.encode(cityName, "UTF-8");
            DownloadJson task = new DownloadJson();
            String apiKey = "d71ff5c60a592c31dd79fe859b00efe9";
            task.execute("https://api.openweathermap.org/data/2.5/weather?q=+"+encodedCityName+"&units=metric&appid=" + apiKey);

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(cityEditText.getWindowToken(),0);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Could not find weather :(", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
    }
}