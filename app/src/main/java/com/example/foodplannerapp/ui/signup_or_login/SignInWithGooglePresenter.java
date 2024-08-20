package com.example.foodplannerapp.ui.signup_or_login;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import com.example.foodplannerapp.data.firbaseauth.AuthenticationFactory;
import com.example.foodplannerapp.data.firbaseauth.GoogleAuth;
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
