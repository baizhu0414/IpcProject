package com.example.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 在RecyclerView条目中添加分割线。
 * 参考：http://www.jishudog.com/9055/html
 * */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {

    int orientation;
    Drawable drawableDivider;

    public SimpleItemDecoration(Context context, int orientation) {
        this.orientation = orientation;
        int[] attrs = new int[]{android.R.attr.listDivider};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        drawableDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    /**画线*/
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == RecyclerView.HORIZONTAL) {
            drawVertical(c, parent, state);
        } else if (orientation == RecyclerView.VERTICAL) {
            drawHorizontal(c, parent, state);
        }
    }

    /**设置条目偏移量*/
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == RecyclerView.HORIZONTAL) {
            outRect.set(0, 0, drawableDivider.getIntrinsicWidth(), 0);
        } else if (orientation == RecyclerView.VERTICAL) {
            outRect.set(0, 0, 0, drawableDivider.getIntrinsicHeight());
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i=0; i<childCount; i++) {
            View item = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)item.getLayoutParams();
            int dividerLeft = item.getWidth() - drawableDivider.getIntrinsicWidth();
            int dividerTop = item.getTop() - layoutParams.topMargin;
            int dividerRight = dividerLeft + drawableDivider.getIntrinsicWidth();
            int dividerBottom = item.getBottom() + layoutParams.bottomMargin;
            drawableDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            drawableDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i=0; i<childCount; i++) {
            View item = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) item.getLayoutParams();
            int left = item.getLeft() + 60;
            int top = item.getBottom() - layoutParams.bottomMargin;
            int right = item.getRight();
            int bottom = top + drawableDivider.getIntrinsicHeight();
            drawableDivider.setBounds(left, top, right, bottom);
            drawableDivider.draw(c);
        }
    }
}
