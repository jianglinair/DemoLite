package com.lin.jiang.app.expandable;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lin.jiang.app.R;

import java.util.ArrayList;

public class ExpandableTextViewActivity extends AppCompatActivity {

    RecyclerView main_rv;
    MainAdapter adapter;

    ArrayList<DataBean> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_text_view);

        models = new ArrayList<>();
        String[] arrays = getResources().getStringArray(R.array.news);
        for (String array : arrays) {
            DataBean bean = new DataBean();
            bean.setText(array);
            models.add(bean);
        }

        main_rv = findViewById(R.id.main_rv);
        main_rv.setHasFixedSize(true);
        main_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this, models);
        main_rv.setAdapter(adapter);
    }
}
