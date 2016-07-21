package com.yuyh.easyadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rv;
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
        rv.setAdapter(new RecyclerViewAdapter(this, list, R.layout.item_list_view_1, R.layout.item_list_view_2));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);
    }
}
