package com.example.module_view;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.views.View_SmoothScroll;

/**
 * 测试VelocityTracker，GestureDetector和Scroller。
 * */
public class ViewActivity extends AppCompatActivity {

    public static String TAG = "ViewActivity";
    // 手势监听，接管onTouchEvent
    GestureDetector gestureDetector;
    View view_ges;
    View view_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initView();
        initData();
    }

    private void initView() {
        view_ges = findViewById(R.id.view_ges);
        view_scroll = findViewById(R.id.view_scroll);
    }

    public void initData() {
        view_ges.setClickable(true);
        view_ges.setFocusable(true);
        view_ges.setLongClickable(true);

//        view_scroll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((View_SmoothScroll)view_scroll).smoothScroll(0, 100, 1000);
//            }
//        });
    }

}