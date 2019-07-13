package com.android.quickjob;

public class VendorData {
    private String vendorName;
    private int vendorNumber;
    private int vendorCost;

    public VendorData(String name, int number, int cost) {
        this.vendorName = name;
        this.vendorNumber = number;
        this.vendorCost = cost;
    }

    public String getName() {
        return vendorName;
    }

    public int getNumber() {
        return vendorNumber;
    }

    public int getCost() {
        return vendorCost;
    }
}
