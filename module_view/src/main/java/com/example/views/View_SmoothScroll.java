package com.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class View_SmoothScroll extends LinearLayout {

    Scroller mScroller ;
    int startX;
    int startY;

    public View_SmoothScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public View_SmoothScroll(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    public void smoothScroll(int dx,int dy,int duration){
        //获取滑动起点坐标
        startX = getScrollX();
        startY = getScrollY();
        //设置滑动参数
        mScroller.startScroll(startX,startY,dx,dy,duration);
        //重新绘制View
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //递归终止条件:滑动结束
        if(!mScroller.computeScrollOffset()){
            return;
        }else{
            //mScroller.getCurrX(),mScroller.getCurrY()记录的是此刻要滑动达到的目标坐标
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
        }
        //递归调用
        invalidate();//或者postInvalidate()
    }
}
