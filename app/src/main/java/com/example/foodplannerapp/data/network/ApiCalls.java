package com.example.foodplannerapp.data.network;


import com.example.foodplannerapp.data.model.category.CategoriesList;
import com.example.foodplannerapp.data.model.countries.AreasList;
import com.example.foodplannerapp.data.model.foodcategory.CategoriesFeed;
import com.example.foodplannerapp.data.model.ingredient.IngredientsList;
import com.example.foodplannerapp.data.model.meals.MealsList;

import io.reactivex.rxjava3.core.Single;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {
    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    @GET("random.php")
    public Single<MealsList> lookupSingleRandomMeal();

    @GET("list.php?i=list")
    public Single<IngredientsList> ingredientsList();

    @GET("list.php?c=list")
    public Single<CategoriesList> categoriesList();


    @GET("list.php?a=list")
    public Single<AreasList> areasList();

    @GET("filter.php")
    public Single<MealsList> retrieveFilterResults(
            @Query("c") String category,
            @Query("i") String ingredient,
            @Query("a") String area
    );

    @GET("categories.php")
    public Single<CategoriesFeed> retrieveCategoriesList();


    @GET("search.php")
    public Single<MealsList> searchMealsByName(@Query("s") String search);


    @GET("lookup.php")
    public Single<MealsList> retrieveMealByID(@Query("i") String id);


}
