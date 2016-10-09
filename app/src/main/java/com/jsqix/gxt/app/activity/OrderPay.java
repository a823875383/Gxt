package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

@ContentView(R.layout.activity_order_pay)
public class OrderPay extends BaseToolActivity {
    @ViewInject(R.id.tv_money)
    private TextView orderMoney;
    @ViewInject(R.id.tv_order_no)
    private TextView orderNo;
    private CustomDialog payDialog;

    private int orderId;
    private double orderTotal;

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
        orderMoney.setText(getString(R.string.rmb) + orderTotal);
        orderNo.setText(orderId + "");

        initPayDialog();
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        orderId = getIntent().getIntExtra(Constant.ID, 0);
        orderTotal = getIntent().getDoubleExtra(Constant.DATA, 0);
    }

    private void initPayDialog() {
        payDialog = new CustomDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_balance_pay, null);
        payDialog.setView(view);
        payDialog.setParas(0.8f, 0);
    }

    @Event(R.id.rel_balance)
    private void balanClick(View v) {
        payDialog.show();
    }


}
