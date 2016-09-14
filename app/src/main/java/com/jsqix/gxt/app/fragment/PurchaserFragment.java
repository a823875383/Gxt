package com.jsqix.gxt.app.fragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jsqix.gxt.app.R;
import com.jsqix.utils.DensityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 采购商个人中心
 */

@ContentView(R.layout.fragment_purchaser)
public class PurchaserFragment extends BaseFragment {
    @ViewInject(R.id.layout_user)
    private LinearLayout users;
    @ViewInject(R.id.iv_set)
    private ImageView imgSet;


    public PurchaserFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        imgSet.setVisibility(View.GONE);

        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp= (ViewGroup.MarginLayoutParams) users.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext)+ DensityUtil.dip2px(mContext,5),0,0);
        users.setLayoutParams(lp);
    }
}
