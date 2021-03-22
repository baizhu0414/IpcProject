package com.example.ipcproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 此模块作为主要提供IPC章节的《Android开发艺术探索》。
 * 21/3/18：暂时用不到此处，跳过，后期补上。
 * */
@Route(path = "/test/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.app_btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/home/main")
                        .withInt("key", 24)
                        .navigation();
            }
        });
    }
}