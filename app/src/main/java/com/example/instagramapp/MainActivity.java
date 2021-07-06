package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    ImageView mIcon;
    TextView mBtnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIcon = findViewById(R.id.ivIcon);
        mIcon.setImageResource(R.mipmap.icon);
        mBtnLogOut = findViewById(R.id.btnLogOut);
        //set onClickListener for logOut Button and log out when clicked
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });
    }

    //Log out the current user and show log in page
    private void logOutUser() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        //send user back to the log in page
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}