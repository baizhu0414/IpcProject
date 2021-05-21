package com.example.module_share;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_share_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 聊天
//                Intent intent = new Intent();
//                ComponentName comp = new ComponentName("com.tencent.mm",
//                        "com.tencent.mm.ui.tools.ShareImgUI");
//                intent.setComponent(comp);
//                intent.setAction("android.intent.action.SEND");
//                intent.setType("text/*"); intent.putExtra(Intent.EXTRA_TEXT,"我是文字");
//                startActivity(intent);

                // 朋友圈
//                Intent intent = new Intent();
//                ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//                intent.setComponent(comp);
//                intent.setAction("android.intent.action.SEND");
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_TEXT, "我是文字");
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//                startActivity(intent);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"一段文字");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"选择分享应用"));
            }
        });

        findViewById(R.id.btn_share_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("file:///sdcard/Pictures/次元壁纸/1620979706663.png");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent, "ChooseImageShare"));
            }
        });

        findViewById(R.id.btn_share_image_restricted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImageWechat("/sdcard/Pictures/dmzj-1611464191221.jpg");
            }
        });

        String[] pers = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (checkPermissionAllGranted(pers)) {
            ActivityCompat.requestPermissions(this, pers, 1000);
        }
        initPhotoError();
    }

    private void shareImageWechat(String path) {
        // 初始化Intent
        Uri uri = Uri.parse("file://" + path);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 获取可用应用信息
        PackageManager  pm = getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(intent,0);
        if(resInfo.isEmpty()){
            Toast.makeText(this, "没有可以分享的应用", Toast.LENGTH_SHORT).show();
            return;
        }
        // 筛选微信QQ
        List<Intent> targetIntents = getTargetIntents(resInfo, pm, uri);

        // 最后调用createChooser方法，显示分享dialog
        Intent chooser = Intent.createChooser(targetIntents.remove(targetIntents.size() - 1), "选择分享");
        if (chooser == null) return;
        LabeledIntent[] labeledIntents = targetIntents.toArray(new LabeledIntent[targetIntents.size()]);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,labeledIntents);
        startActivity(chooser);
    }

    private List<Intent> getTargetIntents(List<ResolveInfo> resInfo, PackageManager pm, Uri uri) {
        List<Intent> targetIntents = new ArrayList<>();
        for (ResolveInfo resolveInfo : resInfo) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo.packageName.contains("com.tencent.mm")
                    || activityInfo.packageName.contains("com.tencent.mobileqq")
                    || activityInfo.packageName.contains("com.tencent.tim")){
                //过滤掉qq收藏
                if (resolveInfo.loadLabel(pm).toString().contains("QQ收藏")
                        || resolveInfo.loadLabel(pm).toString().contains("微信收藏")){
                    continue;
                }
                Intent target = new Intent();
                target.setAction(Intent.ACTION_SEND);
                target.setComponent(new ComponentName(activityInfo.packageName,activityInfo.name));
                target.putExtra(Intent.EXTRA_STREAM, uri);
                target.setType("image/*");//必须设置，否则选定分享类型后不能跳转界面
                targetIntents.add(new LabeledIntent(target,activityInfo.packageName,resolveInfo.loadLabel(pm),resolveInfo.icon));
            }
        }
        if (targetIntents.size()<= 0){
            Toast.makeText(this, "没有可以分享的应用", Toast.LENGTH_SHORT).show();
            return null;
        }
        return targetIntents;
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 调用显示路径
     * */
    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

}