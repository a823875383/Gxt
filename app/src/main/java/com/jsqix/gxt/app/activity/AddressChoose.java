package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_address_manage)
public class AddressChoose extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.address_choose));
    }

    @Override
    protected void initView() {
        mRight.setText(getString(R.string.manage));
        mRight.setTextColor(getResources().getColor(R.color.green));

    }

    @Event(R.id.tv_right)
    private void manaClick(View v) {
        startActivity(new Intent(this, AddressManage.class));
    }
}
