package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 提现
 */
@ContentView(R.layout.activity_withdraw)
public class WithdrawActivity extends BaseToolActivity {
    @ViewInject(R.id.rel_choose)
    private RelativeLayout rel_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.balance_withdraw));
    }

    @Override
    protected void initView() {
        int[] colors={0xff29B292,0xff23A79F,0xff1D9DB4};//分别为开始颜色，中间夜色，结束颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        rel_choose.setBackgroundDrawable(gd);
    }

    @Event(R.id.rel_choose)
    private void chooseClick(View v) {
        startActivity(new Intent(this, BankcradChoose.class));

    }


}
