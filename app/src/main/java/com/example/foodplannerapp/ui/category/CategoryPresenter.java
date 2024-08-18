package com.example.foodplannerapp.ui.category;

import android.content.Context;

import com.example.foodplannerapp.data.model.category.Category;
import com.example.foodplannerapp.data.model.countries.Area;
import com.example.foodplannerapp.data.model.ingredient.Ingredient;
import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;
import com.example.foodplannerapp.data.repository.Repository;

import java.util.List;

public class CategoryPresenter {
    Repository repository;

    public CategoryPresenter(Context context) {
        repository = Repository.getInstance(context);
    }

    public void getCategories(DataFetch<List<MealsItem>> dataFetch) {
        repository.retrieveFilterResults(null, null, "American", dataFetch);
    }

    public void getFilterAreaResults(DataFetch<List<Area>> dataFetch) {
        repository.areasList(dataFetch);
    }

    public void getFilterCategoryResults(DataFetch<List<Category>> dataFetch) {
        repository.categoriesList(dataFetch);
    }

    public void getFilterIngredientResults(DataFetch<List<Ingredient>> dataFetch) {
        repository.ingredientsList(dataFetch);
    }

}