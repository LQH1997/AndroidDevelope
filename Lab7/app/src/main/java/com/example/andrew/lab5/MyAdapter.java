package com.example.andrew.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;
import java.util.ArrayList;
import android.view.ViewGroup;

/**
 * Created by Andrew on 2017/10/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{

    private ArrayList<ListItems> myData;

    public MyAdapter(ArrayList<ListItems> a) {
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,  int position) {
        viewHolder.tv.setText(myData.get(position).getName());
        viewHolder.tv1.setText(myData.get(position).getName().substring(0,1));
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
        TextView tv, tv1;
        public ViewHolder(View view){
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
            tv1 = (TextView) view.findViewById(R.id.header);
            //tv2 = (TextView) view.findViewById(R.id.)
        }
    }
}
