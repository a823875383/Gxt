package com.jsqix.gxt.app.fragment;


import android.view.ViewGroup;
import android.widget.TextView;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 双重身份订单页面
 */
@ContentView(R.layout.fragment_dou_order)
public class DouOrderFragment extends BaseFragment {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    public DouOrderFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) tvTitle.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        tvTitle.setLayoutParams(lp);
    }
}
