package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_supplier)
public class SupplierSet extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        mTitle.setText(getString(R.string.user_center));
    }

    @Event(R.id.tv_change_pass)
    private void changePass(View v) {
        startActivity(new Intent(this, ChangePassword.class));
    }

    @Event(R.id.tv_change_phone)
    private void changePhone(View v) {
        startActivity(new Intent(this, ChangePhoneFirst.class));
    }

}
