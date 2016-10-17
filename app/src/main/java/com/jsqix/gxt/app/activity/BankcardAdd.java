package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.BankUtils;
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

/**
 * 添加银行卡
 */
@ContentView(R.layout.activity_bankcard_add)
public class BankcardAdd extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.et_bank_name)
    private EditText bankName;
    @ViewInject(R.id.et_bank_no)
    private EditText bankNumber;
    @ViewInject(R.id.et_bank_phone)
    private EditText bankPhone;

    private int bankNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.add_bank));
    }

    @Override
    protected void initView() {
    }

    @Event(R.id.bt_done)
    private void bankClick(View v) {
        if (StringUtils.isEmpty(CommUtils.textToString(bankName))) {
            Utils.makeToast(this, getString(R.string.input_card_user));
        } else if (StringUtils.isEmpty(CommUtils.textToString(bankNumber))) {
            Utils.makeToast(this, getString(R.string.input_card_no));
        } else if (StringUtils.isEmpty(CommUtils.textToString(bankPhone))) {
            Utils.makeToast(this, getString(R.string.input_card_phone));
        } else if (StringUtils.notPhone(CommUtils.textToString(bankPhone))) {
            Utils.makeToast(this, "手机号格式不正确");
        } else {
            if (CommUtils.textToString(bankNumber).length() == 16 || CommUtils.textToString(bankNumber).length() == 19) {
//                if (CheckBankCard.checkBankCard(CommUtils.textToString(bankNumber))) {
                String bank_name = BankUtils.getNameOfBank(CommUtils.textToString(bankNumber));
                if (bank_name.contains("建设银行")) {
                    bankNo = 102801;
                } else if (bank_name.contains("工商银行")) {
                    bankNo = 102802;
                } else if (bank_name.contains("农业银行")) {
                    bankNo = 102803;
                } else if (bank_name.contains("交通银行")) {
                    bankNo = 102804;
                } else if (bank_name.contains("中国银行")) {
                    bankNo = 102805;
                } else if (bank_name.contains("招商银行")) {
                    bankNo = 102806;
                } else if (bank_name.contains("民生银行")) {
                    bankNo = 102807;
                } else if (bank_name.contains("浦东发展银行")) {
                    bankNo = 102808;
                } else if (bank_name.contains("邮政储蓄银行")) {
                    bankNo = 102809;
                } else if (bank_name.contains("中信银行")) {
                    bankNo = 102810;
                } else if (bank_name.contains("光大银行")) {
                    bankNo = 102811;
                } else {
                    Utils.makeToast(this, "您输入的银行卡暂不支持");
                    return;
                }
                addBank();
//                } else {
//                    Utils.makeToast(this, "您输入的银行卡不正确");
//                }
            } else {
                Utils.makeToast(this, "您输入的银行卡不正确");
            }
        }
    }

    private void addBank() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        paras.put("userName", CommUtils.textToString(bankName));
        paras.put("bankAccount", CommUtils.textToString(bankNumber));
        paras.put("phone", CommUtils.textToString(bankPhone));
        paras.put("bankNo", bankNo);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.ADD_BANKS);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                finish();
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
