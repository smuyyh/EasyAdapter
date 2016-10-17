package com.yuyh.easyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yuyh.easyadapter.recyclerview.BaseRVAdapter;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerViewAdapter mAdapter;
    private List<Bean> list = Arrays.asList(new Bean(R.mipmap.ic_launcher, "测试1"),
            new Bean(R.mipmap.ic_launcher, "测试2"),
            new Bean(R.mipmap.ic_launcher, "测试3"),
            new Bean(R.mipmap.ic_launcher, "测试4"),
            new Bean(R.mipmap.ic_launcher, "测试5"),
            new Bean(R.mipmap.ic_launcher, "测试6"),
            new Bean(R.mipmap.ic_launcher, "测试7"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapter(this, list, R.layout.item_list_view_1, R.layout.item_list_view_2);
        rv.setAdapter(mAdapter);
        View mHeader = mAdapter.setHeaderView(R.layout.item_header);//此方法需要在 setLayoutManager 之前调用，否则会找不到LayoutManager，从而无法处理 GridLayoutManager   此方法会返回View
        mAdapter.setClick(new BaseRVAdapter.OnItemClick<Bean>() {
            @Override
            public void onItemClick(View v, int vId, int position, Bean item) {
                // item 的点击事件
                Toast.makeText(RecyclerViewActivity.this, "position是：" + position +  "-->" + "点击了："+item.name, Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.setClick(new BaseRVAdapter.OnItemLongClick<Bean>() {
            @Override
            public void onItemLongClick(View v, int vId, int position, Bean item) {
                // item 的长按事件
                Toast.makeText(RecyclerViewActivity.this, "position是：" + position +  "-->" + "长按了："+item.name, Toast.LENGTH_LONG).show();
            }
        });
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);
    }
}
