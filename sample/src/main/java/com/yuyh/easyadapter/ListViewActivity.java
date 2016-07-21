package com.yuyh.easyadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private ListView lv;

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
        setContentView(R.layout.activity_list_view);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ListViewAdapter(this, list, R.layout.item_list_view_1, R.layout.item_list_view_2));
    }
}
