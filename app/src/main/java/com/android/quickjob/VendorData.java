package com.android.quickjob;

public class VendorData {
    private String vendorName;
    private int vendorNumber;
    private String vendorEmail;

    public VendorData(String name, int number, String email) {
        vendorName = name;
        vendorNumber = number;
        vendorEmail=email;
    }

    public String getName() {
        return vendorName;
    }

    public int getNumber() {
        return vendorNumber;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }
}
