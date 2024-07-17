package com.mira.booklibraryapp.view.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mira.booklibraryapp.R;
import com.mira.booklibraryapp.view.login.LoginActivity;
import com.mira.booklibraryapp.view.main.MainActivity;
import com.mira.booklibraryapp.view.signup.SignupActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        loginButton = findViewById(R.id.login_button_welcome);
        registerButton = findViewById(R.id.signup_button_welcome);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        if (isLoggedIn()) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }
}
