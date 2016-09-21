package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 供应商个人中心
 */
@ContentView(R.layout.activity_supplier)
public class SupplierSet extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.user_center));
    }

    @Override
    protected void initView() {

    }

    @Event(R.id.tv_change_pass)
    private void changePass(View v) {
        startActivity(new Intent(this, ChangePassword.class));
    }

    @Event(R.id.tv_change_phone)
    private void changePhone(View v) {
        startActivity(new Intent(this, ChangePhoneFirst.class));
    }

    @Event(R.id.tv_add_bank)
    private void addbankClick(View v) {
        startActivity(new Intent(this, BankcardAdd.class));
    }

    @Event(R.id.tv_bank_added)
    private void addedClick(View v) {
        startActivity(new Intent(this, BankcardAdded.class));
    }

}
