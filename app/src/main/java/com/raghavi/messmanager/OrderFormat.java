package com.raghavi.messmanager;

/**
 * Created by Raghavi on 6/10/2018.
 */

public class OrderFormat {
    private String foodItemName;
    private String userID;

    public  OrderFormat(String foodItemNameName,String userID) {
        this.foodItemName = foodItemNameName;
        this.userID=userID;

    }

    @Override
    public String toString() {
        return foodItemName+userID;
    }

    public  OrderFormat()
    {
        foodItemName= "";
        userID="";

    }


    public String getFoodItemName() {

        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {

        this.foodItemName = foodItemName;
    }



    public void setuserID(String userID) {

        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
