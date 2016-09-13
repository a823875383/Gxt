package com.jsqix.gxt.app.fragment;


import android.view.View;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;

/**
 * 采购商个人中心
 */

@ContentView(R.layout.fragment_purchaser)
public class PurchaserFragment extends BaseFragment {
    @ViewInject(R.id.iv_set)
    private ImageView imgSet;


    public PurchaserFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        imgSet.setVisibility(View.GONE);
    }
}
