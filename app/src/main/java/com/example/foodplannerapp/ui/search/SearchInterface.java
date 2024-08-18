package com.example.foodplannerapp.ui.search;

import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;

import java.util.List;

public interface SearchInterface extends DataFetch<List<MealsItem>>{
    public static final int AREA = 1;
    public static final int INGREDIENT = 2;
    public static final int CATEGORY = 3;
    public static final int SEARCH = 4;
    public void onSavePlane(MealsItem item);
    public void onSaveFavorite(MealsItem item);
}
