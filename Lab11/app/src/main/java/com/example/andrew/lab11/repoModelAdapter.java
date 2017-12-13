package com.example.andrew.lab11;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/12/13.
 */


/**
 * Created by Andrew on 2017/12/4.
 */

public class repoModelAdapter extends RecyclerView.Adapter<repoModelAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{

    private ArrayList<model> myData;

    public repoModelAdapter(ArrayList<model> a) {
        myData = a;
    }

    public void setData(ArrayList<model> a) {
        myData = a;
    }

    public  static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public  static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_tab, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,  int position) {
        viewHolder.tv.setText(myData.get(position).getName());
        viewHolder.tv1.setText(myData.get(position).getLanguage());
        viewHolder.tv2.setText(myData.get(position).getDescription());
        viewHolder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v,(int)v.getTag());
        }
        return false;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv1, tv2;
        public ViewHolder(View view){
            super(view);
            tv = (TextView) view.findViewById(R.id.name);
            tv1 = (TextView) view.findViewById(R.id.ID);
            tv2 = (TextView) view.findViewById(R.id.dir);
        }
    }
}

