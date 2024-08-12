package com.example.foodplannerapp.sign_in_with_google;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.register.RegisterActivity;
import com.google.android.gms.common.SignInButton;

public class SignUpOrLoginActivity extends AppCompatActivity {

    Button signUpButton;
    SignInButton loginWithGoogleButton;
    TextView loginTxtViewBtn;
    Button guestButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_or_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initUi();
        handlebuttonsEvents();

    }


    private void handlebuttonsEvents() {

        signUpButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }

    public void initUi(){
        signUpButton=findViewById(R.id.signUpWithMailBtn);
    }


}