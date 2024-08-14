package com.example.foodplannerapp.ui.favorite;


import android.content.Context;

import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;
import com.example.foodplannerapp.data.repository.Repository;

import java.util.List;

public class FavoritePresenter {
    FavoriteInterface favoriteInterface;
    Repository repository;
    Context context;

    public boolean isUser = false;
    public FavoritePresenter(Context context, FavoriteInterface favoriteInterface) {
        repository = Repository.getInstance(context);
        isUser = repository.isUser();
        this.favoriteInterface = favoriteInterface;
    }

    public void removeFavorite(MealsItem mealsItem, DataFetch<Void> dataFetch) {
        repository.deleteFavorite(mealsItem, dataFetch);
    }
    public void getFavorites(DataFetch<List<MealsItem>> dataFetch) {
        repository.showFavouriteMealsDataBase(dataFetch);
    }
}
