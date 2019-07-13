package com.android.quickjob;

public class OrderData {


    private String vOrderName;
    private String vPrintTyoe;


    public OrderData(String vOrderName,String vPrintType){
        this.vOrderName=vOrderName;
        this.vPrintTyoe=vPrintType;
    }


    public String getrecyclerPrintType() {
        return vPrintTyoe;
    }

    public String getrecyclerOrderName() {
        return vOrderName;
    }
}
