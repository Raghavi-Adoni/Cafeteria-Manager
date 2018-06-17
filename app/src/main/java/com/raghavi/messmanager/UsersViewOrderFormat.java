package com.raghavi.messmanager;

/**
 * Created by Raghavi on 6/17/2018.
 */

public class UsersViewOrderFormat {

        private String foodItemName;
        private String orderStatus;



        public  UsersViewOrderFormat(String foodItemNameName,String orderStatus) {
            this.foodItemName = foodItemNameName;
            this.orderStatus=orderStatus;


        }


        @Override
        public String toString() {
            return foodItemName+orderStatus;
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


    }


