package com.example.foodplannerapp.ui.login;

import com.google.firebase.auth.FirebaseUser;

public interface LoginInterface {
    public void onSuccess(FirebaseUser user);

    public void onFail(String task);
}
