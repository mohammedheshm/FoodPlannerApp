package com.example.foodplannerapp.data.firbaseauth;


public class AuthenticationFactory {
    public final static int EMAIL = 2;
    public final static int GOOGLE = 4;

    private AuthenticationFactory() {
    }

    public static Authentication authenticationManager(int authType){
        Authentication authentication = null;
        switch (authType){

            case GOOGLE:
                authentication = new GoogleAuth();
                break;
            case EMAIL:
                authentication = new EmailAndPasswordAuth();
                break;

            default:
                authentication = new GoogleAuth();
        }


        return authentication;
    }
}
