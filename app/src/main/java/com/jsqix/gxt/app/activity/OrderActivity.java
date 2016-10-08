package com.jsqix.gxt.app.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

/**
 * 供应商订单页面
 */
@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseToolActivity {
    private String order_type = "";
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(title);
        mTitle.setTextColor(Color.WHITE);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_back_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBack.setCompoundDrawables(drawable, null, null, null);
        titleBase.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initVariable() {
        super.initVariable();
        title = getIntent().getExtras().getString(Constant.TITLE, "");
        order_type = getIntent().getExtras().getString(Constant.ORDER_TYPE, "");
    }

}
