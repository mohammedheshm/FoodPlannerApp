package com.example.foodplannerapp.data.firbaseauth;


public class AuthenticationManger {
    public final static int EMAIL = 1;
    public final static int GOOGLE = 2;

    private AuthenticationManger() {
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
