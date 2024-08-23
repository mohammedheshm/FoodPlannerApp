package com.example.foodplannerapp.ui.search;


import android.content.Context;

import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.LocalDataSource;
import com.example.foodplannerapp.data.repository.RemoteDataSource;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesManger;

import java.util.Random;


public class SearchPresenter {

    public static final String AREA= "AREA";
    public static final String CATEGORY= "CATEGORY";
    public static final String INGREDIENT= "INGREDIENT";
    public static final String SEARCH= "SEARCH";
    private Repository repository;
    private SearchInterface searchInterface;


    public SearchPresenter(Context context,SearchInterface searchInterface) {
        this.searchInterface = searchInterface;
        repository = Repository.getInstance(context);
    }
    public String[] getCashList(int type){
        String[] result = {""};
        switch (type){
            case SearchInterface.SEARCH:
                break;
            case SearchInterface.AREA:
                result = LocalDataSource.getList(SharedPrefrencesManger.AREAS);
                break;
            case SearchInterface.INGREDIENT:
                result = LocalDataSource.getList(SharedPrefrencesManger.INGREDIENTS);
                break;
            case SearchInterface.CATEGORY:
                result = LocalDataSource.getList(SharedPrefrencesManger.CATEGORIES);
                break;
        }
        return result;
    }
    public void saveFavorite(MealsItem item, RepoInterface<Void> repoInterface){
        repository.insertFavoriteMealDataBase(item, repoInterface);
    }


    public String getRandomList(int type){
        int random = 0;
        String[] cashList;
        String reVal = "";
        switch (type){
            case SearchInterface.AREA:
                cashList = LocalDataSource.getList(SharedPrefrencesManger.AREAS);
                random = new Random().nextInt(cashList.length);
                reVal = cashList[random];
                break;

            case SearchInterface.CATEGORY:
                cashList = LocalDataSource.getList(SharedPrefrencesManger.CATEGORIES);
                random = new Random().nextInt(cashList.length);
                reVal = cashList[random];
                break;

            case SearchInterface.INGREDIENT:
                cashList = LocalDataSource.getList(SharedPrefrencesManger.INGREDIENTS);
                random = new Random().nextInt(cashList.length);
                reVal = cashList[random];
                break;


        }
        return  reVal;
    }

    public void getSearchResultMeals(int type,String query){
        switch (type){
            case SearchInterface.AREA:
                RemoteDataSource.retrieveFilterResults(null, null, query,searchInterface);
                break;
            case SearchInterface.CATEGORY:
                RemoteDataSource.retrieveFilterResults(query, null, null,searchInterface);
                break;
            case SearchInterface.INGREDIENT:
                RemoteDataSource.retrieveFilterResults(null, query, null,searchInterface);
                break;
            case SearchInterface.SEARCH:
                RemoteDataSource.searchMealsByName(query,searchInterface);
                break;


        }


    }

}
