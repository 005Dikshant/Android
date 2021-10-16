package com.example.instagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    static SQLiteDatabase database;
    Boolean isActivated = false;
    TextView loginTextView;
    EditText usernameEditText, passwordEditText;

    public void signup(View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean checkResults = false;

        if(!isActivated) { // user is in sign-up mode

            Cursor c1 = database.rawQuery(
                    "SELECT * FROM users WHERE name=?",
                    new String[] {username}
            );
            if(c1.getCount() == 0) {
                String sql = "insert into users values (?,?)";
                SQLiteStatement statement = database.compileStatement(sql);
                statement.bindString(1, username);
                statement.bindString(2, password);

                Toast.makeText(this, "Successfull Signup", Toast.LENGTH_SHORT).show();
                checkResults = true;
                statement.execute();
            }else{
                Toast.makeText(this, "Username exists, use different !", Toast.LENGTH_SHORT).show();
            }
        }else{
            Cursor c1 = database.rawQuery(
                    "SELECT * FROM users WHERE name=? and password=?",
                    new String[] {username, password}
            );
//            Toast.makeText(this, String.valueOf(c1.getCount()), Toast.LENGTH_SHORT).show();
            if(c1.getCount() >= 1){
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
                checkResults = true;
            }else{
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
            }
        }
        if(checkResults) {
            ImageView spinner = findViewById(R.id.spinner);
            spinner.setVisibility(View.VISIBLE);


            new CountDownTimer(1500 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    spinner.animate().rotation(1800).setDuration(2000);
                }

                @Override
                public void onFinish() {
                    spinner.setVisibility(View.INVISIBLE);
                }
            }.start();

            TextView countDown = findViewById(R.id.countDownTextView);
            countDown.setVisibility(View.VISIBLE);

            VideoView videoView = findViewById(R.id.videoView);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.welcome);

            videoView.start();

            new CountDownTimer(8000 + 100, 1000) {
                int videoDuration = 9;

                @Override
                public void onTick(long millisUntilFinished) {
                    countDown.setText(String.valueOf(--videoDuration));
                }

                @Override
                public void onFinish() {
                    videoView.setVisibility(View.INVISIBLE);
                    countDown.setVisibility(View.INVISIBLE);
                    showUserList(username);
                }
            }.start();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("Users", Context.MODE_PRIVATE,null);
        database.execSQL("Create table if not exists users (name varchar, password varchar) ");


//        database.execSQL("delete from users");

        Cursor c  = database.rawQuery("Select * from users",null);


        int nameIndex = c.getColumnIndex("name");
        int passIndex = c.getColumnIndex("password");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.i("id,name, pass:-", c.getString(nameIndex) + " " + c.getString(passIndex));
            c.moveToNext();
        }

        loginTextView  = findViewById(R.id.login);
        loginTextView.setOnClickListener(this);

        usernameEditText = findViewById(R.id.userEditText);
        passwordEditText = findViewById(R.id.passEditText);
        passwordEditText.setOnKeyListener(this);

        ConstraintLayout layout = findViewById(R.id.backGroundLayout);
        ImageView instaLogo = findViewById(R.id.imageView);
        ImageView writtenLogo = findViewById(R.id.imageView2);

        layout.setOnClickListener(this);
        instaLogo.setOnClickListener(this);
        writtenLogo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login) {
            Button b = findViewById(R.id.signup);
            if (!isActivated) { // false -> login -> signup
                loginTextView.setText("Or, Sign-up");
                b.setText("Log-in");
                isActivated = true;
            } else { // true -> signup -> login
                loginTextView.setText("Or, Log-in");
                b.setText("Sign-up");
                isActivated = false;
            }
        }else if(v.getId() == R.id.backGroundLayout || v.getId() == R.id.imageView || v.getId() == R.id.imageView2){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            signup(v);
        }
        return false;
    }

    public void showUserList(String name){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        intent.putExtra("currentUser",name);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            usernameEditText.setText("");
            passwordEditText.setText("");

        }
    }
}