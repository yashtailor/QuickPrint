package com.android.quickjob;

public class PendingOrderData {


    private String vPendingOrderName;
    private String vPendingPrintTyoe;


    public PendingOrderData(String vOrderName,String vPrintType){
        this.vPendingOrderName=vOrderName;
        this.vPendingPrintTyoe=vPrintType;
    }


    public String getrecyclerPrintType() {
        return vPendingPrintTyoe;
    }

    public String getrecyclerOrderName() {
        return vPendingOrderName;
    }
}
