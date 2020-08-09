package com.zack.uicomponent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = "HorizontalScrollViewEx";
    public HorizontalScrollViewEx(Context context) {
        super(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    static class LayoutParams extends MarginLayoutParams{

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //先计算子View
        int childCount = getChildCount();
        int childWidth = 0;
        int maxChildHeight = 0;
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE){
                continue;
            }
            //计算子View大小

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidthMeasureSpec = measChild(params.width, widthMode, widthSize);
            int childHeightMeasureSpec = measChild(params.height, heightMode, heightSize);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            childWidth += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            maxChildHeight = Math.max(child.getMeasuredHeight() + params.bottomMargin + params.topMargin + getPaddingBottom() + getPaddingTop(), maxChildHeight);
        }

        switch (widthMode){
            case MeasureSpec.EXACTLY:
                widthSize = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                widthSize = Math.min(childWidth + getPaddingLeft() + getPaddingRight(), widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                widthSize = widthSize<=0?childWidth + getPaddingLeft() + getPaddingRight():Math.min(widthSize,childWidth + getPaddingLeft() + getPaddingRight());

        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                heightSize = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                heightSize = Math.min(heightSize, maxChildHeight);
                break;
            case MeasureSpec.UNSPECIFIED:
                heightSize = heightSize<=0?maxChildHeight:Math.min(heightSize,maxChildHeight);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    private int measChild(int childDimension, int parentMode, int parentSize) {
        int mode = 0;
        int size = 0;
        switch (parentMode){
            case MeasureSpec.EXACTLY:
                if (childDimension>0){
                    mode = MeasureSpec.EXACTLY;
                    size = childDimension;
                } else if (childDimension == LayoutParams.MATCH_PARENT){
                    mode = MeasureSpec.EXACTLY;
                    size = parentSize;
                } else if (childDimension == LayoutParams.WRAP_CONTENT){
                    mode = MeasureSpec.AT_MOST;
                    size = parentSize;
                }
                break; 
            case MeasureSpec.AT_MOST:
                if (childDimension>0){
                    mode = MeasureSpec.EXACTLY;
                    size = childDimension;
                } else if (childDimension == LayoutParams.MATCH_PARENT){
                    mode = MeasureSpec.AT_MOST;
                    size = parentSize;
                } else if (childDimension == LayoutParams.WRAP_CONTENT){
                    mode = MeasureSpec.AT_MOST;
                    size = parentSize;
                } 
                break;
            case MeasureSpec.UNSPECIFIED:
                if (childDimension>0){
                    mode = MeasureSpec.EXACTLY;
                    size = childDimension;
                } else if (childDimension == LayoutParams.MATCH_PARENT){
                    mode = MeasureSpec.UNSPECIFIED;
                    size = parentSize;
                } else if (childDimension == LayoutParams.WRAP_CONTENT){
                    mode = MeasureSpec.UNSPECIFIED;
                    size = parentSize;
                }    
                break;
        }
        return MeasureSpec.makeMeasureSpec(size, mode);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int childLeft = left + getPaddingLeft();
        for (int i=0;i<childCount;i++){
            View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            child.layout(childLeft + params.leftMargin, top + params.topMargin,childLeft  + params.leftMargin + child.getMeasuredWidth(),top + params.topMargin + child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth() + params.leftMargin + params.leftMargin;
        }
    }


    int lastX;
    int lastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int scrollX = getScrollX();
        Log.i(TAG, "onTouchEvent - >> action = " + event.getAction() + ", x = " + x + "， y = " + y);
        Log.i(TAG, "onTouchEvent - >> action = " + event.getAction() + ", RawX = " + event.getRawX() + "， RawY = " + event.getRawY());
        Log.i(TAG, "onTouchEvent - >> action = " + event.getAction() + ", ScrollX = " + getScrollX() + "， ScrollY = " + getScrollY());
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            int diffX = x - lastX;
            int diffY = y - lastY;
            Log.i(TAG, "onTouchEvent - >> action = " + event.getAction() + ", diffX = " + diffX + "， diffY = " + diffY + ", width = " + getWidth());
            if (Math.abs(diffX) > Math.abs(diffY) &&
                    ((diffX > 0 && scrollX > 0) ||
                    (diffX < 0 && scrollX < getWidth()))){
                scrollBy(-diffX, getScrollY());
                lastX = x;
                lastY = y;
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    int mLastInterceptedX = 0;
    int mLastInterceptedY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i(TAG, "onTouchEvent - >> action = " + event.getAction() + ", x = " + x + "， y = " + y);
        boolean intercepted = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - mLastInterceptedX) > Math.abs(y - mLastInterceptedY)){
                    lastY = y;
                    lastX = x;
                    intercepted = true;
                }
        }
        mLastInterceptedX = x;
        mLastInterceptedY = y;
        Log.i(TAG, "onInterceptTouchEvent - >> action = " + event.getAction() + ", diffx = " + Math.abs(x-mLastInterceptedX) + ", diffy = "+ Math.abs(y-mLastInterceptedY) + ", intercepted = " + intercepted);
        return intercepted;
    }
}
