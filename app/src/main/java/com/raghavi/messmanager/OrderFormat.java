package com.raghavi.messmanager;

/**
 * Created by Raghavi on 6/10/2018.
 */

public class OrderFormat {
    private String foodItemName;
    private String userID;
    private String time;

    public  OrderFormat(String foodItemNameName,String userID,String time) {
        this.foodItemName = foodItemNameName;
        this.userID=userID;
        this.time=time;

    }

    @Override
    public String toString() {
        return foodItemName+userID+time;
    }

    public  OrderFormat()
    {
        foodItemName= "";
        userID="";
        time="";

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

    public void setTime(String time) {

        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
