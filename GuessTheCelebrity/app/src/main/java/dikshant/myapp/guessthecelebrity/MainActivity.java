package dikshant.myapp.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebUrls = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int chosenCeleb = 0;
    ImageView imageView;
    String[] answers = new String[4];
    int locationOfCorrectAnswer = 0;
    Button button0,button1,button2,button3;

    public class ImageDownloader extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection;
            String result = "";

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void check(View view){

        String correctAnswer = celebNames.get(chosenCeleb);
        Button b = (Button) view;
        String markedAnswer = b.getText().toString();

        if(correctAnswer.equals(markedAnswer)){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Incorrect, It was "+ correctAnswer, Toast.LENGTH_SHORT).show();
        }


        Random rand = new Random();
        chosenCeleb = rand.nextInt(celebUrls.size());

        ImageDownloader imageTask = new ImageDownloader();
        try{
            Bitmap celebImage = imageTask.execute(celebUrls.get(chosenCeleb)).get();
            imageView.setImageBitmap(celebImage);

        }catch (Exception e){
            e.printStackTrace();
        }

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectLocation;
        for(int j = 0; j < 4; j++){
            if(j == locationOfCorrectAnswer){
                answers[j] = celebNames.get(chosenCeleb);
            }else{
                incorrectLocation = rand.nextInt(celebNames.size());

                while(incorrectLocation == chosenCeleb){
                    incorrectLocation = rand.nextInt(celebNames.size());
                }

                answers[j] = celebNames.get(incorrectLocation);
            }
        }
        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        imageView = findViewById(R.id.imageView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);


        try{
            result = task.execute("https://www.imdb.com/list/ls052283250").get();

            Pattern p = Pattern.compile("img alt=\"(.*?)\"");
            Matcher m = p.matcher(result);


            while(m.find()){
                celebNames.add(m.group(1));
            }

            String []splitResult = result.split("<div class=\"desc lister-total-num-results\">");
            p = Pattern.compile("src=\"(.*?)\"");
            m = p.matcher(splitResult[1]);

            int i = 1;
            while(m.find()){
                if(i <= 100){
                    celebUrls.add(m.group(1));
                    i++;
                }else{
                    break;
                }
            }

            Random rand = new Random();
            chosenCeleb = rand.nextInt(celebUrls.size());

            ImageDownloader imageTask = new ImageDownloader();
            Bitmap celebImage = imageTask.execute(celebUrls.get(chosenCeleb)).get();
            imageView.setImageBitmap(celebImage);

            locationOfCorrectAnswer = rand.nextInt(4);
            int incorrectLocation;
            for(int j = 0; j < 4; j++){
                if(j == locationOfCorrectAnswer){
                    answers[j] = celebNames.get(chosenCeleb);
                }else{
                    incorrectLocation = rand.nextInt(celebNames.size());

                    while(incorrectLocation == chosenCeleb){
                        incorrectLocation = rand.nextInt(celebNames.size());
                    }

                    answers[j] = celebNames.get(incorrectLocation);
                }
            }
            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}