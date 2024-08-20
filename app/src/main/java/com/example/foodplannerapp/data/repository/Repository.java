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
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.pojo.meals.MealsList;
import com.example.foodplannerapp.data.room.Week;
import com.example.foodplannerapp.data.fireasestore.FirebaseStoreBackup;
import com.example.foodplannerapp.data.pojo.user.User;
import com.example.foodplannerapp.data.network.ApiCalls;
import com.example.foodplannerapp.data.network.Network;
import com.example.foodplannerapp.data.room.RoomDatabase;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesFactory;
import com.example.foodplannerapp.data.pojo.category.CategoriesList;
import com.example.foodplannerapp.data.pojo.category.Category;
import com.example.foodplannerapp.data.pojo.countries.Area;
import com.example.foodplannerapp.data.pojo.countries.AreasList;
import com.example.foodplannerapp.data.pojo.foodcategory.CategoriesFood;
import com.example.foodplannerapp.data.pojo.foodcategory.CategoriesItem;
import com.example.foodplannerapp.data.pojo.ingredient.Ingredient;
import com.example.foodplannerapp.data.pojo.ingredient.IngredientsList;



//Handle  Network , RoomDatabase , And SharedPref Manager functions
public class Repository {

    private static final String TAG = "Repository";
    public static final int DELETE_FAV = 1;
    public static final int DELETE_PLAN = 2;
    public static final int DELETE_PLAN_AND_FAV = 3;
    private final ApiCalls apiCalls;
    private final RoomDatabase roomDatabase;
    private final FirebaseStoreBackup firebaseStoreBackup;
    private final SharedPrefrencesFactory sharedPrefrencesFactory;
    public static Repository repository = null;

    public static Repository getInstance(Context context) {
        if (repository == null)
            repository = new Repository(context);
        return repository;
    }

    private Repository(Context context) {
        apiCalls = Network.apiCalls;
        roomDatabase = RoomDatabase.getInstance(context);
        sharedPrefrencesFactory = SharedPrefrencesFactory.getInstance(context);
        firebaseStoreBackup = FirebaseStoreBackup.getInstance(sharedPrefrencesFactory);
    }

