package com.jsqix.gxt.app.activity;

import android.os.Bundle;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 供应商订单页面
 */
@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseToolActivity {
    private String order_type = "";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(title);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initVariable() {
        super.initVariable();
        title = getIntent().getExtras().getString("", "");
        order_type = getIntent().getExtras().getString("", "");
    }
}
