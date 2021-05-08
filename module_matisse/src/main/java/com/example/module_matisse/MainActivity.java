package com.example.module_matisse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.List;

/**
 * date: 2020-5-7
 * note：目前Matisse没有在维护了，0.5.3-beta版本虽然失陪了Android10，但是并不稳定（下方疯狂报error）。
 * 谷_歌老抽风了。
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_STORAGE = 111;
    private static final int REQUEST_CODE_CHOOSE = 112;
    private SelectionSpec spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasCameraPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (hasCameraPermission != PackageManager.PERMISSION_GRANTED
                || hasWriteExternalPermission != PackageManager.PERMISSION_GRANTED) {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_STORAGE);
        }

        findViewById(R.id.btn_matisse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示图片和视频
                Matisse.from(MainActivity.this)
                        .choose(MimeType.ofAll(),true)
                        .countable(false)
                        .addFilter(new GifSizeFilter(320, 320))
                        .originalEnable(false)
                        .imageEngine(new PicassoEngine())
                        .setOnSelectedListener(new OnSelectedListener() {
                            @Override
                            public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                if (pathList.size() >= 1) {
                                    Toast.makeText(MainActivity.this, pathList.get(0), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .showPreview(false)
                        .forResult(REQUEST_CODE_CHOOSE);
                // 只显示视频
//                Matisse.from(MainActivity.this)
//                        .choose(MimeType.ofVideo(),true)
//                        .theme(R.style.Matisse_Dracula)
//                        .countable(false)
//                        .addFilter(new GifSizeFilter(320, 320))
//                        .originalEnable(false)
//                        .showSingleMediaType(true)
//                        .imageEngine(new PicassoEngine())
//                        .forResult(REQUEST_CODE_CHOOSE);
                // 只显示图片
//                Matisse.from(MainActivity.this)
//                        .choose(MimeType.ofImage(),true)
//                        .theme(R.style.Matisse_Dracula)
//                        .countable(false)
//                        .addFilter(new GifSizeFilter(320, 320))
//                        .originalEnable(false)
//                        .showSingleMediaType(true)
//                        .imageEngine(new PicassoEngine())
//                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected  = Matisse.obtainResult(data);
//            Toast.makeText(this, "MainActivity" + mSelected.get(0), Toast.LENGTH_SHORT).show();
        }
    }
}