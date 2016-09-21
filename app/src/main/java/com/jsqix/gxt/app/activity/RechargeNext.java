package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_recharge_next)
public class RechargeNext extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.cashier_counter));
    }

    @Override
    protected void initView() {

    }

    @Event(R.id.bt_login)
    private void loginClick(View v) {
        startActivity(new Intent(this, RechargeDone.class));
    }

    @Event(R.id.tv_forget)
    private void forgetClick(View v) {
        startActivity(new Intent(this, RechargeForget.class));

    }


}
