package com.jsqix.gxt.app.activity;

import android.os.Bundle;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;

import gxt.jsqix.com.mycommon.base.BaseCompat;

/**
 * 供应商主页
 */
@ContentView(R.layout.activity_main_supplier)
public class SupplierMain extends BaseCompat {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return -1;
    }
}
