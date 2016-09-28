package com.jsqix.gxt.app.fragment;


import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 首页
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.gridView)
    PullToRefreshGridView gridView;
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        gridView.setHeaderLayout(new RefreshHeader(mContext));
        gridView.setFooterLayout(new RefreshFooter(mContext));
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) title_bar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        title_bar.setLayoutParams(lp);
    }

    @Override
    protected void getArgument() {

    }
}
