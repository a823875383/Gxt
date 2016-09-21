package com.jsqix.gxt.app.activity;

import android.os.Bundle;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 添加银行卡
 */
@ContentView(R.layout.activity_bankcard_add)
public class BankcardAdd extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.add_bank));
    }

    @Override
    protected void initView() {
    }
}
