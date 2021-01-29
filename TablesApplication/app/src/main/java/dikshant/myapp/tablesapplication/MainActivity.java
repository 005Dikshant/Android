package dikshant.myapp.tablesapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public void create(int i){
        final ArrayList<Integer> values = new ArrayList<>();
        ListView containerListView = (ListView) findViewById(R.id.containerListView);

        for(int j = 1; j <= 20; j++){
            values.add(j*i);
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,values);
        containerListView.setAdapter(arrayAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SeekBar ValueSeekBar = (SeekBar) findViewById(R.id.valueSeekBar);

        int startPosition = 10;
        int max = 20;
        int min = 1;

        ValueSeekBar.setMax(max);
        ValueSeekBar.setMin(min);

        ValueSeekBar.setProgress(startPosition);
        create(startPosition);

        ValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                create(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}