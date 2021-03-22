package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.Nullable;

public class View_Velocity extends View {

    public static final String TAG = "View_Velocity";
    private VelocityTracker velocityTracker = null;

    public View_Velocity(Context context) {
        super(context);
    }

    public View_Velocity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View_Velocity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //获得VelocityTracker类的对象
                velocityTracker = VelocityTracker.obtain();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //注册事件
                velocityTracker.addMovement(event);
                //设置单位时间
                velocityTracker.computeCurrentVelocity(1000);
                //水平速度
                float xVelocity = velocityTracker.getXVelocity();
                //竖直速度
                float yVelocity = velocityTracker.getYVelocity();
                Log.i(TAG, "xVelocity = " + xVelocity + " yVelocity = " + yVelocity);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:{
                //释放VelocityTracker对象
                releaseVelocityTracker();
                break;
            }
        }
        return true;
    }

    private void releaseVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.clear();
            velocityTracker.recycle();
        }
    }
}
