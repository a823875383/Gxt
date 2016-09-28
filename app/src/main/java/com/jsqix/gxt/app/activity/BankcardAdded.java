package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 我的银行卡
 */
@ContentView(R.layout.activity_bankcard_added)
public class BankcardAdded extends BaseToolActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.banks));
    }

    @Override
    protected void initView() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_bank_add);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mRight.setCompoundDrawables(drawable, null, null, null);
    }

    @Event(R.id.tv_right)
    private void addBankClick(View v) {
        startActivity(new Intent(this, BankcardAdd.class));
    }
}
