package com.yuyh.easyadapter;

import android.content.Context;
import android.widget.ImageView;

/**
 * 若需调用setImageUrl加载网络图片，则注册全局ImageLoader回调
 * <p>
 * Created by yuyuhang on 2018/6/14.
 */
public class AdapterImageLoader {

    public static ImageLoader sImageLoader;

    public static void init(ImageLoader loader) {
        sImageLoader = loader;
    }

    public interface ImageLoader {

        void loadImage(Context context, String url, ImageView view);
    }
}
