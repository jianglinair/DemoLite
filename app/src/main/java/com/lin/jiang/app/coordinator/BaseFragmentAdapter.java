package com.lin.jiang.app.coordinator;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by JiangLin.<br>
 * Date: 2019/06/25 14:10<br>
 * Description: VodDetailPagerAdapter 点播详情页 ViewPager，包含视频详情tab和评论tab<br>
 * <a href="https://www.google.com">文档地址</a>
 *
 * @author JiangLin
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        if (mFragments != null && !mFragments.isEmpty()) {
            return mFragments.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        return (mFragments != null && !mFragments.isEmpty()) ? mFragments.size() : 0;
    }
}
