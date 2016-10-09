package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.ViewPageAdapter;
import com.jsqix.gxt.app.app.AppContext;
import com.jsqix.gxt.app.fragment.HomeFragment;
import com.jsqix.gxt.app.fragment.PurOrderFragment;
import com.jsqix.gxt.app.fragment.PurchaseFragment;
import com.jsqix.gxt.app.fragment.PurchaserFragment;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import gxt.jsqix.com.mycommon.base.BaseCompat;

/**
 * 采购商主页
 */
@ContentView(R.layout.activity_purchaser_main)
public class PurchaserMain extends BaseCompat {
    @ViewInject(R.id.viewPage)
    private ViewPager viewPager;
    @ViewInject(R.id.radio_tab)
    private RadioGroup radioGroup;

    private int oldIndex = 0, selectIndex = 0, orderType = 0;

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

        PurOrderFragment fragment = new PurOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.INDEX, orderType);
        fragment.setArguments(bundle);

        fragments.add(fragment);
        fragments.add(new PurchaserFragment());

        viewPager.setAdapter(new ViewPageAdapter(fragments, getSupportFragmentManager()));
        viewPager.setCurrentItem(selectIndex);
    }

    @Override
    protected void initVariable() {
        if (getIntent().getExtras() != null) {
            selectIndex = getIntent().getIntExtra(Constant.INDEX, 0);
            orderType = getIntent().getIntExtra(Constant.ORDER_TYPE, 0);
        }
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
