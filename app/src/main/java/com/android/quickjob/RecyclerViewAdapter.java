package com.android.quickjob;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public ArrayList<FileItems> fileList;
    Context context;
    private OnItemClickListener mListener;
    Uri fileUri;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onRadioClick(int position, int index);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public RecyclerViewAdapter(ArrayList<FileItems> fileList, Context context) {
        this.fileList = fileList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardviewitems, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        FileItems fileItems = fileList.get(i);
        viewHolder.txtPages.setText("Number of Pages:" + fileItems.getNumberOfPages());
        viewHolder.txtCost.setText("Cost of File:" + fileItems.getFileCost());
        viewHolder.txtName.setText("Name:" + fileItems.getFileName());
        viewHolder.btnBandW.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCost, txtPages;
        Button btnDelete;
        RadioButton btnBandW, btnColor;
        RadioGroup colorDecider;

        public ViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.fileName);
            txtCost = (TextView) itemView.findViewById(R.id.fileCost);
            txtPages = (TextView) itemView.findViewById(R.id.filePages);
            btnDelete = (Button) itemView.findViewById(R.id.deleteItem);
            btnBandW = (RadioButton) itemView.findViewById(R.id.blackAndWhite);
            btnColor = (RadioButton) itemView.findViewById(R.id.color);
            colorDecider = (RadioGroup) itemView.findViewById(R.id.colorCombo);

            btnDelete.setOnClickListener(new View.OnClickListener() {
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

            itemView.findViewById(R.id.blackAndWhite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            btnBandW.setChecked(true);
                            listener.onRadioClick(position, 0);
                        }
                    }
                }
            });

            itemView.findViewById(R.id.color).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            btnColor.setChecked(true);
                            listener.onRadioClick(position, 1);
                        }
                    }
                }
            });
        }

    }
}
