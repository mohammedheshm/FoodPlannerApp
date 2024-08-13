package com.example.foodplannerapp.data.model.meals;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Entity(tableName = "meals")

public class MealsItem implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "idMealCode")
    @NotNull
    @SerializedName("idMeal")
    private String idMeal;

    @ColumnInfo(name = "country")
    @SerializedName("strArea")
    private String strArea;

    @ColumnInfo
    @SerializedName("strInstructions")
    private String strInstructions;

    @ColumnInfo
    @SerializedName("strMealThumb")
    private String strMealThumb;

    @ColumnInfo
    @SerializedName("strYoutube")
    private String strYoutube;

    @ColumnInfo
    @SerializedName("strMeal")
    private String strMeal;

    @ColumnInfo
    @SerializedName("strCategory")
    private String strCategory;


    // region Ingredients

    @ColumnInfo
    @SerializedName("strIngredient10")
    private String strIngredient10 = "";
    @ColumnInfo
    @SerializedName("strIngredient12")
    private String strIngredient12= "";
    @ColumnInfo
    @SerializedName("strIngredient11")
    private String strIngredient11= "";
    @ColumnInfo
    @SerializedName("strIngredient14")
    private String strIngredient14= "";

    @ColumnInfo
    @SerializedName("strIngredient13")
    private String strIngredient13= "";

    @ColumnInfo
    @SerializedName("strIngredient16")
    private String strIngredient16= "";
    @ColumnInfo
    @SerializedName("strIngredient15")
    private String strIngredient15= "";
    @ColumnInfo
    @SerializedName("strIngredient18")
    private String strIngredient18= "";
    @ColumnInfo
    @SerializedName("strIngredient17")
    private String strIngredient17= "";

    @ColumnInfo
    @SerializedName("strIngredient19")
    private String strIngredient19= "";

    @ColumnInfo
    @SerializedName("strIngredient1")
    private String strIngredient1= "";
    @ColumnInfo
    @SerializedName("strIngredient3")
    private String strIngredient3= "";
    @ColumnInfo
    @SerializedName("strIngredient2")
    private String strIngredient2= "";
    @ColumnInfo
    @SerializedName("strIngredient20")
    private String strIngredient20= "";
    @ColumnInfo
    @SerializedName("strIngredient5")
    private String strIngredient5= "";
    @ColumnInfo
    @SerializedName("strIngredient4")
    private String strIngredient4= "";
    @ColumnInfo
    @SerializedName("strIngredient7")
    private String strIngredient7= "";
    @ColumnInfo
    @SerializedName("strIngredient6")
    private String strIngredient6= "";
    @ColumnInfo
    @SerializedName("strIngredient9")
    private String strIngredient9= "";
    @ColumnInfo
    @SerializedName("strIngredient8")
    private String strIngredient8= "";
    // endregion


    @Ignore
    private ArrayList<String> ingredients;
    public MealsItem(){

    }

    protected MealsItem(Parcel in) {
        idMeal = in.readString();
        strArea = in.readString();
        strInstructions = in.readString();
        strMealThumb = in.readString();
        strYoutube = in.readString();
        strMeal = in.readString();
        strCategory = in.readString();
        strIngredient10 = in.readString();
        strIngredient12 = in.readString();
        strIngredient11 = in.readString();
        strIngredient14 = in.readString();
        strIngredient13 = in.readString();
        strIngredient16 = in.readString();
        strIngredient15 = in.readString();
        strIngredient18 = in.readString();
        strIngredient17 = in.readString();
        strIngredient19 = in.readString();
        strIngredient1 = in.readString();
        strIngredient3 = in.readString();
        strIngredient2 = in.readString();
        strIngredient20 = in.readString();
        strIngredient5 = in.readString();
        strIngredient4 = in.readString();
        strIngredient7 = in.readString();
        strIngredient6 = in.readString();
        strIngredient9 = in.readString();
        strIngredient8 = in.readString();
        ingredients = in.createStringArrayList();
    }

    public static final Creator<MealsItem> CREATOR = new Creator<MealsItem>() {
        @Override
        public MealsItem createFromParcel(Parcel in) {
            return new MealsItem(in);
        }

        @Override
        public MealsItem[] newArray(int size) {
            return new MealsItem[size];
        }
    };

    public void setStrIngredient10(String strIngredient10){
        this.strIngredient10 = strIngredient10;
    }

    public String getStrIngredient10(){
        return strIngredient10;
    }

    public void setStrIngredient12(String strIngredient12){
        this.strIngredient12 = strIngredient12;
    }

    public String getStrIngredient12(){
        return strIngredient12;
    }

    public void setStrIngredient11(String strIngredient11){
        this.strIngredient11 = strIngredient11;
    }

    public String getStrIngredient11(){
        return strIngredient11;
    }

    public void setStrIngredient14(String strIngredient14){
        this.strIngredient14 = strIngredient14;
    }

    public String getStrIngredient14(){
        return strIngredient14;
    }

    public void setStrCategory(String strCategory){
        this.strCategory = strCategory;
    }

    public String getStrCategory(){
        return strCategory;
    }

    public void setStrIngredient13(String strIngredient13){
        this.strIngredient13 = strIngredient13;
    }

    public String getStrIngredient13(){
        return strIngredient13;
    }

    public void setStrIngredient16(String strIngredient16){
        this.strIngredient16 = strIngredient16;
    }

    public String getStrIngredient16(){
        return strIngredient16;
    }

    public void setStrIngredient15(String strIngredient15){
        this.strIngredient15 = strIngredient15;
    }

    public String getStrIngredient15(){
        return strIngredient15;
    }

    public void setStrIngredient18(String strIngredient18){
        this.strIngredient18 = strIngredient18;
    }

    public String getStrIngredient18(){
        return strIngredient18;
    }

    public void setStrIngredient17(String strIngredient17){
        this.strIngredient17 = strIngredient17;
    }

    public String getStrIngredient17(){
        return strIngredient17;
    }

    public void setStrArea(String strArea){
        this.strArea = strArea;
    }

    public String getStrArea(){
        return strArea;
    }


    public void setStrIngredient19(String strIngredient19){
        this.strIngredient19 = strIngredient19;
    }

    public String getStrIngredient19(){
        return strIngredient19;
    }

    public void setIdMeal(String idMeal){
        this.idMeal = idMeal;
    }

    public String getIdMeal(){
        return idMeal;
    }

    public void setStrInstructions(String strInstructions){
        this.strInstructions = strInstructions;
    }

    public String getStrInstructions(){
        return strInstructions;
    }

    public void setStrIngredient1(String strIngredient1){
        this.strIngredient1 = strIngredient1;
    }

    public String getStrIngredient1(){
        return strIngredient1;
    }

    public void setStrIngredient3(String strIngredient3){
        this.strIngredient3 = strIngredient3;
    }

    public String getStrIngredient3(){
        return strIngredient3;
    }

    public void setStrIngredient2(String strIngredient2){
        this.strIngredient2 = strIngredient2;
    }

    public String getStrIngredient2(){
        return strIngredient2;
    }

    public void setStrIngredient20(String strIngredient20){
        this.strIngredient20 = strIngredient20;
    }

    public String getStrIngredient20(){
        return strIngredient20;
    }

    public void setStrIngredient5(String strIngredient5){
        this.strIngredient5 = strIngredient5;
    }

    public String getStrIngredient5(){
        return strIngredient5;
    }

    public void setStrIngredient4(String strIngredient4){
        this.strIngredient4 = strIngredient4;
    }

    public String getStrIngredient4(){
        return strIngredient4;
    }

    public void setStrIngredient7(String strIngredient7){
        this.strIngredient7 = strIngredient7;
    }

    public String getStrIngredient7(){
        return strIngredient7;
    }

    public void setStrIngredient6(String strIngredient6){
        this.strIngredient6 = strIngredient6;
    }

    public String getStrIngredient6(){
        return strIngredient6;
    }

    public void setStrIngredient9(String strIngredient9){
        this.strIngredient9 = strIngredient9;
    }

    public String getStrIngredient9(){
        return strIngredient9;
    }

    public void setStrIngredient8(String strIngredient8){
        this.strIngredient8 = strIngredient8;
    }

    public String getStrIngredient8(){
        return strIngredient8;
    }

    public void setStrMealThumb(String strMealThumb){
        this.strMealThumb = strMealThumb;
    }

    public String getStrMealThumb(){
        return strMealThumb;
    }

    public void setStrYoutube(String strYoutube){
        this.strYoutube = strYoutube;
    }

    public String getStrYoutube(){
        return strYoutube;
    }

    public void setStrMeal(String strMeal){
        this.strMeal = strMeal;
    }

    public String getStrMeal(){
        return strMeal;
    }



    public ArrayList<String> getIngredients() {
        ArrayList<String> ingredientsList = new ArrayList<>();
        if (strIngredient1!=null && !strIngredient1.isEmpty()){
            ingredientsList.add(strIngredient1);
        }

        if (strIngredient2!=null && !strIngredient2.isEmpty()){
            ingredientsList.add(strIngredient2);
        }

        if (strIngredient3!=null && !strIngredient3.isEmpty()){
            ingredientsList.add(strIngredient3);
        }
        if (strIngredient4!=null && !strIngredient4.isEmpty()){
            ingredientsList.add(strIngredient4);
        }
        if (strIngredient5!=null && !strIngredient5.isEmpty()){
            ingredientsList.add(strIngredient5);
        }
        if (strIngredient6!=null && !strIngredient6.isEmpty()){
            ingredientsList.add(strIngredient6);
        }
        if (strIngredient7!=null && !strIngredient7.isEmpty()){
            ingredientsList.add(strIngredient7);
        }
        if (strIngredient8!=null && !strIngredient8.isEmpty()){
            ingredientsList.add(strIngredient8);
        }
        if (strIngredient9!=null && !strIngredient9.isEmpty()){
            ingredientsList.add(strIngredient9);
        }
        if (strIngredient10!=null && !strIngredient10.isEmpty()){
            ingredientsList.add(strIngredient10);
        }
        if (strIngredient11!=null && !strIngredient11.isEmpty()){
            ingredientsList.add(strIngredient11);
        }
        if (strIngredient12!=null && !strIngredient12.isEmpty()){
            ingredientsList.add(strIngredient12);
        }
        if (strIngredient13!=null && !strIngredient13.isEmpty()){
            ingredientsList.add(strIngredient13);
        }
        if (strIngredient14!=null && !strIngredient14.isEmpty()){
            ingredientsList.add(strIngredient14);
        }
        if (strIngredient15!=null && !strIngredient15.isEmpty()){
            ingredientsList.add(strIngredient15);
        }
        if (strIngredient16!=null && !strIngredient16.isEmpty()){
            ingredientsList.add(strIngredient16);
        }
        if (strIngredient17!=null && !strIngredient17.isEmpty()){
            ingredientsList.add(strIngredient17);
        }
        if (strIngredient18!=null && !strIngredient18.isEmpty()){
            ingredientsList.add(strIngredient18);
        }
        if (strIngredient19!=null && !strIngredient19.isEmpty()){
            ingredientsList.add(strIngredient19);
        }
        if (strIngredient20!=null && !strIngredient20.isEmpty()){
            ingredientsList.add(strIngredient20);
        }
        this.ingredients=ingredientsList;
        return ingredientsList;
    }




    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idMeal);
        dest.writeString(strArea);
        dest.writeString(strInstructions);
        dest.writeString(strMealThumb);
        dest.writeString(strYoutube);
        dest.writeString(strMeal);
        dest.writeString(strCategory);
        dest.writeString(strIngredient10);
        dest.writeString(strIngredient12);
        dest.writeString(strIngredient11);
        dest.writeString(strIngredient14);
        dest.writeString(strIngredient13);
        dest.writeString(strIngredient16);
        dest.writeString(strIngredient15);
        dest.writeString(strIngredient18);
        dest.writeString(strIngredient17);
        dest.writeString(strIngredient19);
        dest.writeString(strIngredient1);
        dest.writeString(strIngredient3);
        dest.writeString(strIngredient2);
        dest.writeString(strIngredient20);
        dest.writeString(strIngredient5);
        dest.writeString(strIngredient4);
        dest.writeString(strIngredient7);
        dest.writeString(strIngredient6);
        dest.writeString(strIngredient9);
        dest.writeString(strIngredient8);
        dest.writeStringList(ingredients);
    }

    public MealPlan convertMealsItemToMealsPlan(MealsItem mealsItem){
        MealPlan mealPlan=new MealPlan();
        mealPlan.setIdMeal(mealsItem.getIdMeal());
        mealPlan.setStrArea(mealsItem.getStrArea());
        mealPlan.setStrCategory(mealsItem.getStrCategory());
        mealPlan.setStrMeal(mealsItem.getStrMeal());
        mealPlan.setStrMealThumb(mealsItem.getStrMealThumb());
        mealPlan.setStrYoutube(mealsItem.getStrYoutube());
        mealPlan.setStrInstructions(mealsItem.getStrInstructions());
        mealPlan.setStrIngredient1(mealsItem.getStrIngredient1());
        mealPlan.setStrIngredient2(mealsItem.getStrIngredient2());
        mealPlan.setStrIngredient3(mealsItem.getStrIngredient3());
        mealPlan.setStrIngredient4(mealsItem.getStrIngredient4());
        mealPlan.setStrIngredient5(mealsItem.getStrIngredient5());
        mealPlan.setStrIngredient6(mealsItem.getStrIngredient6());
        mealPlan.setStrIngredient7(mealsItem.getStrIngredient7());
        mealPlan.setStrIngredient8(mealsItem.getStrIngredient8());
        mealPlan.setStrIngredient9(mealsItem.getStrIngredient9());
        mealPlan.setStrIngredient10(mealsItem.getStrIngredient10());
        mealPlan.setStrIngredient11(mealsItem.getStrIngredient11());
        mealPlan.setStrIngredient12(mealsItem.getStrIngredient12());
        mealPlan.setStrIngredient13(mealsItem.getStrIngredient13());
        mealPlan.setStrIngredient14(mealsItem.getStrIngredient14());
        mealPlan.setStrIngredient15(mealsItem.getStrIngredient15());
        mealPlan.setStrIngredient16(mealsItem.getStrIngredient16());
        mealPlan.setStrIngredient17(mealsItem.getStrIngredient17());
        mealPlan.setStrIngredient18(mealsItem.getStrIngredient18());
        mealPlan.setStrIngredient19(mealsItem.getStrIngredient19());
        mealPlan.setStrIngredient20(mealsItem.getStrIngredient20());
        return mealPlan;
    }
}