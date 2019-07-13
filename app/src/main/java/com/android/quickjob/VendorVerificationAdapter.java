package com.android.quickjob;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorVerificationAdapter extends RecyclerView.Adapter<VendorVerificationAdapter.VendorVerificationHolder> {
    private ArrayList<VendorAddVerify> mVendorList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class VendorVerificationHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public VendorVerificationHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_add);
            mTextView1 = itemView.findViewById(R.id.verificationName);
            mTextView2 = itemView.findViewById(R.id.verificationNumber);
            mTextView3 = itemView.findViewById(R.id.verificationEmail);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }
    }

    public VendorVerificationAdapter(ArrayList<VendorAddVerify> mVendorList) {
        this.mVendorList = mVendorList;
    }

    @NonNull
    @Override
    public VendorVerificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.verification_cardview, viewGroup, false);
        VendorVerificationHolder vvh = new VendorVerificationHolder(v, mListener);
        return vvh;
    }

    @Override
    public void onBindViewHolder(@NonNull VendorVerificationHolder vendorVerificationHolder, int i) {
        VendorAddVerify currentVendor = mVendorList.get(i);
        vendorVerificationHolder.mTextView1.setText("Name " + currentVendor.getVendorName());
        vendorVerificationHolder.mTextView2.setText("Number " + currentVendor.getVendorNumber());
        vendorVerificationHolder.mTextView3.setText("Email "+currentVendor.getEmail());
    }

    @Override
    public int getItemCount() {
        return mVendorList.size();
    }

}
