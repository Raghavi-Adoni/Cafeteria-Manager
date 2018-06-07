package com.raghavi.messmanager;

import java.util.ArrayList;

/**
 * Created by Raghavi on 6/3/2018.
 */


public class FoodItemData {

    private String foodItemName;
    //public static ArrayList<FoodItemData> dataset=new ArrayList<>();


    public FoodItemData(String subName) {
        this.foodItemName = subName;

    }

    public FoodItemData()
    {
        foodItemName= "";

    }

    public FoodItemData(FoodItemData value) {
        this.foodItemName=String.valueOf(value);
    }

    public String getFoodItemName() {

        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {

        this.foodItemName = foodItemName;
    }



}
