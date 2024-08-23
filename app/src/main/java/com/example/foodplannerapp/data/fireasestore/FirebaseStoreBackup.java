package com.example.foodplannerapp.data.fireasestore;


import com.example.foodplannerapp.data.pojo.user.User;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesManger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;


public class FirebaseStoreBackup {
    public static final String ROOT_KEY = "USERS";
    public static final String FAV_KEY = "FAV";
    public static final String PLANE_KEY = "PLANE";
    private SharedPrefrencesManger sharedPrefrencesManger;
    FirebaseFirestore firebaseFirestore;

    private FirebaseStoreBackup(SharedPrefrencesManger sharedPrefrencesManger){
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder()
                        .build();
        firebaseFirestore.setFirestoreSettings(settings);
        this.sharedPrefrencesManger = sharedPrefrencesManger;
    }

    public static FirebaseStoreBackup firebaseStoreBackup = null;

    public static FirebaseStoreBackup getInstance(SharedPrefrencesManger sharedPrefrencesManger){
        if (firebaseStoreBackup ==null)
            firebaseStoreBackup = new FirebaseStoreBackup(sharedPrefrencesManger);

        return firebaseStoreBackup;
    }

    public void saveUser(User user, OnCompleteListener<Void> onCompleteListener){
        firebaseFirestore
                .collection(ROOT_KEY)
                .document(user.getUID())
                .set(user)
                .addOnCompleteListener(onCompleteListener);
    }
    public void deleteFavorite(MealsItem mealsItem){
        if (sharedPrefrencesManger.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedPrefrencesManger.getUser().getUID())
                    .collection(FAV_KEY).document(mealsItem.getIdMeal()).delete();

    }
    public void saveFavorite(MealsItem mealsItem){

        if (sharedPrefrencesManger.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedPrefrencesManger.getUser().getUID())
                    .collection(FAV_KEY)
                    .document(mealsItem.getIdMeal())
                    .set(mealsItem);
    }

    public void restoreDataFavorite(OnSuccessListener<QuerySnapshot> onQuerySnapshot){

        firebaseFirestore
                .collection(ROOT_KEY)
                .document(sharedPrefrencesManger.getUser().getUID())
                .collection(FAV_KEY)
                .get()
                .addOnSuccessListener(onQuerySnapshot);
    }

    public void restoreDataPlane(OnSuccessListener<QuerySnapshot> onQuerySnapshot){
        firebaseFirestore
                .collection(ROOT_KEY)
                .document(sharedPrefrencesManger.getUser().getUID())
                .collection(PLANE_KEY)
                .get()
                .addOnSuccessListener(onQuerySnapshot);
    }

    public void deletePlane(MealPlan mealPlan){
        if (sharedPrefrencesManger.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedPrefrencesManger.getUser().getUID())
                    .collection(PLANE_KEY).document(mealPlan.getIdMeal()).delete();

    }

    public void savePlane(MealPlan mealPlan){
        if (sharedPrefrencesManger.isUser())
            firebaseFirestore
                    .collection(ROOT_KEY)
                    .document(sharedPrefrencesManger.getUser().getUID())
                    .collection(PLANE_KEY)
                    .document(mealPlan.getIdMeal())
                    .set(mealPlan);
    }


}
