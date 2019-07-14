package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.VendorListHolder> {
    private ArrayList<VendorData> mVendorData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class VendorListHolder extends RecyclerView.ViewHolder {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public VendorListHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public VendorListAdapter(ArrayList<VendorData> mVendorData) {
        this.mVendorData = mVendorData;
    }

    @NonNull
    @Override
    public VendorListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vendor_namelist_cardview, viewGroup, false);
        VendorListHolder vlh = new VendorListHolder(v, mListener);
        return vlh;
    }

    @Override
    public void onBindViewHolder(@NonNull VendorListHolder vendorListHolder, int i) {
        VendorData currentVendor = mVendorData.get(i);

        vendorListHolder.mTextView1.setText(currentVendor.getName());
        vendorListHolder.mTextView3.setText("Email id: "+"" + currentVendor.getVendorEmail());
        vendorListHolder.mTextView2.setText("" + currentVendor.getNumber());
    }

    @Override
    public int getItemCount() {
        return mVendorData.size();
    }
}
