package com.example.andrew.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/10/18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private ArrayList<ListItems> myData;

    public CartAdapter(ArrayList<ListItems> a) {
//        for(int i = 0; i < a.size(); i++) {
//            myData.add(a.get(i));
//        }
        myData = a;
    }

    public  static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public  static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private CartAdapter.OnItemClickListener mOnItemClickListener = null;
    private CartAdapter.OnItemLongClickListener mOnItemLongClickListener = null;

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_info_in_cart, viewGroup, false);
        CartAdapter.ViewHolder vh = new CartAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, int position) {
        viewHolder.tv.setText(myData.get(position).getName());
        viewHolder.tv1.setText(myData.get(position).getName().substring(0,1));
        viewHolder.tv2.setText(myData.get(position).getPrice());
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


    public void setOnItemClickListener(CartAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(CartAdapter.OnItemLongClickListener listener) {
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
            tv = (TextView) view.findViewById(R.id.item_name);
            tv1 = (TextView) view.findViewById(R.id.item_header);
            tv2 = (TextView) view.findViewById(R.id.item_price);
        }
    }

}
