package com.example.foodplannerapp.data.repository;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.example.foodplannerapp.data.model.meals.MealPlan;
import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.model.meals.MealsList;
import com.example.foodplannerapp.data.room.Week;
import com.example.foodplannerapp.data.fireasestore.BackupManager;
import com.example.foodplannerapp.data.model.User;
import com.example.foodplannerapp.data.network.ApiCalls;
import com.example.foodplannerapp.data.network.Network;
import com.example.foodplannerapp.data.room.RoomDatabase;
import com.example.foodplannerapp.data.sharedpref.SharedManager;

//Handle  Network , RoomDatabase , And SharedPref Manager functions
public class Repository {
    private static final String TAG = "Repository";

    public static final int DELETE_FAV = 1;
    public static final int DELETE_PLAN = 2;
    public static final int DELETE_PLAN_AND_FAV = 3;
    private final ApiCalls apiCalls;
    private final RoomDatabase roomDatabase;
    private final BackupManager backupManager;
    private final SharedManager sharedManager;
    public static Repository repository = null;

    public static Repository getInstance(Context context) {
        if (repository == null)
            repository = new Repository(context);
        return repository;
    }

    private Repository(Context context) {
        apiCalls = Network.apiCalls;
        roomDatabase = RoomDatabase.getInstance(context);
        sharedManager = SharedManager.getInstance(context);
        backupManager = BackupManager.getInstance(sharedManager);
    }

    //SharedPreference
    public boolean isUser() {return sharedManager.isUser();}
    public void saveUser(User user) {sharedManager.saveUser(user);}
    public User getUser() {return sharedManager.getUser();}
    public String[] getList(String type) {return sharedManager.getList(type);}
    public boolean isFirstEntrance() {return sharedManager.isFirstEntrance();}
    public void saveEntrance() {sharedManager.saveEntrance();}


    //restore all user data ,favorites and MealsPlane ,using rxjava
    public void restoreAllData() {
        backupManager.restoreDataPlane(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Gather all items from firebase
                List<MealPlan> mealPlans = new ArrayList<>();
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    MealPlan mealPlan = ds.toObject(MealPlan.class);
                    mealPlans.add(mealPlan);
                }
                Log.d(TAG, "restoreAllData: Gather all items from firebase");

                // remove all data from room for sure there is no items to prevent duplication
                roomDatabase.PlaneFoodDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d(TAG, "onSubscribe: Remove all db");
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
        backupManager.restoreDataFavorite(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Gather all items from firebase
                List<MealsItem> mealsItemList = new ArrayList<>();
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    MealsItem mealsItem = ds.toObject(MealsItem.class);
                    mealsItemList.add(mealsItem);
                }


                // remove all data from room [for sure] there is no items to prevent duplication
                roomDatabase.FavoriteDAO().removeAllTable()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d(TAG, "onSubscribe:  Remove all db");

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
                Log.d(TAG, "onComplete: TABLE HASE BEEN DELETED");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onComplete: TABLE HASE BEEN FAILED TO DELETE " + e.getMessage());
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
    private void insertFavoriteToRoom(MealsItem mealsItem, DataFetch<Void> dataFetch) {
        roomDatabase
                .FavoriteDAO()
                .insertFavoriteMeal(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        backupManager.saveFavorite(mealsItem);
                        dataFetch.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //Insert Fav Meal with api
    public void insertFavoriteMealDataBase(MealsItem mealsItem, DataFetch<Void> dataFetch) {
        apiCalls.retrieveMealByID(mealsItem.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {
                        insertFavoriteToRoom(mealsList.getMeals().get(0), dataFetch);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    //show Fav Meals in favorite fragment
    public void showFavouriteMealsDataBase(DataFetch<List<MealsItem>> dataFetch) {
        roomDatabase
                .FavoriteDAO()
                .showFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealsItem>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealsItem> mealsItems) {
                        dataFetch.onDataSuccessResponse(mealsItems);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //delete fav item from database
    public void deleteFavorite(MealsItem mealsItem, DataFetch<Void> dataFetch) {
        roomDatabase.FavoriteDAO()
                .deleteFavouriteMeal(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        backupManager.deleteFavorite(mealsItem);
                        dataFetch.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //insert  meal to week plan with api calls
    public void insertPlaneMealDataBase(MealPlan mealPlan, DataFetch<Void> dataFetch) {
        apiCalls.retrieveMealByID(mealPlan.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {
                        insertPlanMealRoom(mealPlan.migrateMealsToPlaneModel(mealsList.getMeals().get(0)), dataFetch);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    //insert  meal to week plan with RoomDatabase
    private void insertPlanMealRoom(MealPlan mealPlan, DataFetch<Void> dataFetch) {
        roomDatabase.PlaneFoodDAO().insertPlanMeal(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        dataFetch.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //show  plan Meals in plan fragment with rx-room
    public void showMealPlan(DataFetch<List<MealPlan>> dataFetch) {
        roomDatabase
                .PlaneFoodDAO()
                .showPlanMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealPlan>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealPlan> mealPlans) {
                        dataFetch.onDataSuccessResponse(mealPlans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //week plan meals with rx-room
    public void showPlanMealsByDay(Week dayName, DataFetch<List<MealPlan>> dataFetch) {
        roomDatabase.PlaneFoodDAO().showPlanMealsByDay(dayName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealPlan>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealPlan> mealPlans) {
                        dataFetch.onDataSuccessResponse(mealPlans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //delete week plan meal from room database with rx-room
    public void deletePlanMeal(MealPlan mealPlan, DataFetch<Void> dataFetch) {
        roomDatabase.PlaneFoodDAO()
                .deletePlanMeal(mealPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dataFetch.onDataLoading();
                    }

                    @Override
                    public void onComplete() {
                        backupManager.deletePlane(mealPlan);
                        dataFetch.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dataFetch.onDataFailedResponse(e.getMessage());
                    }
                });
    }


}
