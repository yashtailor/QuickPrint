package com.android.quickjob;

public class VendorAddVerify {

    private String name;
    private String number;
    private String email;

    public VendorAddVerify() {
    }



    public String getEmail() {
        return email;
    }

    public VendorAddVerify(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }


    public String getVendorName() {
        return name;
    }

    public String getVendorNumber() {
        return number;
    }

}
