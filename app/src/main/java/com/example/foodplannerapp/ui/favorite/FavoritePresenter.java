package com.example.foodplannerapp.ui.favorite;


import android.content.Context;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;

import java.util.List;

public class FavoritePresenter {
    FavoriteInterface favoriteInterface;
    Repository repository;
    Context context;

    public FavoritePresenter(Context context, FavoriteInterface favoriteInterface) {
        repository = Repository.getInstance(context);
        this.favoriteInterface = favoriteInterface;
    }

    public void removeFavorite(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
        repository.deleteFavorite(mealsItem, repoInterface);
    }

    public void getFavorites(RepoInterface<List<MealsItem>> repoInterface) {
        repository.showFavouriteMealsDataBase(repoInterface);
    }

}

