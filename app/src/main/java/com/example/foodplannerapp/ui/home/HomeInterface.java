package com.example.foodplannerapp.ui.home;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;

public interface HomeInterface {
    public void onSavePlane(MealsItem item);

    public void onSaveFavorite(MealsItem item);

    public void addToPlan(MealPlan mealPlan);

    public void deleteFromPlan(MealPlan mealPlan);

}
