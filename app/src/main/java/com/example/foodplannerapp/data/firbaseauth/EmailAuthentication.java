package com.example.foodplannerapp.data.firbaseauth;

import com.example.foodplannerapp.ui.login.LoginInterface;
import com.example.foodplannerapp.ui.register.RegisterInterface;

public abstract class EmailAuthentication<T> extends Authentication<T>{
    public abstract void login(LoginInterface loginInterface, String email, String password);
    public abstract void register(RegisterInterface registerInterface, String email, String password);
}
