package com.android.quickjob;

public class VendorAddVerify {

    private String name;
    private String number;
    private String email;

    public VendorAddVerify() {
    }

    public VendorAddVerify(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public void setVendorName(String name) {
        this.name = name;
    }

    public void setVendorNumber(String number) {
        this.number = number;
    }

    public void setVendorEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getVendorName() {
        return name;
    }

    public String getVendorNumber() {
        return number;
    }

}
