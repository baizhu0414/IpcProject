package com.example.module_matisse;

import android.content.Context;
import android.graphics.Point;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.HashSet;
import java.util.Set;

class GifSizeFilter extends Filter {

    private int mMinWidth;
    private int mMinHeight;
    private static final int MAX_PHOTO_SIZE = 10 * K * K;
    private static final int MAX_VIDEO_DURATION = 60 * 1000; // 60m左右的图片

    GifSizeFilter(int minWidth, int minHeight) {
        mMinWidth = minWidth;
        mMinHeight = minHeight;
    }

    @Override
    public Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {{
            add(MimeType.GIF);
        }};
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        Point size = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
        if (size.x < mMinWidth || size.y < mMinHeight || item.size > MAX_PHOTO_SIZE) {
            return new IncapableCause(IncapableCause.DIALOG,
                    "长宽不小于：" + mMinWidth + "大小不大于：" + PhotoMetadataUtils.getSizeInMB(MAX_PHOTO_SIZE));
        }
        if (MimeType.isVideo(item.mimeType) && item.duration > MAX_VIDEO_DURATION) {
            return new IncapableCause(IncapableCause.DIALOG, "视频时长大于60秒");
        }
        return null;
    }

}

