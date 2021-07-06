package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class login extends AppCompatActivity {
    ImageView ivIconLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ivIconLogin = findViewById(R.id.ivIconLogin);
        ivIconLogin.setImageResource(R.mipmap.icon);

    }
}