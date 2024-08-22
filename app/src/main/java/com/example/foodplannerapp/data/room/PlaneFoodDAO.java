package com.example.foodplannerapp.data.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.ui.plan.Week;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlaneFoodDAO {
    @Query("SELECT * FROM MealPlan")
    public Single<List<MealPlan>> showPlanMeals();

    @Query("DELETE FROM meals")
    public Completable removeAllTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertPlanMeal(MealPlan mealPlan);

    @Delete
    public Completable deletePlanMeal(MealPlan mealPlan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertAllTable(List<MealPlan> mealPlanList);

    @Query("SELECT * FROM MealPlan where day=:dayName" )
    public Single<List<MealPlan>> showPlanMealsByDay(Week dayName);
}
