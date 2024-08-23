package com.example.foodplannerapp.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.foodplannerapp.data.fireasestore.FirebaseStoreBackup;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.pojo.user.User;
import com.example.foodplannerapp.data.room.RoomDatabase;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesManger;
import com.example.foodplannerapp.ui.plan.Week;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalDataSource {
    private static LocalDataSource instance;
    private static RoomDatabase roomDatabase;
    private static FirebaseStoreBackup firebaseStoreBackup;
    private static  SharedPrefrencesManger sharedPrefrencesManger;
    private static final String TAG = "LocalDataSource";
    public static final int DELETE_FAV = 1;
    public static final int DELETE_PLAN = 2;
    public static final int DELETE_PLAN_AND_FAV = 3;

    public LocalDataSource(Context context) {
        roomDatabase = RoomDatabase.getInstance(context);
        sharedPrefrencesManger = SharedPrefrencesManger.getInstance(context);
        firebaseStoreBackup = FirebaseStoreBackup.getInstance(sharedPrefrencesManger);
    }


    // Static method to get the single instance of LocalDataSource
    public static synchronized LocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDataSource(context.getApplicationContext());
        }
        return instance;
    }


    //SharedPreference
    public static boolean isUser() {return sharedPrefrencesManger.isUser();}
    public void saveUser(User user) {
        sharedPrefrencesManger.saveUser(user);}
    public User getUser() {return sharedPrefrencesManger.getUser();}
    public static String[] getList(String type) {return sharedPrefrencesManger.getList(type);}
    public boolean isFirstEntrance() {return sharedPrefrencesManger.isFirstEntrance();}
    public void saveEntrance() {
        sharedPrefrencesManger.saveEntrance();}



    //LocalDataSource
    //restore all user data ,favorites and MealsPlane ,using rxjava
    public void restoreAllData() {
        firebaseStoreBackup.restoreDataPlane(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Gather all items from firebase
                List<MealPlan> mealPlans = new ArrayList<>();
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    MealPlan mealPlan = ds.toObject(MealPlan.class);
                    mealPlans.add(mealPlan);
                }
                Log.d(TAG, "restore All Data from firebaseStore");

                roomDatabase.PlaneFoodDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d(TAG, "onSubscribe: Remove all Table from room database");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete: remove all data successfully");
                                // insert all data in room db
                                roomDatabase.PlaneFoodDAO()
                                        .insertAllTable(mealPlans)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, "onError: error happened during removing " + e.getMessage());
                            }
                        });
            }
        });
        firebaseStoreBackup.restoreDataFavorite(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Gather all items from firebase
                List<MealsItem> mealsItemList = new ArrayList<>();
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    MealsItem mealsItem = ds.toObject(MealsItem.class);
                    mealsItemList.add(mealsItem);
                }


                // remove all data from room to prevent duplication items
                roomDatabase.FavoriteDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d(TAG, "onSubscribe:  Remove all Table from room database");

                            }

                            @Override
                            public void onComplete() {
                                // insert all data in room db
                                Log.d(TAG, "onComplete: remove all data successfully");

                                roomDatabase.FavoriteDAO()
                                        .insertAllTable(mealsItemList)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, "onError: error happened during removing " + e.getMessage());
                            }
                        });


            }
        });
    }

    //delete all room database according type using rxjava
    public void deleteAllTable(int type) {
        CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Table Deleted!");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: Table failed to deleted! " + e.getMessage());
            }
        };
        switch (type) {
            case DELETE_FAV:
                roomDatabase.FavoriteDAO()
                        .removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(completableObserver);
                break;
            case DELETE_PLAN:
                roomDatabase.PlaneFoodDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(completableObserver);

                break;
            case DELETE_PLAN_AND_FAV:
                roomDatabase.FavoriteDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(completableObserver);
                roomDatabase.PlaneFoodDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(completableObserver);
                break;

        }
    }

    //Insert Fav Meal Into Room Database
    public void insertFavoriteToRoom(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
        roomDatabase
                .FavoriteDAO()
                .insertFavoriteMeal(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        firebaseStoreBackup.saveFavorite(mealsItem);
                        repoInterface.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }


    //show Favourite Meals in favorite fragment
    public static void showFavouriteMealsDataBase(RepoInterface<List<MealsItem>> repoInterface) {
        roomDatabase
                .FavoriteDAO()
                .showFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealsItem>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealsItem> mealsItems) {
                        repoInterface.onDataSuccessResponse(mealsItems);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //delete fav item from database
    public static void deleteFavorite(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
        roomDatabase.FavoriteDAO()
                .deleteFavouriteMeal(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        firebaseStoreBackup.deleteFavorite(mealsItem);
                        repoInterface.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }


    //insert  meal to week plan with RoomDatabase
    public void insertPlanMealRoom(MealPlan mealPlan, RepoInterface<Void> repoInterface) {
        roomDatabase.PlaneFoodDAO().insertPlanMeal(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        firebaseStoreBackup.savePlane(mealPlan);
                        repoInterface.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //week plan meals with rx-room
    public static void showPlanMealsByDay(Week dayName, RepoInterface<List<MealPlan>> repoInterface) {
        roomDatabase.PlaneFoodDAO().showPlanMealsByDay(dayName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealPlan>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealPlan> mealPlans) {

                        for (MealPlan mealPlan : mealPlans) {
                            firebaseStoreBackup.savePlane(mealPlan);
                        }
                        repoInterface.onDataSuccessResponse(mealPlans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //delete week plan meal from room database with rx-room
    public static void deletePlanMeal(MealPlan mealPlan, RepoInterface<Void> repoInterface) {
        roomDatabase.PlaneFoodDAO()
                .deletePlanMeal(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        firebaseStoreBackup.deletePlane(mealPlan);
                        repoInterface.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }



}