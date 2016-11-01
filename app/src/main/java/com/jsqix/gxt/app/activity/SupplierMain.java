package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.app.AppContext;
import com.jsqix.gxt.app.obj.CountResult;
import com.jsqix.gxt.app.obj.UserBalanceResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.DensityUtil;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.api.ApiClient;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 供应商主页
 */
@ContentView(R.layout.activity_main_supplier)
public class SupplierMain extends BaseCompat implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.layout_user)
    private LinearLayout users;
    @ViewInject(R.id.tv_money)
    private TextView balanceAvailable;
    @ViewInject(R.id.tv_order_all)
    private TextView orderAllCount;
    @ViewInject(R.id.tv_order_unpay)
    private TextView orderUnpayCount;
    @ViewInject(R.id.tv_order_unreceive)
    private TextView orderUnreceiveCount;
    @ViewInject(R.id.tv_order_refund)
    private TextView orderRefundCount;
    @ViewInject(R.id.tv_order_done)
    private TextView orderDoneCount;


    final static int BALANCE_QUERY = 0x0001, ORDER_ALL = 0x0010, ORDER_UNPAY = 0x0020, ORDER_UNRECEIVE1 = 0x0030, ORDER_UNRECEIVE2 = 0x0031, ORDER_REFUND = 0x0040, ORDER_DONE1 = 0x0050, ORDER_DONE2 = 0x0051;


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

    @Override
    protected void onResume() {
        super.onResume();

        queryRefundNum(ORDER_REFUND);
        queryOrderNum(-1, ORDER_ALL);
        queryOrderNum(100401, ORDER_UNPAY);
        queryOrderNum(100402, ORDER_UNRECEIVE1);
        queryOrderNum(100403, ORDER_UNRECEIVE2);
        queryOrderNum(100404, ORDER_DONE1);
        queryOrderNum(100405, ORDER_DONE2);
        queryBalance();
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
                intent.putExtra(Constant.ORDER_TYPE, 0);
                break;
            case R.id.order_unpay:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_unpay));
                intent.putExtra(Constant.ORDER_TYPE, 1);
                break;
            case R.id.order_unreceive:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_unreceive));
                intent.putExtra(Constant.ORDER_TYPE, 2);
                break;
            case R.id.order_refund:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_refund_purchase));
                intent.putExtra(Constant.ORDER_TYPE, 3);
                break;
            case R.id.order_done:
                intent.putExtra(Constant.TITLE, getString(R.string.title_order_done));
                intent.putExtra(Constant.ORDER_TYPE, 4);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return AppContext.getInstance().closeAppByBack(keyCode, event);
    }

    /**
     * 查询用户余额
     */
    private void queryBalance() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("platId", ApiClient.P_ID);
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        paras.put("acctType", ApiClient.ACC_TYPE);
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.setResultCode(BALANCE_QUERY);
        get.execute(RequestIP.USER_BALANCE);
    }

    /**
     * 查询订单数量
     *
     * @param resultCode
     */
    private void queryOrderNum(int orderStatus, int resultCode) {
        Map<String, Object> unParas = new HashMap<>();
        if (orderStatus != -1) {
            unParas.put("orderStatus", orderStatus);
        }
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(resultCode);
        get.execute(RequestIP.ORDER_COUNT);
    }

    private void queryRefundNum(int resultCode) {
        Map<String, Object> unParas = new HashMap<>();
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(resultCode);
        get.execute(RequestIP.ORDER_COUNT1);
    }


    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case BALANCE_QUERY:
                balanceResult(result);
                break;
            default:
                countResult(resultCode, result);
                break;
        }
        loadingUtils.dismiss();
    }

    int num = 0;

    private void countResult(int resultCode, String result) {
        CountResult countResult = new Gson().fromJson(result, CountResult.class);
        if (countResult != null) {
            if (countResult.getCode().equals("000")) {
                switch (resultCode) {
                    case ORDER_ALL:
                        num += countResult.getObj();
                        orderAllCount.setText(getString(R.string.order_all).replace("x", num + ""));
                        break;
                    case ORDER_UNPAY:
                        orderUnpayCount.setText(getString(R.string.order_unpay).replace("x", countResult.getObj() + ""));
                        break;
                    case ORDER_UNRECEIVE1:
                        num = countResult.getObj();
                        break;
                    case ORDER_UNRECEIVE2:
                        num += countResult.getObj();
                        orderUnreceiveCount.setText(getString(R.string.order_unreceive).replace("x", num + ""));
                        break;
                    case ORDER_REFUND:
                        num = countResult.getObj();
                        orderRefundCount.setText(getString(R.string.order_refund).replace("x", num + ""));
                        break;
                    case ORDER_DONE1:
                        num = countResult.getObj();
                        break;
                    case ORDER_DONE2:
                        num += countResult.getObj();
                        orderDoneCount.setText(getString(R.string.order_done).replace("x", num + ""));
                        break;
                }
            } else {
                Utils.makeToast(this, countResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }

    }

    private void balanceResult(String result) {
        UserBalanceResult balanceResult = new Gson().fromJson(result, UserBalanceResult.class);
        if (balanceResult != null) {
            if (balanceResult.getCode().equals("000")) {
                balanceAvailable.setText(CommUtils.toFormat(balanceResult.getObj().getAcc_balance() / 100.0));
            } else {
                Utils.makeToast(this, balanceResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

}
