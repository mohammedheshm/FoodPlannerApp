package com.example.foodplannerapp.data.firbaseauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.fireasestore.FirebaseStoreBackup;
import com.example.foodplannerapp.data.pojo.user.User;
import com.example.foodplannerapp.data.repository.Repository;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesFactory;
import com.example.foodplannerapp.ui.signup_or_login.SignInWithGoogleInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuth extends SocialAuthentication<GoogleAuth.Google> {
    private static final String TAG = "GoogleAuth";
    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private SignInWithGoogleInterface signInWithGoogleInterface;
    private Context context;

    public GoogleAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void login() {
    }

    @Override
    public void logout(Context context) {
        Repository.getInstance(context).deleteAllTable(Repository.DELETE_PLAN_AND_FAV);
        SharedPrefrencesFactory.getInstance(context).clearAllData();
        mAuth.signOut();
    }

    @Override
    public GoogleAuth.Google instance() {
        return new Google();
    }

    public class Google {
        public void googleInitializer(Activity activity, @NonNull FragmentActivity fragmentActivity, SignInWithGoogleInterface signInWithGoogleInterface) {
            GoogleAuth.this.context = activity.getApplicationContext();
            GoogleAuth.this.signInWithGoogleInterface = signInWithGoogleInterface;

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id)) // Check this ID
                    .requestEmail()
                    .build();



            googleSignInClient = GoogleSignIn.getClient(activity, gso);
        }

        public void checkRequestCode(int requestCode, Intent data) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        public Intent loginWithGoogle() {
            return googleSignInClient.getSignInIntent();
        }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                } else {
                    Log.w(TAG, "GoogleSignInAccount is null");
                    signInWithGoogleInterface.onFailedFireBaseAuth();
                }
            } catch (ApiException e) {
                Log.w(TAG, "SignInResult : Failed code==>" + e.getStatusCode(), e);
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
                        User user = getUserData();
                        SharedPrefrencesFactory sharedPrefrencesFactory = SharedPrefrencesFactory.getInstance(context);
                        FirebaseStoreBackup.getInstance(sharedPrefrencesFactory).saveUser(user, task -> {
                            if (task.isSuccessful()) {
                                sharedPrefrencesFactory.saveUser(user);
                                Repository.getInstance(context).restoreAllData();
                                signInWithGoogleInterface.onSuccessFullFireBaseAuth();
                            } else {
                                Log.e(TAG, "Failed to save user data");
                                signInWithGoogleInterface.onFailedFireBaseAuth();
                            }
                        });
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "FirebaseAuthWithGoogle: Failed", e);
                        signInWithGoogleInterface.onFailedFireBaseAuth();
                    });
        }

        private User getUserData() {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                return new User(user.getUid(), user.getDisplayName(), user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null, user.getEmail(), AuthenticationFactory.GOOGLE);
            } else {
                return new User("", "", "", "", AuthenticationFactory.GOOGLE);
            }
        }
    }
}
