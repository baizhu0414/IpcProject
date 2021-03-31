package com.example.module_view;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 1.测试三种滚动方式scrollTo,scrollBy和LayoutParams。
 * 2.动画。
 * */
public class ViewScrollActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edt_scroll;
    Button btn_scroll;
    TextView tv_scroll_to_by;
    TextView tv_anim;
    Button btn_layparams;

    public static final String TAG = "ViewScrollActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scroll);

        initView();
    }

    public void initView() {
        edt_scroll = findViewById(R.id.edt_scroll);
        btn_scroll = findViewById(R.id.btn_scroll);
        tv_scroll_to_by = findViewById(R.id.tv_scrolltoby);
        tv_anim = findViewById(R.id.tv_anim);
        btn_layparams = findViewById(R.id.btn_layparams);

        btn_scroll.setOnClickListener(this);
        tv_scroll_to_by.setOnClickListener(this);
        tv_anim.setOnClickListener(this);
        btn_layparams.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_scroll) {
            // View的内容滑动，右下为负。
            Editable scrollText = edt_scroll.getText();
            if (scrollText != null && !"".equals(scrollText.toString())) {
                int dis = Integer.parseInt(scrollText.toString());
                Log.d(TAG, "滑动距离-" + dis);
                tv_scroll_to_by.scrollBy(-dis, -dis);
            }

        } else if (v.getId() == R.id.tv_scrolltoby) {
            // xml实现动画效果
            Animation trans_animation = AnimationUtils.loadAnimation(ViewScrollActivity.this, R.anim.trans_anim);
            tv_scroll_to_by.startAnimation(trans_animation);
            Log.d(TAG, "Animation移动");
        } else if (v.getId() == R.id.tv_anim) {
            // java实现动画效果
            ObjectAnimator.ofFloat(tv_anim, "translationX", 0, 100).setDuration(200).start();
        } else if (v.getId() == R.id.btn_layparams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)btn_layparams.getLayoutParams();
            layoutParams.width += 100;
            layoutParams.leftMargin += 100;
            btn_layparams.requestLayout();
        } else {
            Log.d(TAG, "...啥也没发生");
        }
    }
}