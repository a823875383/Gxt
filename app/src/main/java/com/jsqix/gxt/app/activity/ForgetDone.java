package com.jsqix.gxt.app.activity;

import android.os.Bundle;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_forget_done)
public class ForgetDone extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.title_forget));
    }

    @Override
    protected void initView() {

    }
}
