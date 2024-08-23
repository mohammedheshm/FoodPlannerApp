package com.example.foodplannerapp.ui.category;

import android.content.Context;
import com.example.foodplannerapp.data.pojo.category.Category;
import com.example.foodplannerapp.data.pojo.countries.Area;
import com.example.foodplannerapp.data.pojo.ingredient.Ingredient;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RemoteDataSource;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;
import java.util.List;

public class CategoryPresenter {
    Repository repository;

    public CategoryPresenter(Context context) {
        repository = Repository.getInstance(context);
    }

    public void getCategories(RepoInterface<List<MealsItem>> repoInterface){
        RemoteDataSource.retrieveFilterResults(null,null,"Egyptian", repoInterface);
    }

    public void getFilterAreaResults(RepoInterface<List<Area>> repoInterface){
        RemoteDataSource.areasList(repoInterface);
    }

    public void getFilterCategoryResults(RepoInterface<List<Category>> repoInterface){
        RemoteDataSource.categoriesList(repoInterface);
    }

    public void getFilterIngredientResults(RepoInterface<List<Ingredient>> repoInterface){
        RemoteDataSource.ingredientsList(repoInterface);
    }



}
