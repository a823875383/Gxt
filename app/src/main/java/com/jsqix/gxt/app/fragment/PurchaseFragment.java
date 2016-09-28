package com.jsqix.gxt.app.fragment;


import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 采购单页面
 */
@ContentView(R.layout.fragment_purchase)
public class PurchaseFragment extends BaseFragment {
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;


    public PurchaseFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {

        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) title_bar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        title_bar.setLayoutParams(lp);
    }

    @Override
    protected void getArgument() {

    }
}
