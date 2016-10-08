package com.jsqix.gxt.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.FragmentActivity;
import com.jsqix.gxt.app.activity.OrderActivity;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 双重身份订单页面
 */
@ContentView(R.layout.fragment_dou_order)
public class DouOrderFragment extends BaseFragment {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    public DouOrderFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) tvTitle.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        tvTitle.setLayoutParams(lp);
    }

    @Override
    protected void getArgument() {
    }

    @Event(value = {R.id.pur_order_all, R.id.pur_order_unpay, R.id.pur_order_unreceive, R.id.pur_order_refund, R.id.pur_order_done})
    private void purchaseClick(View v) {
        Intent intent = new Intent(mContext, FragmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.NAME, mContext.getPackageName() + ".fragment." + "PurOrderFragment");
        switch (v.getId()) {
            case R.id.pur_order_all:
                bundle.putInt(Constant.INDEX, 0);
                break;
            case R.id.pur_order_unpay:
                bundle.putInt(Constant.INDEX, 1);
                break;
            case R.id.pur_order_unreceive:
                bundle.putInt(Constant.INDEX, 2);
                break;
            case R.id.pur_order_refund:
                bundle.putInt(Constant.INDEX, 3);
                break;
            case R.id.pur_order_done:
                bundle.putInt(Constant.INDEX, 4);
                break;

        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Event(value = {R.id.sup_order_all, R.id.sup_order_unpay, R.id.sup_order_unreceive, R.id.sup_order_refund, R.id.sup_order_done})
    private void supplierClick(View v) {
        Intent intent = new Intent(mContext, OrderActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.sup_order_all:
                bundle.putString(Constant.TITLE, getString(R.string.title_order_all));
                bundle.putString(Constant.ORDER_TYPE, "");
                break;
            case R.id.sup_order_unpay:
                bundle.putString(Constant.TITLE, getString(R.string.title_order_unpay));
                bundle.putString(Constant.ORDER_TYPE, "");
                break;
            case R.id.sup_order_unreceive:
                bundle.putString(Constant.TITLE, getString(R.string.title_order_unreceive));
                bundle.putString(Constant.ORDER_TYPE, "");
                break;
            case R.id.sup_order_refund:
                bundle.putString(Constant.TITLE, getString(R.string.title_order_refund));
                bundle.putString(Constant.ORDER_TYPE, "");
                break;
            case R.id.sup_order_done:
                bundle.putString(Constant.TITLE, getString(R.string.title_order_done));
                bundle.putString(Constant.ORDER_TYPE, "");
                break;

        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
