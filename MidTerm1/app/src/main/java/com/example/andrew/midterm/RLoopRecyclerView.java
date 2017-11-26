package com.example.andrew.midterm;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 2017/11/21.
 */

public class RLoopRecyclerView extends RecyclerView {
    private static final String TAG = "aaa";

    public RLoopRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RLoopRecyclerView(Context context) {
        super(context);
    }

    public RLoopRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    @Override
    public LoopAdapter getAdapter() {
        return (LoopAdapter) super.getAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof LoopAdapter)) {
            throw new IllegalArgumentException("adapter must  instanceof LoopAdapter!");
        }
        super.setAdapter(adapter);
        //scrollToPosition(getAdapter().getItemRawCount() * 10000);//开始时的偏移量
    }

    private void initView() {
        new RPagerSnapHelper().setOnPageListener(new RPagerSnapHelper.OnPageListener() {
            @Override
            public void onPageSelector(int position) {
                Log.e(TAG, "onPageSelector: " + position % getAdapter().getItemRawCount());
            }
        }).attachToRecyclerView(this);
    }

    public static abstract class LoopAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

        public ArrayList<personInfo> datas = new ArrayList<personInfo>();

        public void setDatas(ArrayList<personInfo> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        /**
         * 真实数据的大小
         */
        public int getItemRawCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        final public int getItemViewType(int position) {
            return getLoopItemViewType(position % getItemRawCount());
        }

        protected int getLoopItemViewType(int position) {
            return 0;
        }

        @Override
        final public void onBindViewHolder(T holder, int position) {
            onBindLoopViewHolder(holder, position % getItemRawCount());
        }

        public abstract void onBindLoopViewHolder(T holder, int position);

        @Override
        final public int getItemCount() {
            int rawCount = getItemRawCount();
            if (rawCount > 0) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }
    }
}

