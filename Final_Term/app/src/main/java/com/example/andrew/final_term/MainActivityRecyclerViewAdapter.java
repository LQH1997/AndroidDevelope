package com.example.andrew.final_term;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Andrew on 2017/12/22.
 */

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{

    private ArrayList<Info> myData;

    String IMG_URL= "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514999167116&di=1f73131fa58abaf313d31d585a892186&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2F7a899e510fb30f2457bf0965ca95d143ad4b03aa.jpg";

    public MainActivityRecyclerViewAdapter(ArrayList<Info> a) {
        myData = a;
    }

    public void setData(ArrayList<Info> a) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linear_tab, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.tv.setText(myData.get(position).getTitle());
        viewHolder.tv1.setText(myData.get(position).getContext());
        viewHolder.tv2.setText(myData.get(position).getTime());
        //viewHolder.iv.setBackground();
        viewHolder.iv.setTag("UNCLICKED");

        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String viewTag = viewHolder.iv.getTag().toString();
                if(viewTag.equals("UNCLICKED")) {
                    viewHolder.iv.setTag("CLICKED");
                    viewHolder.iv.setImageBitmap(downloadBitmap(viewHolder.iv));

                } else {
                    //Do nothing
                }


            }
        });
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
        ImageButton iv;
        public ViewHolder(View view){
            super(view);
            tv = (TextView) view.findViewById(R.id.title);
            tv1 = (TextView) view.findViewById(R.id.context);
            tv2 = (TextView) view.findViewById(R.id.time);
            iv = (ImageButton) view.findViewById(R.id.previewImage);
        }
    }

    public Bitmap downloadBitmap(ImageButton imageView) {
        Bitmap bitmap = getBitmapFromUrl(IMG_URL);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();

        return bitmap;
    };

    private Bitmap getBitmapFromUrl(String imgUrl) {
        URL url;
        Bitmap bitmap = null;

        try {
            url = new URL(IMG_URL);
            InputStream is = url.openConnection().getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }



}
