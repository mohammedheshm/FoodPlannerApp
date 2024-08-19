package com.example.foodplannerapp.data.firbaseauth;


import android.content.Context;

import com.example.foodplannerapp.ui.login.LoginInterface;
import com.example.foodplannerapp.ui.register.RegisterInterface;

public abstract class Authentication<T> {
    public void login(LoginInterface loginInterface, String email, String password){}
    public void register(RegisterInterface registerInterface, String email, String password){}
    public abstract void logout(Context context);
    public abstract T instance();

}
