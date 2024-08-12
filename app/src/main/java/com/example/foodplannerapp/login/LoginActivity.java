package com.example.foodplannerapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    TextView create_an_account;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initUi();
        handleButtonEvens();
    }

    public void handleButtonEvens(){
        create_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

            }
        });

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(LoginActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void initUi(){
        create_an_account = findViewById(R.id.createNewAccountTxtView);
        btn_login =findViewById(R.id.loginBtn);
        btn_login.setEnabled(false);
    }
}