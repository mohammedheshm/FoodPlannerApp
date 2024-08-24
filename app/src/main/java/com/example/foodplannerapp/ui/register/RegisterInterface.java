package com.example.foodplannerapp.ui.register;

import com.google.firebase.auth.FirebaseUser;

public interface RegisterInterface {
    public void onSuccess(FirebaseUser user);

    public void onFail(Exception exception);
}
