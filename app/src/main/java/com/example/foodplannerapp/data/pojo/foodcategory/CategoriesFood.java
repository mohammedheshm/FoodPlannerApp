package com.example.foodplannerapp.data.pojo.foodcategory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFood {

    @SerializedName("meals")
    private List<CategoriesItem> categories = new ArrayList<>();

    public void setCategories(List<CategoriesItem> categories){
        this.categories = categories;
    }

    public List<CategoriesItem> getCategories(){
        return categories;
    }

    @Override
    public String toString(){
        return
                "Categories{" +
                        "Category = '" + categories + '\'' +
                        "}";
    }
}