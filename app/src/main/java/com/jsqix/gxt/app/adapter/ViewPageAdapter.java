package com.jsqix.gxt.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongqing on 16/9/12.
 * viewpage 导航adapter
 */
public class ViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> data = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public ViewPageAdapter(List<Fragment> list, FragmentManager manager) {
        super(manager);
        this.data = list;
    }

    public ViewPageAdapter(List<Fragment> list, List<String> titles, FragmentManager manager) {
        this(list, manager);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
