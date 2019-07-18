package com.android.quickjob;

public class PreviousOrdersItems {

    private String timeOfCompletion,vendorName;

    public PreviousOrdersItems(String fileName, String timeOfCompletion, String vendorName, int cost) {
        this.timeOfCompletion = timeOfCompletion;
        this.vendorName = vendorName;

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


    public PreviousOrdersItems() {
    }
}
