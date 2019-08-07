package com.android.quickjob;

public class VendorData {
    private String vendorName;
    private String vendorNumber;
    private String vendorEmail;


    public VendorData(String name, String number, String email) {
        vendorName = name;
        vendorNumber = number;
        vendorEmail = email;
    }

    public String getName() {
        return vendorName;
    }

    public String getNumber() {
        return vendorNumber;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

}
