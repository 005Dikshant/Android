package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class profileView extends AppCompatActivity {


    ConstraintLayout constraintLayout;
    int picker = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        constraintLayout = findViewById(R.id.constraintLayout);
        Random rand = new Random();

        picker = rand.nextInt(2) + 1;

        if(picker == 1){
            constraintLayout.setBackgroundResource(R.drawable.profileone);
        }else{
            constraintLayout.setBackgroundResource(R.drawable.profiletwo);
        }

        Intent intent = getIntent();
        String username = intent.getStringExtra("user");


        ImageView profileImage = findViewById(R.id.userImageView);

        Cursor c1 = MainActivity.database.rawQuery(
                "SELECT * FROM photo WHERE name=?",
                new String[] {username}
        );
        int nameIndex = c1.getColumnIndex("name");
        int imageIndex = c1.getColumnIndex("image");

        c1.moveToNext();
        while(!c1.isAfterLast()){
            String user = c1.getString(nameIndex);
            if(user.equals(username)){
                byte[] img = c1.getBlob(imageIndex);
                Bitmap bitmap = UserListActivity.getImage(img);
                profileImage.setImageBitmap(bitmap);
                break;
            }
        }

        TextView textView = findViewById(R.id.userProfileTextView);
        textView.setText(username.toUpperCase());


        Toast.makeText(this, "Hehehe", Toast.LENGTH_SHORT).show();
    }
}