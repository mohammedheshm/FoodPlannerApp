package com.example.foodplannerapp.data.auth;


import android.content.Context;
public class TwitterAuth extends SocialAuthentication<TwitterAuth> {
    @Override
    public void logout(Context context){
    }
    @Override
    public TwitterAuth instance() {
        return this;
    }


    @Override
    public void login() {

    }
}
