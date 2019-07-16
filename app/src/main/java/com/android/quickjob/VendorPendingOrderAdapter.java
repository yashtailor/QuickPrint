package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorPendingOrderAdapter extends RecyclerView.Adapter<VendorPendingOrderAdapter.VendorPendingOrderHolder> {

    private ArrayList<OrderData> pendingOrderData;
    private OnCheckBoxPressed listener;

    public interface OnCheckBoxPressed{
         void onCheck(int pos);
    }

    public void setOnCheckBoxPressed(OnCheckBoxPressed listener){
        this.listener = listener;
    }

    public static class VendorPendingOrderHolder extends RecyclerView.ViewHolder{

        private TextView vTextView1;
        private CheckBox doneOrder;

        public VendorPendingOrderHolder(View itemView,final OnCheckBoxPressed listener){
            super(itemView);
            vTextView1 = itemView.findViewById(R.id.pendingordername);
            doneOrder = itemView.findViewById(R.id.checkboxDoneOrder);

            doneOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null){
                        listener.onCheck(getAdapterPosition());
                    }
                }
            });

        }
    }

    public VendorPendingOrderAdapter(ArrayList<OrderData> orderData){
        this.pendingOrderData = orderData ;
    }

    @NonNull
    @Override
    public  VendorPendingOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pending_order_cardview,viewGroup,false);
        return new VendorPendingOrderHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorPendingOrderAdapter.VendorPendingOrderHolder vendorPendingOrderHolder, int i) {

        OrderData currentData = pendingOrderData.get(i);
        vendorPendingOrderHolder.vTextView1.setText(currentData.getrecyclerOrderName());

    }

    @Override
    public int getItemCount() {
        return pendingOrderData.size();
    }

}
