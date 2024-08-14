package com.example.foodplannerapp.ui.home;

import com.example.foodplannerapp.data.model.meals.MealsItem;

public interface HomeInterface {
    public void onSavePlane(MealsItem item);
    public void onSaveFavorite(MealsItem item);
}
