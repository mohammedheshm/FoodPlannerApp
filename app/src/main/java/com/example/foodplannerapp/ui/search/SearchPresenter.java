package com.example.foodplannerapp.ui.search;


import android.content.Context;

import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;
import com.example.foodplannerapp.data.sharedpref.SharedPrefrencesFactory;

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
                result = repository.getList(SharedPrefrencesFactory.AREAS);
                break;
            case SearchInterface.INGREDIENT:
                result = repository.getList(SharedPrefrencesFactory.INGREDIENTS);
                break;
            case SearchInterface.CATEGORY:
                result = repository.getList(SharedPrefrencesFactory.CATEGORIES);
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
                cashList = repository.getList(SharedPrefrencesFactory.AREAS);
                random = new Random().nextInt(cashList.length);
                reVal = cashList[random];
                break;

            case SearchInterface.CATEGORY:
                cashList = repository.getList(SharedPrefrencesFactory.CATEGORIES);
                random = new Random().nextInt(cashList.length);
                reVal = cashList[random];
                break;

            case SearchInterface.INGREDIENT:
                cashList = repository.getList(SharedPrefrencesFactory.INGREDIENTS);
                random = new Random().nextInt(cashList.length);
                reVal = cashList[random];
                break;


        }
        return  reVal;
    }

    public void getSearchResultMeals(int type,String query){
        switch (type){
            case SearchInterface.AREA:
                repository.retrieveFilterResults(null, null, query,searchInterface);
                break;
            case SearchInterface.CATEGORY:
                repository.retrieveFilterResults(query, null, null,searchInterface);
                break;
            case SearchInterface.INGREDIENT:
                repository.retrieveFilterResults(null, query, null,searchInterface);
                break;
            case SearchInterface.SEARCH:
                repository.searchMealsByName(query,searchInterface);
                break;


        }


    }

}
