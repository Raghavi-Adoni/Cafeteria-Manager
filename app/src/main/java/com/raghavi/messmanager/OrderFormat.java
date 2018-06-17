package com.raghavi.messmanager;

import android.content.SharedPreferences;

/**
 * Created by Raghavi on 6/10/2018.
 */

public class OrderFormat {
    private String foodItemName;
    private String userID;
    private String time;
    private String orderStatus;
    private String uniqueID;

    public  OrderFormat(String foodItemNameName,String userID,String time,String orderStatus,String uniqueID) {
        this.foodItemName = foodItemNameName;
        this.userID=userID;
        this.time=time;
        this.orderStatus=orderStatus;
        this.uniqueID=uniqueID;
    }


    @Override
    public String toString() {
        return foodItemName+userID+time+orderStatus+uniqueID;
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

    public String getOrderStatus() {

        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {

        this.orderStatus = orderStatus;
    }

    public String getuniqueID() {

        return uniqueID;
    }

    public void setuniqueID(String uniqueID) {

        this.uniqueID = uniqueID;
    }

}
