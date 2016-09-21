package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 选择银行卡
 */
@ContentView(R.layout.activity_bankcrad_choose)
public class BankcradChoose extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.bank_select));
    }

    @Override
    protected void initView() {

    }

    @Event(R.id.tv_add_bank)
    private void addBankClick(View v) {
        startActivity(new Intent(this, BankcardAdd.class));

    }
}
