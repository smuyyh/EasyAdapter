package com.yuyh.easyadapter;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by yuyuhang on 2018/6/14.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 若需要调用adapter.setImageUrl，可以配置全局统一回调，也可Adapter单独重写getImageLoader方法
        AdapterImageLoader.init(new AdapterImageLoader.ImageLoader() {
            @Override
            public void loadImage(Context context, String url, ImageView view) {
                Glide.with(context)
                        .load(url)
                        .into(view);
            }
        });
    }
}
