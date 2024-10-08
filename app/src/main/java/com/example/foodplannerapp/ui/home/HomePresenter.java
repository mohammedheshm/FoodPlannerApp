package com.example.foodplannerapp.ui.home;


import android.content.Context;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.LocalDataSource;
import com.example.foodplannerapp.data.repository.RemoteDataSource;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesManger;

import java.util.List;
import java.util.Random;

public class HomePresenter {
    private HomeInterface homeInterface;
    private Repository repository;
    public static final String AREA = "AREA";
    public static final String CATEGORY = "CATEGORY";
    public static final String INGREDIENT = "INGREDIENT";
    public static final String SINGLE = "SINGLE";

    public HomePresenter(Context context, HomeInterface homeInterface) {
        this.homeInterface = homeInterface;
        repository = Repository.getInstance(context);
    }


    public void getRandomMeals(String type, RepoInterface<List<MealsItem>> repoInterface) {
        int random = 0;
        String[] cashList;
        switch (type) {
            case AREA:
                cashList = LocalDataSource.getList(SharedPrefrencesManger.AREAS);
                random = new Random().nextInt(cashList.length);
                RemoteDataSource.retrieveFilterResults(null, null, cashList[random], repoInterface);
                break;
            case CATEGORY:
                cashList = LocalDataSource.getList(SharedPrefrencesManger.CATEGORIES);
                random = new Random().nextInt(cashList.length);
                RemoteDataSource.retrieveFilterResults(cashList[random], null, null, repoInterface);
                break;
            case INGREDIENT:
                cashList = LocalDataSource.getList(SharedPrefrencesManger.INGREDIENTS);
                random = new Random().nextInt(cashList.length);
                RemoteDataSource.retrieveFilterResults(null, cashList[random], null, repoInterface);
                break;
            case SINGLE:
                RemoteDataSource.lookupSingleRandomMeal(repoInterface);
                break;


        }
    }

    public void saveFavorite(MealsItem item, RepoInterface<Void> repoInterface) {
        repository.insertFavoriteMealDataBase(item, repoInterface);
    }


    public void addToPlan(MealPlan mealPlan, RepoInterface<Void> repoInterface) {

        repository.insertPlaneMealDataBase(mealPlan, repoInterface);

    }

    public void deleteFromPlan(MealPlan mealPlan) {

        LocalDataSource.deletePlanMeal(mealPlan, null);

    }
}
