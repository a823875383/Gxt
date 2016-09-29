package com.jsqix.gxt.app.fragment;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * 商品页面
 */
@ContentView(R.layout.fragment_merchandise)
public class MerchandiseFragment extends BaseFragment {
    private CustomDialog specDialog;


    public MerchandiseFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        initSpecDialog();
    }

    private void initSpecDialog() {
        specDialog = new CustomDialog(mContext, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_merchandise_spec, null);
        specDialog.setView(view, Gravity.BOTTOM);
        specDialog.setParas(1, 0);
    }

    @Override
    protected void getArgument() {
    }

    @Event(R.id.rel_spec)
    private void specClick(View v) {
        specDialog.show();
    }
}
