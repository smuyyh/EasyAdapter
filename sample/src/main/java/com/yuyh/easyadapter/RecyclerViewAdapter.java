package com.yuyh.easyadapter;

import android.content.Context;

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
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        if (position % 2 == 0)
            return 0;
        else return 1;
    }
}
