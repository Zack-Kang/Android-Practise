package com.zack.uicomponent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class HorizontalScrollViewEx extends ViewGroup {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childrenWidth = 0;
        int maxChildHeight = 0;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for (int i=0;i<childCount;i++){
            childrenWidth += getChildAt(i).getMeasuredWidth();
            maxChildHeight = Math.max(maxChildHeight, getChildAt(i).getMeasuredHeight());
        }
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (withMode){
            case MeasureSpec.AT_MOST:
                withSize = childrenWidth;
                break;
        }
        switch (heightMode){
            case MeasureSpec.AT_MOST:
                heightSize = maxChildHeight;
        }
        setMeasuredDimension(withSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int childLeft = left;
        for (int i=0;i<childCount;i++){
            View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }
            child.layout(childLeft, top,childLeft + child.getMeasuredWidth(),top + child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth();
        }
    }

    int lastX;
    int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("onTouchEvent", event.getAction()+"  ==  " + event.getRawX());
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                scrollBy( -(x - lastX),0);
                break;
        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(event);
    }

    int mLastInterceptedX = 0;
    int mLastInterceptedY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean intercepted = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x-mLastInterceptedX)>Math.abs(y-mLastInterceptedY)){
                    intercepted = true;
                }

        }
        Log.i("onInterceptTouchEvent", ev.getAction()+"  ==  X " + Math.abs(x-mLastInterceptedX) + ", Y = "+ Math.abs(y-mLastInterceptedY) +" " + intercepted);
        mLastInterceptedY = x;
        mLastInterceptedX = y;
        lastX = x;
        lastY = y;
        return intercepted;
    }
}
