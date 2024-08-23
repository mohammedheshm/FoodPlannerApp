package com.example.foodplannerapp.data.repository;

import android.content.Context;

import com.example.foodplannerapp.data.network.ApiCalls;
import com.example.foodplannerapp.data.pojo.category.CategoriesList;
import com.example.foodplannerapp.data.pojo.category.Category;
import com.example.foodplannerapp.data.pojo.countries.Area;
import com.example.foodplannerapp.data.pojo.countries.AreasList;
import com.example.foodplannerapp.data.pojo.ingredient.Ingredient;
import com.example.foodplannerapp.data.pojo.ingredient.IngredientsList;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.pojo.meals.MealsList;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesManger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RemoteDataSource {
    private static ApiCalls apiCalls;
    private static SharedPrefrencesManger sharedPrefrencesManger;

    public RemoteDataSource(Context context, ApiCalls apiCalls) {
        this.apiCalls = apiCalls;
        sharedPrefrencesManger = SharedPrefrencesManger.getInstance(context);
    }



    //Fetch random meals from api
    public static void lookupSingleRandomMeal(RepoInterface<List<MealsItem>> repoInterface) {
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
    public static void ingredientsList(RepoInterface<List<Ingredient>> repoInterface) {
        apiCalls.ingredientsList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<IngredientsList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                repoInterface.onDataLoading();
            }

            @Override
            public void onSuccess(@NonNull IngredientsList ingredientsList) {
                if (ingredientsList != null && ingredientsList.getMeals() != null) {
                    List<String> arrayList = ingredientsList.getMeals().stream().map(category -> category.getStrIngredient()).collect(Collectors.toList());
                    sharedPrefrencesManger.saveList(SharedPrefrencesManger.INGREDIENTS, arrayList);
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
    public static void categoriesList(RepoInterface<List<Category>> repoInterface) {
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
                            sharedPrefrencesManger.saveList(SharedPrefrencesManger.CATEGORIES, arrayList);
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
    public static void areasList(RepoInterface<List<Area>> repoInterface) {
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
                            sharedPrefrencesManger.saveList(SharedPrefrencesManger.AREAS, arrayList);
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
    public static void retrieveFilterResults(String category, String ingredient, String area, RepoInterface<List<MealsItem>> repoInterface) {
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

    //Fetch search about meals by meals name
    public static void searchMealsByName(String search, RepoInterface<List<MealsItem>> repoInterface) {
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
    public static void retrieveMealByID(String id, RepoInterface<List<MealsItem>> repoInterface) {

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
