package com.example.views.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SimpleScroll extends LinearLayout {

    private static final String TAG = "SimpleScroll";
    // 记录最后一次点击位置
    int mLastX = 0;
    int mLastY = 0;

    public SimpleScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleScroll(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击位置距离屏幕距离
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d(TAG, "move, delta X:" + deltaX + "  delta Y:" + deltaY);
                int translationX = (int) getTranslationX() + deltaX;
                int translationY = (int) getTranslationY() + deltaY;
                setTranslationX(translationX);
                setTranslationY(translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
