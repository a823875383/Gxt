package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.LogWriter;

import gxt.jsqix.com.mycommon.base.BaseCompat;

public class FragmentActivity extends BaseCompat {
    int currentIndex = 0;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_fragment, fragment).commit();
    }

    @Override
    protected void initVariable() {
        currentIndex = getIntent().getIntExtra(Constant.INDEX, 0);
        String className = getIntent().getStringExtra(Constant.NAME);
        try {
            fragment = (Fragment) Class.forName(className).newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.INDEX, currentIndex);
            bundle.putBoolean(Constant.DATA, true);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            LogWriter.e(e.getMessage());
        }

    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected boolean isShowNetOff() {
        return false;
    }

    @Override
    protected boolean isStatusWhite() {
        return false;
    }
}
