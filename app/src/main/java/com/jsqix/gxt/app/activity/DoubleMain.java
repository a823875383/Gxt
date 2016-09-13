package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.ViewPageAdapter;
import com.jsqix.gxt.app.fragment.DouOrderFragment;
import com.jsqix.gxt.app.fragment.DoubleFragment;
import com.jsqix.gxt.app.fragment.HomeFragment;
import com.jsqix.gxt.app.fragment.PurOrderFragment;
import com.jsqix.gxt.app.fragment.PurchaseFragment;
import com.jsqix.gxt.app.fragment.PurchaserFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import gxt.jsqix.com.mycommon.base.BaseCompat;

/**
 * 双重身份
 */
@ContentView(R.layout.activity_double_main)
public class DoubleMain extends BaseCompat {
    @ViewInject(R.id.viewPage)
    private ViewPager viewPager;
    @ViewInject(R.id.radio_tab)
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new PurchaseFragment());
        fragments.add(new DouOrderFragment());
        fragments.add(new DoubleFragment());

        viewPager.setAdapter(new ViewPageAdapter(fragments, getSupportFragmentManager()));
    }
    @Event(value = R.id.radio_tab, type = RadioGroup.OnCheckedChangeListener.class)
    private void changeTab(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_home:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.radio_purchase:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.radio_order:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.radio_user:
                viewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Event(value = R.id.viewPage, type = ViewPager.OnPageChangeListener.class, method = "onPageSelected")
    private void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.radio_home);
                break;
            case 1:
                radioGroup.check(R.id.radio_purchase);
                break;
            case 2:
                radioGroup.check(R.id.radio_order);
                break;
            case 3:
                radioGroup.check(R.id.radio_user);
                break;
        }
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }
}