    //SharedPreference
    public boolean isUser() {return sharedPrefrencesFactory.isUser();}
    public void saveUser(User user) {sharedPrefrencesFactory.saveUser(user);}
    public User getUser() {return sharedPrefrencesFactory.getUser();}
    public String[] getList(String type) {return sharedPrefrencesFactory.getList(type);}
    public boolean isFirstEntrance() {return sharedPrefrencesFactory.isFirstEntrance();}
    public void saveEntrance() {sharedPrefrencesFactory.saveEntrance();}



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
    private void insertFavoriteToRoom(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
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

    //retrieveMea lBy ID Meal from api calls
    public void insertFavoriteMealDataBase(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
        apiCalls.retrieveMealByID(mealsItem.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {
                        insertFavoriteToRoom(mealsList.getMeals().get(0), repoInterface);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    //show Favourite Meals in favorite fragment
    public void showFavouriteMealsDataBase(RepoInterface<List<MealsItem>> repoInterface) {
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
    public void deleteFavorite(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
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

    //retrieve Meal By ID to week plan from  api calls
    public void insertPlaneMealDataBase(MealPlan mealPlan, RepoInterface<Void> repoInterface) {
        apiCalls.retrieveMealByID(mealPlan.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {
                        insertPlanMealRoom(mealPlan.migrateMealsToPlaneModel(mealsList.getMeals().get(0)), repoInterface);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    //insert  meal to week plan with RoomDatabase
    private void insertPlanMealRoom(MealPlan mealPlan, RepoInterface<Void> repoInterface) {
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
                        repoInterface.onDataSuccessResponse(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //show  plan Meals in plan fragment with rx-room
    public void showMealPlan(RepoInterface<List<MealPlan>> repoInterface) {
        roomDatabase
                .PlaneFoodDAO()
                .showPlanMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealPlan>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealPlan> mealPlans) {
                        repoInterface.onDataSuccessResponse(mealPlans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //week plan meals with rx-room
    public void showPlanMealsByDay(Week dayName, RepoInterface<List<MealPlan>> repoInterface) {
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
                        repoInterface.onDataSuccessResponse(mealPlans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

    //delete week plan meal from room database with rx-room
    public void deletePlanMeal(MealPlan mealPlan, RepoInterface<Void> repoInterface) {
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


    //RemoteDataSource
    //Handle Apis Functionalities with rxjava
    //Fetch random meals from api
    public void lookupSingleRandomMeal(RepoInterface<List<MealsItem>> repoInterface) {
        apiCalls.lookupSingleRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MealsList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                repoInterface.onDataLoading();
            }

            @Override
            public void onSuccess(@NonNull MealsList mealsList) {
                if (mealsList != null && mealsList.getMeals() != null)
                    repoInterface.onDataSuccessResponse(mealsList.getMeals());
                else
                    repoInterface.onDataSuccessResponse(new ArrayList<>());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                repoInterface.onDataFailedResponse(e.getMessage());
            }
        });
    }

    //Fetch ingredients List from api
    public void ingredientsList(RepoInterface<List<Ingredient>> repoInterface) {
        apiCalls.ingredientsList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<IngredientsList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                repoInterface.onDataLoading();
            }

            @Override
            public void onSuccess(@NonNull IngredientsList ingredientsList) {
                if (ingredientsList != null && ingredientsList.getMeals() != null) {
                    List<String> arrayList = ingredientsList.getMeals().stream().map(category -> category.getStrIngredient()).collect(Collectors.toList());
                    sharedPrefrencesFactory.saveList(SharedPrefrencesFactory.INGREDIENTS, arrayList);
                    repoInterface.onDataSuccessResponse(ingredientsList.getMeals());
                } else {
                    repoInterface.onDataSuccessResponse(new ArrayList<>());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                repoInterface.onDataFailedResponse(e.getMessage());
            }
        });
    }

    //Fetch categories List  from api
    public void categoriesList(RepoInterface<List<Category>> repoInterface) {
        apiCalls.categoriesList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull CategoriesList categoriesList) {
                        if (categoriesList != null && categoriesList.getCategory() != null) {
                            List<String> arrayList = categoriesList.getCategory().stream().map(category -> category.getStrCategory()).collect(Collectors.toList());
                            sharedPrefrencesFactory.saveList(SharedPrefrencesFactory.CATEGORIES, arrayList);
                            repoInterface.onDataSuccessResponse(categoriesList.getCategory());
                        } else {
                            repoInterface.onDataSuccessResponse(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });

    }

    //Fetch Countries List  from api
    public void areasList(RepoInterface<List<Area>> repoInterface) {
        apiCalls.areasList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AreasList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull AreasList areasList) {
                        if (areasList != null && areasList.getAreas() != null) {
                            List<String> arrayList = areasList.getAreas().stream().map(category -> category.getStrArea()).collect(Collectors.toList());
                            sharedPrefrencesFactory.saveList(SharedPrefrencesFactory.AREAS, arrayList);
                            repoInterface.onDataSuccessResponse(areasList.getAreas());
                        } else {
                            repoInterface.onDataSuccessResponse(new ArrayList<>());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });

    }

    //Fetch Filtering data according to category , ingredient and country
    public void retrieveFilterResults(String category, String ingredient, String area, RepoInterface<List<MealsItem>> repoInterface) {
        apiCalls
                .retrieveFilterResults(category, ingredient, area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {

                        if (mealsList != null && mealsList.getMeals() != null)  // sometime api return null
                            repoInterface.onDataSuccessResponse(mealsList.getMeals());
                        else
                            repoInterface.onDataSuccessResponse(new ArrayList<>());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
        ;

    }

    //Fetch Categories List  from api
    public void retrieveCategoriesList(RepoInterface<List<CategoriesItem>> repoInterface) {

        apiCalls
                .retrieveCategoriesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesFood>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull CategoriesFood categoriesFood) {
                        if (categoriesFood != null && categoriesFood.getCategories() != null)
                            repoInterface.onDataSuccessResponse(categoriesFood.getCategories());
                        else
                            repoInterface.onDataSuccessResponse(new ArrayList<>());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                })
        ;
    }

    //Fetch search about meals by meals name
    public void searchMealsByName(String search, RepoInterface<List<MealsItem>> repoInterface) {
        apiCalls
                .searchMealsByName(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {
                        if (mealsList != null && mealsList.getMeals() != null)
                            repoInterface.onDataSuccessResponse(mealsList.getMeals());
                        else
                            repoInterface.onDataSuccessResponse(new ArrayList<>());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
        ;
    }

    //Fetch meals by meals id
    public void retrieveMealByID(String id, RepoInterface<List<MealsItem>> repoInterface) {

        apiCalls.retrieveMealByID(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        repoInterface.onDataLoading();
                    }

                    @Override
                    public void onSuccess(@NonNull MealsList mealsList) {
                        if (mealsList != null && mealsList.getMeals() != null)
                            repoInterface.onDataSuccessResponse(mealsList.getMeals());
                        else
                            repoInterface.onDataSuccessResponse(new ArrayList<>());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        repoInterface.onDataFailedResponse(e.getMessage());
                    }
                });
    }

}
