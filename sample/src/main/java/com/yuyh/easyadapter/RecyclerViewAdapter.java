package com.yuyh.easyadapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/21.
 */
public class RecyclerViewAdapter extends EasyRVAdapter<Bean> {
    public RecyclerViewAdapter(Context context, List<Bean> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, final int position, Bean item) {
        viewHolder.setText(R.id.tv, item.name);
        // 自定义点击可覆盖父类的setOnItemClickListener
        /*viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, position+"---", Toast.LENGTH_SHORT).show();
            }
        });*/
        viewHolder.setImageUrl(R.id.iv, "https://img.25pp.com//ppnews/zixun_img/6fc/096/1444271700253231.jpg");
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        if (position % 2 == 0)
            return 0;
        else return 1;
    }

    @Override
    public AdapterImageLoader.ImageLoader getImageLoader() {
        // 重写该方法，以替换全局初始化的ImageLoader
        return new AdapterImageLoader.ImageLoader() {
            @Override
            public void loadImage(Context context, String url, ImageView view) {
                Glide.with(context)
                        .load(url)
                        .override(10, 10)
                        .into(view);
            }
        };
    }
}
