package com.jsqix.gxt.app.activity;

import android.os.Bundle;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_recharge_forget)
public class ChangePassword extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.title_change_pass));
    }

    @Override
    protected void initView() {
    }

}
