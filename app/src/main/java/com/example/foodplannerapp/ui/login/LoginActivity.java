package com.example.foodplannerapp.ui.login;

import static com.example.foodplannerapp.ui.common.Utils.isValidEmail;
import static com.example.foodplannerapp.ui.common.Utils.isValidPassword;

import android.app.ProgressDialog;
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
import com.example.foodplannerapp.data.firbaseauth.AuthenticationManger;
import com.example.foodplannerapp.ui.register.RegisterActivity;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginInterface {

    TextView create_an_account;
    Button btn_login;
    TextView emailTV;
    TextView passwordTV;
    Authentication authentication;
    private static boolean statesFlagEmail = false;
    private static boolean statesFlagPassword = false;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        authentication = AuthenticationManger.authenticationManager(AuthenticationManger.EMAIL);
        initUi();
        handleButtonEvens();
    }

    public void handleButtonEvens() {

        create_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(LoginActivity.this, "",
                        "Loading. Please wait...", true);
                authentication.login(LoginActivity.this, emailTV.getText().toString(), passwordTV.getText().toString());
            }
        });

        passwordTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                statesFlagPassword = isValidPassword(s);
                buttonStates();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                statesFlagEmail = isValidEmail(s);
                buttonStates();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void initUi() {
        create_an_account = findViewById(R.id.createNewAccountTxtView);
        emailTV = findViewById(R.id.emailTxtView);
        passwordTV = findViewById(R.id.passwordTxtView);
        btn_login = findViewById(R.id.loginBtn);
        btn_login.setEnabled(false);
    }

    public void buttonStates() {
        btn_login.setEnabled(statesFlagEmail && statesFlagPassword);
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        updateUI(user);
    }

    @Override
    public void onFail(String task) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(LoginActivity.this, task, Toast.LENGTH_SHORT).show();
        updateUI(null);
    }
}
