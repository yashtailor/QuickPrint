package com.android.quickjob;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static  ArrayList<FileItems> fileList;
    Context context;
    private OnItemClickListener mListener;
    Uri fileUri;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onColorChange(int position,int i);
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
        //viewHolder.txtPages.setText("Number of Pages:" + fileItems.getNumberOfPages());
        viewHolder.txtCost.setText("Cost of one page:" + fileItems.getFileCost());
        viewHolder.txtName.setText("Name:" + fileItems.getFileName());
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCost, txtPages,txtBW,txtColor;
        Button btnDelete;

        public ViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.fileName);
            txtCost = (TextView) itemView.findViewById(R.id.fileCost);
            txtPages = (TextView) itemView.findViewById(R.id.filePages);
            btnDelete = (Button) itemView.findViewById(R.id.deleteItem);
            txtBW=(TextView) itemView.findViewById(R.id.blackNWhite);
            txtColor=(TextView) itemView.findViewById(R.id.color);

            txtBW.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onColorChange(position,0);
                        }
                    }
                }
            });
            txtColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onColorChange(position,1);
                        }
                    }
                }
            });

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
        }

    }
}
