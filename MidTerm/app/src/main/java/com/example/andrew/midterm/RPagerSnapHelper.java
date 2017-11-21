package com.example.andrew.midterm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andrew on 2017/11/21.
 */

public class RPagerSnapHelper extends PagerSnapHelper {

    OnPageListener mOnPageListener;
    int mCurrentPosition = 0;

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public RPagerSnapHelper setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
        return this;
    }

    public OnPageListener getOnPageListener() {
        return mOnPageListener;
    }

    /**
     * 页面选择回调监听
     */
    public RPagerSnapHelper setOnPageListener(OnPageListener onPageListener) {
        mOnPageListener = onPageListener;
        return this;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        //开始滚动
                    } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //结束滚动
                    } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        //滑行中
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) targetView.getLayoutParams();
        int position = params.getViewAdapterPosition();
        int left = targetView.getLeft();
        int right = targetView.getRight();
        ViewGroup viewGroup = (ViewGroup) targetView.getParent();

        int[] out = new int[]{0, 0};
        boolean isLastItem = position == layoutManager.getItemCount() - 1/*最后一个*/ && right == viewGroup.getMeasuredWidth();

//        if (left == 0 || isLastItem) {
//            return out;
//        }

        out[0] = left;
        out[1] = 0;
        if (mOnPageListener != null && mCurrentPosition != position && (out[0] == 0 || isLastItem)) {
            mOnPageListener.onPageSelector(mCurrentPosition = position);
        }
        return out;
    }

    public interface OnPageListener {
        void onPageSelector(int position);
    }
}

