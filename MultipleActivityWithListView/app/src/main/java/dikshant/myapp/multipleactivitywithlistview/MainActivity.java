package dikshant.myapp.multipleactivitywithlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.textListView);
        final ArrayList<String> friendList = new ArrayList<>();
        friendList.add("Ranauk");
        friendList.add("Vivek");
        friendList.add("Rahul");
        friendList.add("Paras");
        friendList.add("Ansul");
        friendList.add("Bitto");
        friendList.add("Harshit");



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,friendList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("name",friendList.get(i));
                startActivity(intent);
                //Toast.makeText(MainActivity.this,friendList.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }
}