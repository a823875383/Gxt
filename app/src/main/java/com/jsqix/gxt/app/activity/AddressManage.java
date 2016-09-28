package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_address_manage)
public class AddressManage extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.address_manage));
    }

    @Override
    protected void initView() {

    }

    @Event(value = {R.id.tv_add_address, R.id.lin_add})
    private void AddrClick(View v) {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra(Constant.TITLE, getString(R.string.title_add_address));
        startActivity(intent);
    }
}
