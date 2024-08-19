package com.example.foodplannerapp.ui.home;


import android.content.Context;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesFactory;
import java.util.List;
import java.util.Random;

public class HomePresenter {
    public static final String AREA= "AREA";
    public static final String CATEGORY= "CATEGORY";
    public static final String INGREDIENT= "INGREDIENT";
    public static final String SINGLE= "SINGLE";
    private HomeInterface homeInterface;
    private Repository repository;
    public boolean isUser = false;


    public HomePresenter(Context context, HomeInterface homeInterface) {
        this.homeInterface = homeInterface;
        repository = Repository.getInstance(context);
        isUser = repository.isUser();
    }



    public void getRandomMeals(String type, RepoInterface<List<MealsItem>> repoInterface){
        int random = 0;
        String[] cashList;
        switch (type){
            case AREA:
                cashList = repository.getList(SharedPrefrencesFactory.AREAS);
                random = new Random().nextInt(cashList.length);
                repository.retrieveFilterResults(null, null, cashList[random], repoInterface);
                break;
            case CATEGORY:
                cashList = repository.getList(SharedPrefrencesFactory.CATEGORIES);
                random = new Random().nextInt(cashList.length);
                repository.retrieveFilterResults(cashList[random], null, null, repoInterface);
                break;
            case INGREDIENT:
                cashList = repository.getList(SharedPrefrencesFactory.INGREDIENTS);
                random = new Random().nextInt(cashList.length);
                repository.retrieveFilterResults(null, cashList[random], null, repoInterface);
                break;
            case SINGLE:
                repository.lookupSingleRandomMeal(repoInterface);
                break;


        }
    }

    public void saveFavorite(MealsItem item, RepoInterface<Void> repoInterface){
        repository.insertFavoriteMealDataBase(item, repoInterface);
    }



    public void addToPlan(MealPlan mealPlan, RepoInterface<Void> repoInterface){

        repository.insertPlaneMealDataBase(mealPlan, repoInterface);

    }

    public void deleteFromPlan(MealPlan mealPlan){

        repository.deletePlanMeal(mealPlan,null);

    }
}
