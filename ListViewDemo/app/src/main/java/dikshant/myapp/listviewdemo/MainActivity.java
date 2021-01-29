package dikshant.myapp.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.textListView);
        final ArrayList<String> friendsList = new ArrayList<>();
        friendsList.add("Me");
        friendsList.add("Hemant");
        friendsList.add("Nikhil");
        friendsList.add("Ankush");
        friendsList.add("Mohit");
        friendsList.add("Aryan");
        friendsList.add("Deepak");
        friendsList.add("Manoj");
        friendsList.add("Chinmey");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,friendsList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"Hello " +friendsList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}