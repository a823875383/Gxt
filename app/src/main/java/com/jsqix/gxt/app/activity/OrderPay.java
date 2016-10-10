package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.OrderPayResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

@ContentView(R.layout.activity_order_pay)
public class OrderPay extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.tv_money)
    private TextView orderMoney;
    @ViewInject(R.id.tv_order_no)
    private TextView orderNo;
    private CustomDialog payDialog;

    private int orderId;
    final static int ORDER_QUERY = 0x0001;

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
        orderNo.setText(orderId + "");

        initPayDialog();
        queryOrder();
    }

    private void queryOrder() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("orderId", orderNo);
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_QUERY);
        get.execute(RequestIP.QUERY_ORDER);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        orderId = getIntent().getIntExtra(Constant.ID, 0);
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


    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case ORDER_QUERY:
                OrderPayResult payResult = new Gson().fromJson(result, OrderPayResult.class);
                if (payResult != null) {
                    if (payResult.getCode().equals("000")) {
                        orderMoney.setText(getString(R.string.rmb) + (payResult.getObj().get(0).getOrder_totals() - payResult.getObj().get(0).getPaid_amt()));
                    } else {
                        Utils.makeToast(this, payResult.getMsg());
                    }
                } else {
                    Utils.makeToast(this, getString(R.string.network_timeout));
                }
                break;
        }
    }
}
