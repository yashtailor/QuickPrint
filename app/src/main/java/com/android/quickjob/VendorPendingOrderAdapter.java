package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorPendingOrderAdapter extends RecyclerView.Adapter<VendorPendingOrderAdapter.VendorPendingOrderHolder> {

    private ArrayList<PendingOrderData> pendingOrderData;

    public static class VendorPendingOrderHolder extends RecyclerView.ViewHolder{

        public TextView vTextView1;
        public TextView vTextView2;

        public VendorPendingOrderHolder(View itemView){
            super(itemView);
            vTextView1 = itemView.findViewById(R.id.pendingorderprinttype);
            vTextView2 = itemView.findViewById(R.id.pendingordername);
        }
    }

    public VendorPendingOrderAdapter(ArrayList<PendingOrderData> orderData){
        this.pendingOrderData = orderData ;
    }

    @NonNull
    @Override
    public VendorPendingOrderAdapter.VendorPendingOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_order_cardview,viewGroup,false);
        VendorPendingOrderAdapter.VendorPendingOrderHolder vpoh = new VendorPendingOrderAdapter.VendorPendingOrderHolder(v);
        return vpoh;
    }

    @Override
    public void onBindViewHolder(@NonNull VendorPendingOrderAdapter.VendorPendingOrderHolder vendorPendingOrderHolder, int i) {

        PendingOrderData currentData = pendingOrderData.get(i);
        vendorPendingOrderHolder.vTextView1.setText(currentData.getrecyclerOrderName());
        vendorPendingOrderHolder.vTextView2.setText(currentData.getrecyclerPrintType());
    }

    @Override
    public int getItemCount() {
        return pendingOrderData.size();
    }

}
