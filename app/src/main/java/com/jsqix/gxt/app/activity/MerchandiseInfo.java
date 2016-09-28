package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.ViewPageAdapter;
import com.jsqix.gxt.app.fragment.MerDetailFragment;
import com.jsqix.gxt.app.fragment.MerchandiseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

@ContentView(R.layout.activity_merchandise_info)
public class MerchandiseInfo extends BaseCompat {
    @ViewInject(R.id.title_bar)
    private RelativeLayout titleBar;
    @ViewInject(R.id.tab_viewPage)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewPage)
    private ViewPager viewPager;
    @ViewInject(R.id.bt_buy)
    private Button buy;
    @ViewInject(R.id.bt_cart)
    private Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) titleBar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
        titleBar.setLayoutParams(lp);

    }

    @Override
    protected void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MerchandiseFragment());
        fragments.add(new MerDetailFragment());
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.merchandise));
        titles.add(getString(R.string.detail));
        ViewPageAdapter adapter = new ViewPageAdapter(fragments, titles, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initVariable() {

    }

    @Event(R.id.tv_left)
    private void backClick(View v) {
        finish();
    }

    @Event(R.id.bt_buy)
    private void buyClick(View v) {
        startActivity(new Intent(this, OrderSubmit.class));
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
        return true;
    }

    @Override
    protected boolean isStatusWhite() {
        return true;
    }
}
