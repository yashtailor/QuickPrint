package com.android.quickjob;

public class OrderData {


    private String vOrderName;



    public OrderData(String vOrderName){
        this.vOrderName=vOrderName;
    }

    public void setvOrderName(String vOrderName) {
        this.vOrderName = vOrderName;
    }

    public String getRecyclerOrderName() {
        return vOrderName;
    }
}
