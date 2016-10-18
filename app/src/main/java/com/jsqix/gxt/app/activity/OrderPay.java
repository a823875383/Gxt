package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.BalanceResult;
import com.jsqix.gxt.app.obj.OrderMoneyResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

@ContentView(R.layout.activity_order_pay)
public class OrderPay extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.tv_money)
    private TextView orderMoney;
    @ViewInject(R.id.tv_order_no)
    private TextView orderNo;
    @ViewInject(R.id.tv_balance)
    private TextView balanceAvailable;

    private CustomDialog payDialog;
    private EditText payMoney, payPass;
    private Button submit, cancel;

    private int orderId;
    private double availableMoney, needMoney;
    final static int ORDER_QUERY = 0x0001, BALANCE_QUERY = 0x0010, ORDER_PAY = 0x0011;

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


    @Override
    protected void initVariable() {
        super.initVariable();
        orderId = getIntent().getIntExtra(Constant.ID, 0);
    }

    private void initPayDialog() {
        payDialog = new CustomDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_balance_pay, null);
        payMoney = (EditText) view.findViewById(R.id.et_money);
        payPass = (EditText) view.findViewById(R.id.et_pass);
        submit = (Button) view.findViewById(R.id.bt_submit);
        cancel = (Button) view.findViewById(R.id.bt_cancel);
        submit.setOnClickListener(v -> {
            if (StringUtils.isEmpty(CommUtils.textToString(payMoney))) {
                Utils.makeToast(this, "请输入支付金额");
            } else if (StringUtils.isEmpty(CommUtils.textToString(payPass))) {
                Utils.makeToast(this, "请输入支付密码");
            } else {
                balancePay();
            }
        });
        cancel.setOnClickListener(v -> {
            payDialog.dismiss();
        });
        payMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {//保留两位小数
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        payMoney.setText(s);
                        payMoney.setSelection(s.length());
                    }

                }
                if (s.toString().trim().substring(0).equals(".")) {//自动添加0
                    s = "0" + s;
                    payMoney.setText(s);
                    payMoney.setSelection(2);
                }
                if (needMoney > availableMoney) {
                    if (CommUtils.toDouble(s) > availableMoney) {
                        s = availableMoney + "";
                        payMoney.setText(s);
                        payMoney.setSelection(s.length());
                        return;
                    }
                } else {
                    if (CommUtils.toDouble(s) > needMoney) {
                        s = needMoney + "";
                        payMoney.setText(s);
                        payMoney.setSelection(s.length());
                        return;
                    }
                }


                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {//0开头
                    if (!s.toString().substring(1, 2).equals(".")) {
                        payMoney.setText(s.subSequence(1, 2));
                        payMoney.setSelection(1);
                        return;
                    }
                }
                if (s.toString().equals("0.00")) {
                    s = "0.01";
                    payMoney.setText(s);
                    payMoney.setSelection(s.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        payDialog.setView(view);
    }

    @Event(R.id.rel_balance)
    private void balanClick(View v) {
        payDialog.show();
    }

    /**
     * 查看订单支付明细
     */
    private void queryOrder() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("orderId", orderId);
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

    /**
     * 查询用户余额
     */
    private void queryBalance() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.setResultCode(BALANCE_QUERY);
        get.execute(RequestIP.GET_USER_BALANCE);
    }

    /**
     * 余额支付
     */
    private void balancePay() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("orderId", orderId);
        paras.put("payAmt", CommUtils.textToString(payMoney));
        paras.put("payPwd", CommUtils.textToString(payPass));
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_PAY);
        get.execute(RequestIP.BALANCE_PAY);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case ORDER_QUERY:
                moneyResult(result);
                break;
            case BALANCE_QUERY:
                balanceResult(result);
                break;
            case ORDER_PAY:
                payResult(result);
                break;
        }
        loadingUtils.dismiss();
    }

    private void balanceResult(String result) {
        BalanceResult balanceResult = new Gson().fromJson(result, BalanceResult.class);
        if (balanceResult != null) {
            if (balanceResult.getCode().equals("000")) {
                availableMoney = balanceResult.getObj() / 100.0;
                balanceAvailable.setText(getString(R.string.balance_available).replace("x", CommUtils.toFormat(availableMoney)));
            } else {
                Utils.makeToast(this, balanceResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void payResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                payDialog.dismiss();
                queryOrder();
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void moneyResult(String result) {
        OrderMoneyResult moneyResult = new Gson().fromJson(result, OrderMoneyResult.class);
        if (moneyResult != null) {
            if (moneyResult.getCode().equals("000")) {
                needMoney = moneyResult.getObj().get(0).getOrder_totals() - moneyResult.getObj().get(0).getPaid_amt();
                if (moneyResult.getObj().get(0).getOrder_status() == 100402) {
                    finish();
                }
                orderMoney.setText(getString(R.string.rmb) + needMoney);
                queryBalance();
            } else {
                Utils.makeToast(this, moneyResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
