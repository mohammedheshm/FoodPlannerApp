package com.example.foodplannerapp.ui.register;

import static com.example.foodplannerapp.ui.common.Utils.isValidEmail;
import static com.example.foodplannerapp.ui.common.Utils.isValidPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.ui.main.MainActivity;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.firbaseauth.Authentication;
import com.example.foodplannerapp.data.firbaseauth.AuthenticationFactory;
import com.example.foodplannerapp.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface {

    TextView loginTv;
    Button btn_signUP;
    TextView signUpEmailTv;
    TextView signUpPasswordTv;
    private static boolean statesFlagEmail=false;
    private static boolean statesFlagPassword=false;

    private FirebaseAuth mAuth;
    private static final String TAG="RegisterActivity";

    private Authentication authentication;
    private AuthenticationFactory authenticationFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        authentication= AuthenticationFactory.authenticationManager(AuthenticationFactory.EMAIL);
        initUi();
        handleButtonEvents();
    }

    public void initUi(){
        loginTv = findViewById(R.id.loginTv);
        btn_signUP = findViewById(R.id.signUpBtn);
        signUpEmailTv=findViewById(R.id.signupEmailTxtView);
        signUpPasswordTv=findViewById(R.id.passwordSignupTxtView);
        btn_signUP.setEnabled(false);
    }

    public void handleButtonEvents(){

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });



        signUpEmailTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isValidEmail(charSequence))
                {   statesFlagEmail=true;
                    buttonStates();}
                if(isValidEmail(charSequence)==false) {
                    statesFlagEmail=false;
                    buttonStates();
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }


        });


        signUpPasswordTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isValidPassword(charSequence))
                {  statesFlagPassword=true;
                    buttonStates();}
                if(isValidPassword(charSequence)==false)
                {
                    statesFlagPassword=false;
                    buttonStates();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication.register(RegisterActivity.this,signUpEmailTv.getText().toString(),signUpPasswordTv.getText().toString());
            }
        });
    }


    public void updateUI(FirebaseUser user) {
        if(user!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));}
    }

    public void buttonStates() {
        if (statesFlagEmail&&statesFlagPassword)
            btn_signUP.setEnabled(true);
        else
            btn_signUP.setEnabled(false);
    }


    @Override
    public void onSuccess(FirebaseUser user) {
        updateUI(user);
    }

    @Override
    public void onFail(Exception exception) {
        if (exception == null) {
            Toast.makeText(getApplicationContext(), "UnExpected error occurred", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}