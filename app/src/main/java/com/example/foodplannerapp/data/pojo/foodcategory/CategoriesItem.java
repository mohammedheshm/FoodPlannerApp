package com.example.foodplannerapp.data.pojo.foodcategory;


public class CategoriesItem{

    private String strCategory;

    private String strCategoryDescription;

    private String idCategory;

    private String strCategoryThumb;

    public CategoriesItem(String strCategory, String strCategoryDescription, String idCategory, String strCategoryThumb) {
        this.strCategory = strCategory;
        this.strCategoryDescription = strCategoryDescription;
        this.idCategory = idCategory;
        this.strCategoryThumb = strCategoryThumb;
    }

    public void setStrCategory(String strCategory){
        this.strCategory = strCategory;
    }

    public String getStrCategory(){
        return strCategory;
    }

    public void setStrCategoryDescription(String strCategoryDescription){
        this.strCategoryDescription = strCategoryDescription;
    }

    public String getStrCategoryDescription(){
        return strCategoryDescription;
    }

    public void setIdCategory(String idCategory){
        this.idCategory = idCategory;
    }

    public String getIdCategory(){
        return idCategory;
    }

    public void setStrCategoryThumb(String strCategoryThumb){
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getStrCategoryThumb(){
        return strCategoryThumb;
    }

}