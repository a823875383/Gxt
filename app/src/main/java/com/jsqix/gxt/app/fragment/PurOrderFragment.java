package com.jsqix.gxt.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.ViewPageAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 采购商订单页面
 */
@ContentView(R.layout.fragment_pur_order)
public class PurOrderFragment extends BaseFragment {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.tab_viewPage)
    private TabLayout tabLayout;
    @ViewInject(R.id.viewPage)
    private ViewPager viewPager;
    //订单类型
    final static String ORDER_TYPE = "order_type";
    final static int ORDER_ALL = 0, ORDER_UNPAY = 1, ORDER_UNRECIEVE = 2, ORDER_REFUND = 3, ORDER_DONE = 4;

    public PurOrderFragment() {
    }

    @Override
    protected void initView() {
        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        Fragment fragment = new OrderFragment();
        bundle.putInt(ORDER_TYPE, ORDER_ALL);
        fragment.setArguments(bundle);
        fragments.add(fragment);

        bundle.putInt(ORDER_TYPE, ORDER_UNPAY);
        fragment = new OrderFragment();
        fragment.setArguments(bundle);
        fragments.add(fragment);

        bundle.putInt(ORDER_TYPE, ORDER_UNRECIEVE);
        fragment = new OrderFragment();
        fragment.setArguments(bundle);
        fragments.add(fragment);

        bundle.putInt(ORDER_TYPE, ORDER_REFUND);
        fragment = new OrderFragment();
        fragment.setArguments(bundle);
        fragments.add(fragment);

        bundle.putInt(ORDER_TYPE, ORDER_DONE);
        fragment = new OrderFragment();
        fragment.setArguments(bundle);
        fragments.add(fragment);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.title_order_all));
        titles.add(getString(R.string.title_order_unpay));
        titles.add(getString(R.string.title_order_unreceive));
        titles.add(getString(R.string.title_order_refund));
        titles.add(getString(R.string.title_order_done));
        ViewPageAdapter adapter = new ViewPageAdapter(fragments, titles, getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp= (ViewGroup.MarginLayoutParams) tvTitle.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext),0,0);
        tvTitle.setLayoutParams(lp);
    }
}
