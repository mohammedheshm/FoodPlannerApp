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
    Button btn_signUP;
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
        btn_signUP = findViewById(R.id.signUpBtn);
        btn_signUP.setEnabled(false);
    }

    public void handleButtonEvents(){
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btn_signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}