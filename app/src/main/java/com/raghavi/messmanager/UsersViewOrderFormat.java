package com.raghavi.messmanager;

/**
 * Created by Raghavi on 6/17/2018.
 */

public class UsersViewOrderFormat {

    private String foodItemName;
    private String orderStatus;
    private String time;


    public UsersViewOrderFormat(String foodItemNameName, String orderStatus,String time) {
        this.foodItemName = foodItemNameName;
        this.orderStatus = orderStatus;
        this.time=time;
    }


    @Override
    public String toString() {
        return foodItemName + orderStatus+time;
    }


    public String getFoodItemName() {

        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {

        this.foodItemName = foodItemName;
    }


    public String getOrderStatus() {

        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {

        this.orderStatus = orderStatus;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

}


