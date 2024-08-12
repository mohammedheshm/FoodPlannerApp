package com.example.foodplannerapp.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.boarding.BoardingActivity;
import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                startActivity(new Intent(getApplicationContext(), BoardingActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}