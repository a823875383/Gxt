package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.DensityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 供应商主页
 */
@ContentView(R.layout.activity_main_supplier)
public class SupplierMain extends BaseCompat {
    @ViewInject(R.id.layout_user)
    private LinearLayout users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) users.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this) + DensityUtil.dip2px(this, 5), 0, 0);
        users.setLayoutParams(lp);
    }

    @Override
    protected void initVariable() {

    }

    @Event(R.id.iv_set)
    private void setClick(View v) {
        startActivity(new Intent(this, SupplierSet.class));
    }

    @Event(R.id.bt_recharge)
    private void rechargeClick(View v) {
        startActivity(new Intent(this, RechargeFirst.class));
    }

    @Event(R.id.bt_withdraw)
    private void withDrawClick(View v) {
        startActivity(new Intent(this, WithdrawActivity.class));
    }

    @Event(R.id.goods_manage)
    private void goodsClick(View v) {
        startActivity(new Intent(this, SupplierMerchandise.class));
    }

    @Event(value = {R.id.order_all, R.id.order_unpay, R.id.order_unreceive, R.id.order_refund, R.id.order_done})
    private void orderClick(View v) {
        Intent intent = new Intent(this, OrderActivity.class);
        switch (v.getId()) {
            case R.id.order_all:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_all));
                break;
            case R.id.order_unpay:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_unpay));
                break;
            case R.id.order_unreceive:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_unreceive));
                break;
            case R.id.order_refund:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_refund_purchase));
                break;
            case R.id.order_done:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_done));
                break;
        }
        startActivity(intent);
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return -1;
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
