package com.example.foodplannerapp.data.auth;

public abstract class SocialAuthentication<T> extends Authentication<T>{
    public abstract void login();
}
