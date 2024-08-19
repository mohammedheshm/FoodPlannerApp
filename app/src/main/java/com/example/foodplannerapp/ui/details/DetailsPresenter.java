package com.example.foodplannerapp.ui.details;


import android.content.Context;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;

import java.util.List;


public class DetailsPresenter {
    DetailsInterface detailsInterface;
    private Repository repository;
    Context context;

    public DetailsPresenter(Context context,DetailsInterface detailsInterface) {
        this.detailsInterface = detailsInterface;
        repository=Repository.getInstance(context);
    }

    public void deleteFromPlan(MealPlan mealPlan){

        repository.deletePlanMeal(mealPlan,null);

    }

    public void deleteFromFav(MealsItem mealsItem){
        repository.deleteFavorite(mealsItem,null);
    }

    public void addToPlan(MealPlan mealPlan, RepoInterface<Void> repoInterface){

        repository.insertPlaneMealDataBase(mealPlan, repoInterface);

    }

    public void addToFav(MealsItem mealsItem, RepoInterface<Void> repoInterface){
        repository.insertFavoriteMealDataBase(mealsItem, repoInterface);
    }
    public void getMeal(String mealId, RepoInterface<List<MealsItem>> repoInterface)
    {
        repository.retrieveMealByID(mealId, repoInterface);
    }

}