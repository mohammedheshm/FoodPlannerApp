package com.example.foodplannerapp.data.fireasestore;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodplannerapp.data.model.User;
import com.example.foodplannerapp.data.model.meals.MealPlan;
import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.share.SharedManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;


public class BackupManager  {
    public static final String ROOT_KEY = "USERS";
    public static final String FAV_KEY = "FAV";
    public static final String PLANE_KEY = "PLANE";
    private SharedManager sharedManager;
    FirebaseFirestore firebaseFirestore;
    private BackupManager(SharedManager sharedManager){
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder()
                        .build();
        firebaseFirestore.setFirestoreSettings(settings);
        this.sharedManager =  sharedManager;
    }

    public static BackupManager backupManager = null;

    public static BackupManager getInstance(SharedManager sharedManager){
        if (backupManager==null)
            backupManager = new BackupManager(sharedManager);

        return backupManager;
    }

    public void saveUser(User user, OnCompleteListener<Void> onCompleteListener){
        firebaseFirestore
                .collection(ROOT_KEY)
                .document(user.getUID())
                .set(user)
                .addOnCompleteListener(onCompleteListener);
    }
    public void deleteFavorite(MealsItem mealsItem){
        if (sharedManager.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedManager.getUser().getUID())
                    .collection(FAV_KEY).document(mealsItem.getIdMeal()).delete();

    }
    public void saveFavorite(MealsItem mealsItem){
        if (sharedManager.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedManager.getUser().getUID())
                    .collection(FAV_KEY)
                    .document(mealsItem.getIdMeal())
                    .set(mealsItem);
    }

    public void restoreDataFavorite(OnSuccessListener<QuerySnapshot> onQuerySnapshot){

        firebaseFirestore
                .collection(ROOT_KEY)
                .document(sharedManager.getUser().getUID())
                .collection(FAV_KEY)
                .get()
                .addOnSuccessListener(onQuerySnapshot);
    }

    public void restoreDataPlane(OnSuccessListener<QuerySnapshot> onQuerySnapshot){
        firebaseFirestore
                .collection(ROOT_KEY)
                .document(sharedManager.getUser().getUID())
                .collection(PLANE_KEY)
                .get()
                .addOnSuccessListener(onQuerySnapshot);
    }

    public void deletePlane(MealPlan mealPlan){
        if (sharedManager.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedManager.getUser().getUID())
                    .collection(PLANE_KEY).document(mealPlan.getIdMeal()).delete();

    }
    public void savePlane(MealPlan mealPlan){
        if (sharedManager.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedManager.getUser().getUID())
                    .collection(FAV_KEY)
                    .document(mealPlan.getIdMeal())
                    .set(mealPlan);
    }


}
