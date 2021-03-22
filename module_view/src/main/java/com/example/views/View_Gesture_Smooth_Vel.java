package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 简单实现的一个滚动，当然还有很多bug，但是我也不想完善了。了解原理即可。
 * */
public class View_Gesture_Smooth_Vel extends LinearLayout implements GestureDetector.OnGestureListener {

    public static final String TAG = "View_Gesture_Smooth_Vel";
    // 滑动速度监听
    VelocityTracker mVelocityTracker;
    // 手势监听
    GestureDetector mGestureDetector;
    // 弹性滑动
    Scroller mScroller;

    public View_Gesture_Smooth_Vel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mGestureDetector = new GestureDetector(context, this);
    }

    public View_Gesture_Smooth_Vel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mGestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        mVelocityTracker.computeCurrentVelocity(500);
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        Log.w(TAG, "onScroll, vY:" + mVelocityTracker.getYVelocity());
        smoothScrollTo(0, (int)mVelocityTracker.getYVelocity());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    private void smoothScrollTo(int dX, int dY) {
        int startX = getScrollX();
        int startY = getScrollY();
        mScroller.startScroll(startX, startY, dX, -dY, 1000);
//        Log.w(TAG, "smoothScrollTo dY:" + dY);
        // 重新绘制UI
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            // 未到达目的地则继续滑动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
