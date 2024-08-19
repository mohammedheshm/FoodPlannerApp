package com.example.foodplannerapp.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteDAO {

    @Query("SELECT * FROM meals")
    public Single<List<MealsItem>> showFavouriteMeals();

    @Query("DELETE FROM meals")
    public Completable removeAllTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertFavoriteMeal(MealsItem mealsItem);

    @Delete
    public Completable deleteFavouriteMeal(MealsItem mealsItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertAllTable(List<MealsItem> mealPlanList);
}
