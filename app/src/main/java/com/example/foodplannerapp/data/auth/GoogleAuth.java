package com.example.foodplannerapp.data.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.fireasestore.BackupManager;
import com.example.foodplannerapp.data.model.User;
import com.example.foodplannerapp.data.share.SharedManager;
import com.example.foodplannerapp.ui.sign_in_with_google.SignInWithGoogleInterface;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuth extends SocialAuthentication<GoogleAuth.Google> implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "GoogleAuth";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private SignInWithGoogleInterface signInWithGoogleInterface;

    private Context context;


    public GoogleAuth() {
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void login() {
    }



    @Override
    public void logout(Context context) {
        // TODO make this function in login and register
        //Repository.getInstance(context).deleteAllTable(Repository.DELETE_PLAN_AND_FAV);
        SharedManager.getInstance(context).clearAllData();
        mAuth.signOut();
    }


    @Override
    public GoogleAuth.Google instance() {
        return new Google();
    }

    public class Google {
        public void googleIntializer(Activity activity, @NonNull FragmentActivity fragmentActivity, SignInWithGoogleInterface signInWithGoogleInterface) {
            GoogleAuth.this.context = activity.getApplicationContext();
            GoogleAuth.this.signInWithGoogleInterface = signInWithGoogleInterface;

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getApplicationContext().getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext()).enableAutoManage(fragmentActivity, GoogleAuth.this).
                    addApi(Auth.GOOGLE_SIGN_IN_API, gso).
                    build();
        }
        public void checkRequestCode(int requestCode, Intent data) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        public Intent loginWithGoogle() {

            return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Log.w(TAG, "SignInResult : Failed code==>" + e.getStatusCode());
                signInWithGoogleInterface.onFailedFireBaseAuth();
            }
        }

        public FirebaseUser getCurrentUser() {
            return mAuth.getCurrentUser();
        }

        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)

                    .addOnSuccessListener(authResult -> {
                        // TODO make this function in login and register
                        User user = getUserData();
                        SharedManager sharedManager = SharedManager.getInstance(context);
                        BackupManager.getInstance(sharedManager).saveUser(user, task -> {
                            if (task.isSuccessful()){
                                sharedManager.saveUser(user);
                              //  Repository.getInstance(context).restoreAllData();
                                signInWithGoogleInterface.onSuccessFullFireBaseAuth();
                            }else{
                                signInWithGoogleInterface.onFailedFireBaseAuth();
                            }
                        });
                    })
                    .addOnFailureListener(e -> signInWithGoogleInterface.onFailedFireBaseAuth());
        }




    }

    private User getUserData() {
        FirebaseUser user = mAuth.getCurrentUser();

        return new User(user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString(), user.getEmail(),AuthenticationFactory.GOOGLE);
    }
}
