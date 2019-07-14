package com.android.quickjob;

public class PreviousOrdersItems {

    private String fileName,timeOfCompletion,vendorName;
    private int cost;

    public PreviousOrdersItems(String fileName, String timeOfCompletion, String vendorName, int cost) {
        this.fileName = fileName;
        this.timeOfCompletion = timeOfCompletion;
        this.vendorName = vendorName;
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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public PreviousOrdersItems() {
    }
}
