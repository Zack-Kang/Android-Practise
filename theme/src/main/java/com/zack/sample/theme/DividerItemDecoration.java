package com.zack.sample.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Author: zhoukang
 * Time: 2017/9/26.
 * Description:
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private Drawable divider;
    private int[] attrs = new int[]{
            android.R.attr.listDivider
    };

    public DividerItemDecoration(Context context, int orientation) {
        super();
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        divider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);
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
        //水平线
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - left;
        int childeCount = parent.getChildCount();
        for (int i=0;i<childeCount;i++){
            View view = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int top = (int) (view.getBottom()+params.bottomMargin+ ViewCompat.getTranslationY(view));
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom );
            divider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        //水平线
        int top = parent.getPaddingLeft();
        int bottom = parent.getWidth() - top;
        int childeCount = parent.getChildCount();
        for (int i=0;i<childeCount;i++){
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = (int) (view.getWidth()+params.rightMargin+ ViewCompat.getTranslationX(view));
            int right = top + divider.getIntrinsicWidth();
            divider.setBounds(left,top,right,bottom );
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获得条目的偏移量（所有item都会调用一次）
        if (mOrientation ==  LinearLayout.HORIZONTAL){
            outRect.set(0,0,0,divider.getIntrinsicHeight());
        } else if (mOrientation == LinearLayout.VERTICAL){
            //int left, int top, int right, int bottom
            outRect.set(0,0,divider.getIntrinsicWidth(),0);
        }

    }
}
