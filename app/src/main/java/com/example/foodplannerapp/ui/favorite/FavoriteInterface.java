package com.example.foodplannerapp.ui.favorite;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;

import java.util.List;


public interface FavoriteInterface extends RepoInterface<List<MealsItem>> {
    public void addToPlan(MealPlan mealPlan);
    public void deleteFromPlan(MealPlan mealPlan);

}
