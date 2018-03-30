package com.zack.sample.theme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author: zhoukang
 * Description:暂时只支持LinearLayoutManager类型样式
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private int mDividerSize;
    private int mDividerHead;
    private int mDividerTail;
    private ColorDrawable mDivider;

    public DividerItemDecoration(Context context, int colorId) {
        this(context,colorId,LinearLayout.HORIZONTAL);
    }

    public DividerItemDecoration(Context context, int colorId, int orientation) {
        this(context,colorId, orientation,0);
        setOrientation(orientation);
    }

    public DividerItemDecoration(Context context, int colorId , int orientation, int dividerSize) {
       this(context,colorId,orientation,dividerSize,0,dividerSize);
    }

    public DividerItemDecoration(Context context, int colorId, int orientation, int dividerSize, int dividerHead, int dividerTail) {
        setOrientation(orientation);
        setDividerSize(dividerSize);
        setDividerHead(dividerHead);
        setDividerTail(dividerTail);
        mDivider = new ColorDrawable(context.getResources().getColor(colorId));
    }

    public void setDividerSize(int dividerSize) {
        this.mDividerSize = dividerSize;
    }

    public void setDividerHead(int dividerHead) {
        this.mDividerHead = dividerHead;
    }

    public void setDividerTail(int dividerTail) {
        this.mDividerTail = dividerTail;
    }

    public void setOrientation(int orientation){
        if (orientation !=  LinearLayout.HORIZONTAL && orientation != LinearLayout.VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //RecyclerView回调该回执方法，需要自己去绘制条目的间隔线
        if (mOrientation ==  LinearLayout.HORIZONTAL){
            drawHorizontal(c, parent);
        } else if (mOrientation == LinearLayout.VERTICAL){
            //int left, int top, int right, int bottom
            drawVertical(c, parent);
        }
        super.onDraw(c, parent, state);
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - left;
        int childeCount = parent.getChildCount();
        for (int i=0;i<childeCount;i++){
            View view = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            //第一个卡片添加头
            if (i==0){
                int bottom = view.getTop()-params.topMargin;
                int top = bottom - mDividerHead;
                mDivider.setBounds(left,top,right,bottom );
                mDivider.draw(c);
            }
            int top = (int) (view.getBottom()+params.bottomMargin+ ViewCompat.getTranslationY(view));
            int bottom;
            if (i==childeCount -1){
                bottom = top + mDividerTail;
            }else{
                bottom = top + mDividerSize;
            }
            mDivider.setBounds(left,top,right,bottom );
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int top = parent.getPaddingLeft();
        int bottom = parent.getWidth() - top;
        int childeCount = parent.getChildCount();
        for (int i=0;i<childeCount;i++){
            View view = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            //第一个卡片添加头
            if (i==0){
                int right = view.getLeft()-params.leftMargin;
                int left = right - mDividerHead;
                mDivider.setBounds(left,top,right,bottom );
                mDivider.draw(c);
            }
            int left = (int) (view.getRight()+params.leftMargin+ ViewCompat.getTranslationY(view));
            int right;
            if (i==childeCount -1){
                right = left + mDividerTail;
            }else{
                right = left + mDividerSize;
            }
            mDivider.setBounds(left,top,right,bottom );
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获得条目的偏移量（所有item都会调用一次）
        if (mOrientation ==  LinearLayout.HORIZONTAL){
            if (parent.getChildAdapterPosition(view)==0){
                outRect.set(mDividerHead,0,mDividerSize,0);
            }else if (parent.getChildAdapterPosition(view)==parent.getAdapter().getItemCount()-1){
                outRect.set(0,0,mDividerTail,0);
            } else {
                outRect.set(0,0,mDividerSize,0);
            }
        } else if (mOrientation == LinearLayout.VERTICAL){
            //int left, int top, int right, int bottom
            if (parent.getChildAdapterPosition(view)==0){
                outRect.set(0,mDividerHead,0,mDividerSize);
            }else if (parent.getChildAdapterPosition(view)==parent.getAdapter().getItemCount()-1){
                outRect.set(0,0,0,mDividerTail);
            } else {
                outRect.set(0,0,0,mDividerSize);
            }
        }

    }
}
