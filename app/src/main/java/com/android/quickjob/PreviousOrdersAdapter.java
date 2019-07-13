package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PreviousOrdersAdapter extends RecyclerView.Adapter<PreviousOrdersAdapter.PreviosOrdersViewHolder> {

    private  static  ArrayList<PreviousOrdersItems> previousOrdersItems;
    OnDeleteIconClickListener listener;

    public PreviousOrdersAdapter(ArrayList<PreviousOrdersItems> arrayList) {
        previousOrdersItems = arrayList;
    }

    public interface  OnDeleteIconClickListener{
        public void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnDeleteIconClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public PreviosOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.previous_orders_cardview, null);
        return new PreviosOrdersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PreviosOrdersViewHolder previosOrdersViewHolder, int i) {
        PreviousOrdersItems ordersItems = previousOrdersItems.get(i);
        previosOrdersViewHolder.fileName.setText(ordersItems.getFileName());
        previosOrdersViewHolder.vendorName.setText(ordersItems.getVendorName());
        previosOrdersViewHolder.fileCost.setText(ordersItems.getCost());
        previosOrdersViewHolder.time.setText(ordersItems.getTimeOfCompletion());
    }

    @Override
    public int getItemCount() {
        return previousOrdersItems.size();
    }

    public class PreviosOrdersViewHolder extends RecyclerView.ViewHolder{

        TextView fileName,fileCost,vendorName,time;
        Button btnDelete;

        public PreviosOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = (TextView)itemView.findViewById(R.id.fileNamePreviousOrder);
            fileCost = (TextView)itemView.findViewById(R.id.fileCostPreviousOrder);
            vendorName = (TextView)itemView.findViewById(R.id.VendorNamePreviousOrder);
            time = (TextView)itemView.findViewById(R.id.finishTimePreviousOrder);
            btnDelete = (Button)itemView.findViewById(R.id.deleteItemPreviousOrder);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }


    }
}
