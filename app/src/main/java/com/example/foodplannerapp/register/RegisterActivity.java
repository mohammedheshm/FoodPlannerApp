package com.example.foodplannerapp.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView loginTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initUi();
        handleButtonEvents();
    }

    public void initUi(){
        loginTv = findViewById(R.id.loginTv);

    }

    public void handleButtonEvents(){
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

}