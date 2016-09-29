package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

@ContentView(R.layout.activity_order_pay)
public class OrderPay extends BaseToolActivity {
    private CustomDialog payDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.cashier_counter));
    }

    @Override
    protected void initView() {
        initPayDialog();
    }

    private void initPayDialog() {
        payDialog = new CustomDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_balance_pay, null);
        payDialog.setView(view);
        payDialog.setParas(0.8f, 0);
    }
    @Event(R.id.rel_balance)
    private void balanClick(View v){
        payDialog.show();
    }


}
