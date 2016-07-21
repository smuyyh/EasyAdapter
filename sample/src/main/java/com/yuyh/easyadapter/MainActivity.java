package com.yuyh.easyadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listView(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
    }

    public void recyclerView(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }
}
