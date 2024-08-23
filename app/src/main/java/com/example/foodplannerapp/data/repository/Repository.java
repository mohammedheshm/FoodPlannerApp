package com.example.foodplannerapp.data.repository;

import android.content.Context;
import com.example.foodplannerapp.data.network.ApiCalls;
import com.example.foodplannerapp.data.network.Network;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.pojo.meals.MealsList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {

    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    public static Repository repository = null;

    public static Repository getInstance(Context context) {
        if (repository == null)
            repository = new Repository(context);
        return repository;
    }

    private Repository(Context context) {
        ApiCalls apiCalls = Network.apiCalls;  // Get the ApiCalls instance
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource(context, apiCalls);  // Pass both context and apiCalls to RemoteDataSource
    }




    // Insert favorite meal by fetching data from the API, then storing it locally
    public void insertFavoriteMealDataBase(MealsItem mealsItem, RepoInterface<Void> repoInterface) {
        remoteDataSource.retrieveMealByID(mealsItem.getIdMeal(), new RepoInterface<List<MealsItem>>() {
            @Override
            public void onDataLoading() {
                repoInterface.onDataLoading();
            }

            @Override
            public void onDataSuccessResponse(List<MealsItem> mealsItems) {
                if (!mealsItems.isEmpty()) {
                    localDataSource.insertFavoriteToRoom(mealsItems.get(0), repoInterface);
                } else {
                    repoInterface.onDataFailedResponse("No meals found");
                }
            }

            @Override
            public void onDataFailedResponse(String errorMessage) {
                repoInterface.onDataFailedResponse(errorMessage);
            }
        });
    }




    // Insert meal plan by fetching data from the API, then storing it locally
    public void insertPlaneMealDataBase(MealPlan mealPlan, RepoInterface<Void> repoInterface) {
        remoteDataSource.retrieveMealByID(mealPlan.getIdMeal(), new RepoInterface<List<MealsItem>>() {
            @Override
            public void onDataLoading() {
                repoInterface.onDataLoading();
            }

            @Override
            public void onDataSuccessResponse(List<MealsItem> mealsItems) {
                if (!mealsItems.isEmpty()) {
                    localDataSource.insertPlanMealRoom(mealPlan.migrateMealsToPlaneModel(mealsItems.get(0)), repoInterface);
                } else {
                    repoInterface.onDataFailedResponse("No meals found");
                }
            }

            @Override
            public void onDataFailedResponse(String errorMessage) {
                repoInterface.onDataFailedResponse(errorMessage);
            }
        });
    }
}
