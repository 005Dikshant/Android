package dikshant.myapp.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("dikshant.myapp.sharedpreferences", Context.MODE_PRIVATE);

        ArrayList<String> friends = new ArrayList<>();
        friends.add("kannu");
        friends.add("Rannu");
        friends.add("puunu");

        try {
            sharedPreferences.edit().putString("friends",ObjectSerializer.serialize(friends));
            Log.i("friends",ObjectSerializer.serialize(friends));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("friends",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("newFriends",newFriends.toString());
        //sharedPreferences.edit().putString("username","dikshant").apply();
       // String username = sharedPreferences.getString("username","empty");
        //Log.i("This is username",username);


    }
}