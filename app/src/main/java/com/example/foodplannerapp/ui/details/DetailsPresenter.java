package com.example.foodplannerapp.ui.details;


import android.content.Context;

import com.example.foodplannerapp.data.model.meals.MealPlan;
import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;
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

    public void addToPlan(MealPlan mealPlan, DataFetch<Void> dataFetch){

        repository.insertPlaneMealDataBase(mealPlan,dataFetch);

    }

    public void addToFav(MealsItem mealsItem, DataFetch<Void> dataFetch){
        repository.insertFavoriteMealDataBase(mealsItem,dataFetch);
    }
    public void getMeal(String mealId,DataFetch<List<MealsItem>> dataFetch )
    {
        repository.retrieveMealByID(mealId,dataFetch);
    }

}