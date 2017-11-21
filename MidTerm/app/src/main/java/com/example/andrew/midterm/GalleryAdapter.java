package com.example.andrew.midterm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 2017/11/18.
 */

public class GalleryAdapter extends RLoopRecyclerView.LoopAdapter<GalleryAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    public ArrayList<personInfo> myData;

    public GalleryAdapter(ArrayList<personInfo> datas) {
        myData = datas;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.renwukapian, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindLoopViewHolder(ViewHolder viewHolder, int position) {
        int newPosition = position % myData.size();
        viewHolder.tv.setText(myData.get(position).getName());
        viewHolder.tv1.setText(myData.get(position).getShili());
        //int resID = getResources().getIdentifier(myData.get(position).getPersonPic(), "drawable", "com.example.andrew.lab5");
         viewHolder.pic.setImageResource(getResource(myData.get(position).getPersonPic()));
  //      viewHolder.pic.setImageResource();
        viewHolder.itemView.setTag(position);
    }
public int getResource(String name) {
    Class myDrawable = R.drawable.class;
    Field field = null;
    int r_id ;
    try {
        field = myDrawable.getField(name);
        r_id = field.getInt(field.getName());
    } catch (Exception e) {
        r_id=R.drawable.mute;
        Log.e("ERROR", "PICTURE NOT　FOUND！");
    }
    return r_id;


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

//    @Override
//    public int getItemCount() {
//        return Integer.MAX_VALUE;
//    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv1;
        ImageView pic;
        public ViewHolder(View view){
            super(view);
            tv = (TextView) view.findViewById(R.id.shili1);
            tv1 = (TextView) view.findViewById(R.id.name1);
            pic = (ImageView) view.findViewById(R.id.personPic);
        }
    }




}
