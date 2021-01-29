package dikshant.myapp.processingjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadJson extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
//            String apiKey = "d71ff5c60a592c31dd79fe859b00efe9";
//            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=" + apiKey;
//            return null;

            URL url;
            String result = "";
            HttpURLConnection urlConnection;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while(data != -1){
                    char currentData = (char) data;
                    result += currentData;
                    data = reader.read();
                }
                return  result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                Log.i("Weather Content",weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);
                for(int i = 0; i < arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    Log.i("Description",jsonPart.getString("description"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i("JSON",s);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadJson task = new DownloadJson();
        String apiKey = "d71ff5c60a592c31dd79fe859b00efe9";
       // 'http://api.openweathermap.org/data/2.5/weather?q=' + cityName + '&units=metric&appid=' + apiKey;
        task.execute("https://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=" + apiKey);
    }
}