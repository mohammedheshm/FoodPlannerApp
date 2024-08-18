package com.example.foodplannerapp.ui.sign_in_with_google;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.MainActivity;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.ui.login.LoginActivity;
import com.example.foodplannerapp.ui.register.RegisterActivity;
import com.google.android.gms.common.SignInButton;

public class SignUpOrLoginActivity extends AppCompatActivity implements SignInWithGoogleInterface {

    Button signUpButton;
    SignInButton loginWithGoogleButton;
    TextView btn_login_tv;
    Button guestButton;
    SignInWithGooglePresenter presenter;
    private static final String TAG = "SignUpOrLoginActivity";
    private static final int RC_SIGN_IN = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_or_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initUi();
        presenter = new SignInWithGooglePresenter(this, this);
        presenter.googleAuthInitialize(this);
        handlebuttonsEvents();
    }


    private void handlebuttonsEvents() {
        btn_login_tv.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
        guestButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        loginWithGoogleButton.setOnClickListener(view -> startActivityForResult(presenter.loginWithGoogle(), RC_SIGN_IN));
        signUpButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }

    public void initUi() {
        signUpButton = findViewById(R.id.signUpWithMailBtn);
        loginWithGoogleButton = findViewById(R.id.signInWithGoogle);
        btn_login_tv = findViewById(R.id.loginTxtView);
        guestButton = findViewById(R.id.guestButton);
    }

    @Override
    public void onSuccessFullFireBaseAuth() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        updateUI();
    }


    @Override
    public void onFailedFireBaseAuth() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(SignUpOrLoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
    }

    public void updateUI() {
        startActivity(new Intent(SignUpOrLoginActivity.this, MainActivity.class));
        finish();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(SignUpOrLoginActivity.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Please Wait .....");
            progressDialog.setTitle("Prepare Now");
            progressDialog.show();
        }

        presenter.checkRequestCode(requestCode, data);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter.isUser()) {
            updateUI();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}