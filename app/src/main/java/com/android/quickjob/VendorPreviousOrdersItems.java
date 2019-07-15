package com.android.quickjob;

public class VendorPreviousOrdersItems {

    private String fileName,timeOfCompletion,userName;
    private int cost;

    public VendorPreviousOrdersItems(String fileName, String timeOfCompletion, String userName, int cost) {
        this.fileName = fileName;
        this.timeOfCompletion = timeOfCompletion;
        this.userName = userName;
        this.cost = cost;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTimeOfCompletion() {
        return timeOfCompletion;
    }

    public void setTimeOfCompletion(String timeOfCompletion) {
        this.timeOfCompletion = timeOfCompletion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String vendorName) {
        this.userName = vendorName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public VendorPreviousOrdersItems() {
    }
}
