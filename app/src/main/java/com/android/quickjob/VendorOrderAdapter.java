package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorOrderAdapter extends RecyclerView.Adapter<VendorOrderAdapter.VendorOrderHolder> {
    private ArrayList<OrderData> orderData;

    public static class VendorOrderHolder extends RecyclerView.ViewHolder{

        public TextView vTextView1;
        public TextView vTextView2;

        public VendorOrderHolder(View itemView){
            super(itemView);
            vTextView1 = itemView.findViewById(R.id.pendingorderprinttype);
            vTextView2 = itemView.findViewById(R.id.pendingordername);
        }
    }

    public VendorOrderAdapter(ArrayList<OrderData> orderData){
        this.orderData = orderData ;
    }

    @NonNull
    @Override
    public VendorOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vendor_card_view,viewGroup,false);
        VendorOrderHolder voh = new VendorOrderHolder(v);
        return voh;
    }

    @Override
    public void onBindViewHolder(@NonNull VendorOrderHolder vendorOrderHolder, int i) {

        OrderData currentData = orderData.get(i);
        vendorOrderHolder.vTextView1.setText(currentData.getRecyclerOrderName());
    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

}
