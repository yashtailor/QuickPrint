package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorsPreviousOrdersAdapter extends RecyclerView.Adapter<VendorsPreviousOrdersAdapter.PreviosOrdersViewHolder> {

    private  static  ArrayList<VendorPreviousOrdersItems> previousOrdersItems;
    OnVendorDeleteIconClickListener listener;

    public VendorsPreviousOrdersAdapter(ArrayList<VendorPreviousOrdersItems> arrayList) {
        previousOrdersItems = arrayList;
    }

    public interface  OnVendorDeleteIconClickListener{
        void onVendorDeleteClick(int position);
    }

    public void setOnItemClickListener(OnVendorDeleteIconClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public PreviosOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vendors_previous_orders_cardview, viewGroup, false);
        return new PreviosOrdersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PreviosOrdersViewHolder previosOrdersViewHolder, int i) {
        VendorPreviousOrdersItems ordersItems = previousOrdersItems.get(i);
        previosOrdersViewHolder.fileName.setText(ordersItems.getFileName());
        previosOrdersViewHolder.userName.setText(ordersItems.getUserName());
        previosOrdersViewHolder.fileCost.setText(Integer.toString(ordersItems.getCost()));
        previosOrdersViewHolder.time.setText(ordersItems.getTimeOfCompletion());
    }

    @Override
    public int getItemCount() {
        return previousOrdersItems.size();
    }

    public class PreviosOrdersViewHolder extends RecyclerView.ViewHolder{

        TextView fileName,fileCost,userName,time;
        Button btnDelete;

        public PreviosOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = (TextView)itemView.findViewById(R.id.fileNamePreviousOrderVendor);
            fileCost = (TextView)itemView.findViewById(R.id.fileCostPreviousOrderVendor);
            userName = (TextView)itemView.findViewById(R.id.userNamePreviousOrderVendor);
            time = (TextView)itemView.findViewById(R.id.finishTimePreviousOrderVendor);
            btnDelete = (Button)itemView.findViewById(R.id.deleteItemPreviousOrderVendor);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        if(position != RecyclerView.NO_POSITION){
                            listener.onVendorDeleteClick(position);
                        }
                    }
                }
            });
        }


    }
}
