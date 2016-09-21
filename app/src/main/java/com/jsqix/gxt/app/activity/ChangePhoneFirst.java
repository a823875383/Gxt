package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_forget_first)
public class ChangePhoneFirst extends BaseToolActivity {
    @ViewInject(R.id.et_name)
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.title_change_phone));
    }

    @Override
    protected void initView() {
        phone.setHint(getString(R.string.login_phone));
    }

    @Event(R.id.bt_next)
    private void nextClick(View v) {
        startActivity(new Intent(this, ChangePhoneNext.class));
    }
}
