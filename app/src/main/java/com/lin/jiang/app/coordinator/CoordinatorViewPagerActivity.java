package com.lin.jiang.app.coordinator;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lin.jiang.app.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CoordinatorViewPagerActivity extends AppCompatActivity {

    ViewPager mViewPager;
    List<Fragment> mFragments;

    String[] mTitles = new String[] {
            "视频", "评论"
    };
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_view_pager);
        // 第一步，初始化ViewPager和TabLayout
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        setupViewPager();
    }

    private void setupViewPager() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Fragment fragment = new Fragment();
            mFragments.add(fragment);
        }
        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter =
                new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
