package com.lin.jiang.app.coordinator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lin.jiang.app.R;
import com.lin.jiang.app.widget.BaseRecyclerAdapter;
import com.lin.jiang.app.widget.CommonRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CoordinatorWithAppBarActivity extends AppCompatActivity {
    private BaseRecyclerAdapter<String> mAdapter;
    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_with_app_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher));
        toolbar.setTitle("这是标题");
        toolbar.inflateMenu(R.menu.menu_coor);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        RecyclerView recyclerView = findViewById(R.id.rv_coor);
        mStringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStringList.add("测试：" + i);
        }

        mAdapter = new BaseRecyclerAdapter<String>(this, mStringList, R.layout.item_content_coordinator_with_app_bar) {
            @Override
            public void convert(CommonRecyclerHolder holder, String item, int position, boolean isScrolling) {
                holder.setText(R.id.item_text, mStringList.get(position));
            }
        };
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

}
