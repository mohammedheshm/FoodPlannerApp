package com.example.foodplannerapp.ui.category;

import android.content.Context;
import com.example.foodplannerapp.data.pojo.category.Category;
import com.example.foodplannerapp.data.pojo.countries.Area;
import com.example.foodplannerapp.data.pojo.ingredient.Ingredient;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;
import java.util.List;

public class CategoryPresenter {
    Repository repository;

    public CategoryPresenter(Context context) {
        repository = Repository.getInstance(context);
    }

    public void getCategories(RepoInterface<List<MealsItem>> repoInterface){
        repository.retrieveFilterResults(null,null,"American", repoInterface);
    }

    public void getFilterAreaResults(RepoInterface<List<Area>> repoInterface){
        repository.areasList(repoInterface);
    }

    public void getFilterCategoryResults(RepoInterface<List<Category>> repoInterface){
        repository.categoriesList(repoInterface);
    }

    public void getFilterIngredientResults(RepoInterface<List<Ingredient>> repoInterface){
        repository.ingredientsList(repoInterface);
    }



}
