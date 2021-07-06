package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity {
    ImageView ivIconLogin;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;

    //When loginActivity is created either take the user to login page or feed, based on if a user is already logged in
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //If the current user is logged in, go to main activity
        if (ParseUser.getCurrentUser() != null){
            goToMainActivity();
        }

        ivIconLogin = findViewById(R.id.ivIconLogin);
        ivIconLogin.setImageResource(R.mipmap.icon);
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("login", "Log in button clicked");
                //when the button is clicked, get inputted strings, and attempt login
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

    }

    //Use Parse to log in user based on the username and password given
    private void loginUser(String username, String password) {
        Log.i("login", "Attempting to log user in");
        Log.i("login", username);
        Log.i("login", password);

        //If log in successful, go to the main activity
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e("login", "issue with log in");
                    return;
                }
                goToMainActivity();
                Toast.makeText(login.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                
            }
        });
    }

    //Make and start intent to go to the main activity
    private void goToMainActivity() {
        Intent i = new Intent(login.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}