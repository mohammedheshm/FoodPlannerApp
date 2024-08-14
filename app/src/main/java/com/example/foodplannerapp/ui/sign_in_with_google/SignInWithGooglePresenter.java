package com.example.foodplannerapp.ui.sign_in_with_google;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import com.example.foodplannerapp.data.auth.AuthenticationFactory;
import com.example.foodplannerapp.data.auth.GoogleAuth;
import com.example.foodplannerapp.data.repository.Repository;

public class SignInWithGooglePresenter {
    private GoogleAuth.Google google;
    private Repository repository;
    private SignInWithGoogleInterface signInWithGoogleInterface;

    public SignInWithGooglePresenter(Context context, SignInWithGoogleInterface signInWithGoogleInterface) {
        repository = Repository.getInstance(context);
        this.signInWithGoogleInterface = signInWithGoogleInterface;
        GoogleAuth authentication = (GoogleAuth) AuthenticationFactory.authenticationManager(AuthenticationFactory.GOOGLE);
        google = authentication.instance();
    }

    public void googleAuthInitialize(FragmentActivity activity) {
        // Ensure that SignUpOrLoginActivity extends FragmentActivity or Activity
        google.googleInitializer(activity, activity, signInWithGoogleInterface);
    }

    public void onSuccessFullFireBaseAuth() {
        signInWithGoogleInterface.onSuccessFullFireBaseAuth();
    }

    public void onFailedFireBaseAuth() {
        signInWithGoogleInterface.onFailedFireBaseAuth();
    }

    public Intent loginWithGoogle() {
        return google.loginWithGoogle();
    }

    public boolean isUser() {
        return repository.isUser();
    }

    public void checkRequestCode(int requestCode, Intent data) {
        google.checkRequestCode(requestCode, data);
    }
}
