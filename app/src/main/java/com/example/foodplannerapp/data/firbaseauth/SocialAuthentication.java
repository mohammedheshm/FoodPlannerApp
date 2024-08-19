package com.example.foodplannerapp.data.firbaseauth;

public abstract class SocialAuthentication<T> extends Authentication<T>{
    public abstract void login();
}
