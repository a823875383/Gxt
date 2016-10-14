package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.ViewPageAdapter;
import com.jsqix.gxt.app.app.AppContext;
import com.jsqix.gxt.app.fragment.DouOrderFragment;
import com.jsqix.gxt.app.fragment.DoubleFragment;
import com.jsqix.gxt.app.fragment.HomeFragment;
import com.jsqix.gxt.app.fragment.PurchaseFragment;

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

    int oldIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initTitle() {

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

    @Override
    protected void initVariable() {

    }

    @Event(value = R.id.radio_tab, type = RadioGroup.OnCheckedChangeListener.class)
    private void changeTab(RadioGroup group, int checkedId) {
        int newIndex = 0;
        switch (checkedId) {
            case R.id.radio_home:
                newIndex = 0;
                break;
            case R.id.radio_purchase:
                newIndex = 1;
                break;
            case R.id.radio_order:
                newIndex = 2;
                break;
            case R.id.radio_user:
                newIndex = 3;
                break;
        }
        if (Math.abs(newIndex - oldIndex) > 1) {
            viewPager.setCurrentItem(newIndex, false);
        } else {
            viewPager.setCurrentItem(newIndex);
        }
        oldIndex = newIndex;
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

    @Override
    protected boolean isShowNetOff() {
        return false;
    }

    @Override
    protected boolean isStatusWhite() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return AppContext.getInstance().closeAppByBack(keyCode, event);
    }
}
