package com.jsqix.gxt.app.fragment;


import android.view.View;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;

/**
 * 双重身份的个人中心
 */
@ContentView(R.layout.fragment_double)
public class DoubleFragment extends BaseFragment {
    @ViewInject(R.id.iv_set)
    private ImageView imgSet;


    public DoubleFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        imgSet.setVisibility(View.GONE);
    }

}
