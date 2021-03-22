package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class View_Gesture extends View {

    public static final String TAG = "View_Gesture";
    GestureDetector gestureDetector;

    public View_Gesture(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public View_Gesture(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public View_Gesture(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    class GestureListener implements GestureDetector.OnGestureListener {
        /**
         * ACTION_DOWN触发。
         * */
        @Override
        public boolean onDown(MotionEvent e) {
            Log.w(TAG, "MotionEvent.ACTION_DOWN:" + "onDown");
            return true;
        }

        /**
         * ACTION_DOWN，未松开时。
         * */
        @Override
        public void onShowPress(MotionEvent e) {

        }

        /**
         * {@link MotionEvent#ACTION_UP}触发。
         * */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.w(TAG, "MotionEvent.ACTION_UP" + "onSingleTapUp");
            return true;
        }

        /**
         * 手指拖动，ACTION_DOWN+ACTION_MOVE*n
         * */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.w(TAG, "拖动:onScroll");
            return true;
        }

        /**
         * 长按动作。
         * */
        @Override
        public void onLongPress(MotionEvent e) {
            Log.w(TAG, "长按:onLongPress");
        }

        /**
         * 滑动一下。
         * */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.w(TAG, "快速滑动一下:onFling");
            return true;
        }
    }
}
