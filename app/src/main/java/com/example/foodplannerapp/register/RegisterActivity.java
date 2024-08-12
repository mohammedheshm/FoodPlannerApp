package com.example.foodplannerapp.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView btn_login_tv;

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

    public void handleButtonEvents(){
        btn_login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }

    public  void initUi(){
        btn_login_tv = findViewById(R.id.loginTxtView);
    }
}