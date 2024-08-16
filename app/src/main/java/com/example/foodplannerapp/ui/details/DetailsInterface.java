package com.example.foodplannerapp.ui.details;

import com.example.foodplannerapp.data.model.meals.MealPlan;
import com.example.foodplannerapp.data.model.meals.MealsItem;

public interface DetailsInterface {

    public void addToPlan(MealPlan mealPlan);
    public void addToFav(MealsItem mealsItem);
    public void deleteFromPlan(MealPlan mealPlan);
    public void deleteFromFav(MealsItem mealsItem);


}
