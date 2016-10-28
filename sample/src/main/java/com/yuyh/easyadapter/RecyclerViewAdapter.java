package com.yuyh.easyadapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.yuyh.easyadapter.recyclerview.BaseRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/21.
 */
public class RecyclerViewAdapter extends BaseRVAdapter<Bean> {
    public RecyclerViewAdapter(Context context, List<Bean> list, int... layoutIds) {
        super(context, list, layoutIds);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, final int position, Bean item) {
        //如果不想暴露 item 的点击事件就直接继承 EasyRVAdapter 即可
        super.onBindData(viewHolder, position, item);//调用父类来暴露item的点击事件
        viewHolder.setText(R.id.tv, item.name);
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, position+"---", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getLayoutIndex(int position, Bean item) {
        if (position % 2 == 0)
            return 0;
        else return 1;
    }
}
