package com.lin.jiang.app.scroll;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lin.jiang.app.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * scroll 滚动的是view内部的内容，x方向负数向右正数向左，y方向负数向下正数向上；
 * scrollTo 和 scrollBy 都是跳跃滚动，不是平滑滚动，平滑滚动需要用到工具类 Scroller.
 */
public class ScrollerActivity extends AppCompatActivity {

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        mBtn1 = findViewById(R.id.btn_first);
        mBtn2 = findViewById(R.id.btn_second);
        mBtn3 = findViewById(R.id.btn_third);

        mBtn1.setOnClickListener(v -> Log.d("[jianglin]", "ScrollerActivity.onClick: first button is clicked."));
        mBtn2.setOnClickListener(v -> Log.d("[jianglin]", "ScrollerActivity.onClick: second button is clicked."));
        mBtn3.setOnClickListener(v -> Log.d("[jianglin]", "ScrollerActivity.onClick: third button is clicked."));
    }
}
