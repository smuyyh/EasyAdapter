package com.yuyh.easyadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;

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
            new Bean(R.mipmap.ic_launcher, "测试7"),
            new Bean(R.mipmap.ic_launcher, "测试8"),
            new Bean(R.mipmap.ic_launcher, "测试9"),
            new Bean(R.mipmap.ic_launcher, "测试10"),
            new Bean(R.mipmap.ic_launcher, "测试11"),
            new Bean(R.mipmap.ic_launcher, "测试12"),
            new Bean(R.mipmap.ic_launcher, "测试13"),
            new Bean(R.mipmap.ic_launcher, "测试14"),
            new Bean(R.mipmap.ic_launcher, "测试15"),
            new Bean(R.mipmap.ic_launcher, "测试16"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new RecyclerViewAdapter(this, list, R.layout.item_list_view_1, R.layout.item_list_view_2);
        rv.setAdapter(mAdapter);
        View mHeader = mAdapter.setHeaderView(R.layout.item_header);//此方法需要在 setLayoutManager 之前调用，否则会找不到LayoutManager，从而无法处理 GridLayoutManager   此方法会返回View
        TextView tvHeaderName = (TextView) mHeader.findViewById(R.id.tv_name);
        tvHeaderName.setText("这是头部");
        View mFooter = mAdapter.setFooterView(R.layout.item_header);
        TextView tvFooterName = (TextView) mFooter.findViewById(R.id.tv_name);
        tvFooterName.setText("这是尾部");
        mAdapter.setOnItemClickListener(new EasyRVAdapter.OnItemClickListener<Bean>() {
            @Override
            public void onItemClick(View v, int position, Bean item) {
                Toast.makeText(RecyclerViewActivity.this, "position是：" + position +  "-->" + "点击了："+item.name, Toast.LENGTH_LONG).show();
            }
        });
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new GridItemDecoration(this, true, true));
        rv.setHasFixedSize(true);

        //mAdapter.setHeaderView(R.layout.item_header, rv);
    }
}
