package com.example.instagram;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;
    Intent intent;
    static ImageView imageView;
    String currentUser;
    Bitmap scaled;
    ArrayList<String> userList;

    boolean checkIntial = true;

    public void setUserList() {

        intent = getIntent();
        listView = findViewById(R.id.listView);
        userList = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userList);


        Cursor c = MainActivity.database.rawQuery("select * from users", null);
        int nameIndex = c.getColumnIndex("name");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            String user = c.getString(nameIndex);
            userList.add(user);
            c.moveToNext();
        }
        listView.setAdapter(arrayAdapter);
    }

    public void setExtraDetails() {
        currentUser = intent.getStringExtra("currentUser");
        textView.setText(currentUser);

        checkUser();
    }

    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                scaled = Bitmap.createScaledBitmap(bitmap, imageView.getWidth(), imageView.getHeight(), false);
                imageView.setImageBitmap(scaled);

                byte[] img = getBytes(scaled);
                ContentValues values = new ContentValues();
                values.put("name", currentUser);
                values.put("image", img);

                if(checkIntial) {
                    MainActivity.database.insert("photo", null, values);
                }else{ // when user want to change the picture.
                    String whereClause = "name=?";
                    String whereArgs[] = {currentUser};
                    MainActivity.database.update("photo", values, whereClause, whereArgs);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkUser() {

        Cursor c1 = MainActivity.database.rawQuery("Select * from photo where name=?",
                new String[]{currentUser}
        );

        if(c1.getCount() >= 1){ // if inserted is present

            int username = c1.getColumnIndex("name");
            int picIndex = c1.getColumnIndex("image");
            c1.moveToFirst();

            while(!c1.isAfterLast()){
                Log.i("username: ",c1.getString(username));
                byte[] img = c1.getBlob(picIndex);

                Bitmap imgBitmap = getImage(img);
                imageView.setImageBitmap(imgBitmap);
                break;
            }

        }else{ // if not inserted then insert username, photo

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                getPhoto();
            }

        }
    }


    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void  changePictureOnButton(View view){
        checkIntial = false;
        getPhoto();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout){
//            Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        MainActivity.database.execSQL("Create table if not exists photo(name varchar, image blob) ");
       // MainActivity.database.execSQL("delete from photo ");


        textView = findViewById(R.id.userTextView);
        imageView = findViewById(R.id.imageView3);

        Cursor c1 = MainActivity.database.rawQuery("Select * from photo",null);
        int usernameIndex = c1.getColumnIndex("name");
        c1.moveToNext();

//        Log.i("length of photos",Integer.toString(c1.getCount()));
//        while(!c1.isAfterLast()){
//            Log.i("username for photos",c1.getString(usernameIndex));
//            c1.moveToNext();
//        }
        setUserList();
        setExtraDetails();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),profileView.class);
                intent.putExtra("user",userList.get(position));
                startActivity(intent);
            }
        });

    }

}