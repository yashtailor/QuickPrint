package com.android.quickjob;

import java.util.Date;

public class VendorPreviousOrdersItems {

    private String userName;
    private Date timeOfCompletion;
    //private int cost;

    public VendorPreviousOrdersItems(Date timeOfCompletion, String userName) {
        this.timeOfCompletion = timeOfCompletion;
        this.userName = userName;
        //this.cost = cost;
    }


    public Date getTimeOfCompletion() {
        return timeOfCompletion;
    }

    public void setTimeOfCompletion(Date timeOfCompletion) {
        this.timeOfCompletion = timeOfCompletion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String vendorName) {
        this.userName = vendorName;
    }

    //public int getCost() {
        //return cost;
    //}

   // public void setCost(int cost) {
        //this.cost = cost;
   // }

    public VendorPreviousOrdersItems() {
    }
}
