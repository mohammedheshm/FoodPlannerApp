package com.example.foodplannerapp.ui.signup_or_login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplannerapp.data.firbaseauth.AuthenticationManger;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesManger;
import com.example.foodplannerapp.ui.main.MainActivity;
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
        setContentView(R.layout.activity_signup_or_login);

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
        guestButton.setOnClickListener(v -> {
            // Save guest login status
            SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isGuest", true);
            editor.apply();

            // Start MainActivity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);

        // Check if the user is a guest
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isGuest = preferences.getBoolean("isGuest", false);

        // Hide or disable the sign-out option if the user is a guest
        if (isGuest) {
            MenuItem signOutItem = menu.findItem(R.id.signout_option);
            signOutItem.setVisible(false); // You can also use signOutItem.setEnabled(false); if you want to keep it visible but disabled
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signout_option) {
            logoutFromApp();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void logoutFromApp() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.logout_title));
        alertDialog.setMessage(getResources().getString(R.string.logout_exitapp_dialog));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.dialog_positive_button), (dialog, which) -> {

            int autProvider = SharedPrefrencesManger.getInstance(this).getUser().getAuthProvider();
            AuthenticationManger.authenticationManager(autProvider)
                    .logout(this);
            startActivity(new Intent(this, SignUpOrLoginActivity.class));
            finish();
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.dialog_negative_button), (dialog, which) -> dialog.cancel());

        AlertDialog dialog = alertDialog.create();
        dialog.show();
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