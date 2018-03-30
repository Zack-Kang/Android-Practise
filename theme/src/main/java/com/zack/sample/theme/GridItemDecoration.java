package com.zack.sample.theme;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author: zhoukang
 * Description:暂时只支持GridLayoutManager类型样式
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mDividerTop;
    private int mDividerBottom;
    private int mVerDividerSize;
    private int mHorDividerSize;

    public GridItemDecoration() {
        this(0);
    }

    public GridItemDecoration(int dividerSize) {
       this(dividerSize,dividerSize,0,0);
    }

    public GridItemDecoration(int horDividerSize,int verDividerSize, int dividerTop, int dividerBotton) {
        setDividerTop(dividerTop);
        setDividerBottom(dividerBotton);
        setVerDividerSize(verDividerSize);
        setHorDividerSize(horDividerSize);
    }


    public void setVerDividerSize(int verDividerSize) {
        this.mVerDividerSize = verDividerSize;
    }

    public void setHorDividerSize(int horDividerSize) {
        this.mHorDividerSize = horDividerSize;
    }

    public void setDividerTop(int dividerTop) {
        this.mDividerTop = dividerTop;
    }

    public void setDividerBottom(int dividerBottom) {
        this.mDividerBottom = dividerBottom;
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获得条目的偏移量（所有item都会调用一次）
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (position<spanCount){//第一行
            //只有一行
            if (isLastRaw(position,spanCount,childCount)){
                //最后一列
                if (isLastColum(position,spanCount,childCount)){
                    outRect.set(0,mDividerTop,0,mDividerBottom);
                } else {
                    outRect.set(0,mDividerTop,mVerDividerSize,mDividerBottom);
                }
            } else {
                //最后一列
                if (isLastColum(position,spanCount,childCount)){
                    outRect.set(0,mDividerTop,0,mHorDividerSize);
                } else {
                    outRect.set(0,mDividerTop,mVerDividerSize,mHorDividerSize);
                }
            }

        } else if (isLastRaw(parent.getChildAdapterPosition(view),spanCount,childCount)) {
            //最后一列
            if (isLastColum(position,spanCount,childCount)){
                outRect.set(0,0,0,mHorDividerSize);
            } else {
                outRect.set(0,0,mVerDividerSize,mHorDividerSize);
            }
        } else {
            //非第一行和最后一行
            if (isLastColum(position,spanCount,childCount)){
                outRect.set(0,0,0,mHorDividerSize);
            } else {
                outRect.set(0,0,mVerDividerSize,mHorDividerSize);
            }
        }
    }

    private int getSpanCount(RecyclerView parent)
    {
        // 列数
        int spanCount;
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        return spanCount;
    }

    /**
     * 判断是否是最后一列
     */
    private boolean isLastColum(int position, int spanCount,int childCount)
    {
        return ((position + 1) % spanCount == 0);
    }

    /**
     * 判断是否是最后一列
     */
    private boolean isLastRaw(int position, int spanCount,int childCount)
    {
        childCount = childCount - childCount % spanCount;
        return position >= childCount;
    }

    /**
     * 判断是否是第一列
     */
    private boolean isFirstRaw(int position, int spanCount,int childCount)
    {
        childCount = childCount - childCount % spanCount;
        return position >= childCount;
    }
}
