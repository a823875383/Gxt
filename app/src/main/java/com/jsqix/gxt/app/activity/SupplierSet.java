package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.CountResult;
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

/**
 * 供应商个人中心
 */
@ContentView(R.layout.activity_supplier)
public class SupplierSet extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.tv_bank_added)
    private TextView bankCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.user_center));
    }

    @Override
    protected void initView() {
        queryBank();
    }

    @Event(R.id.tv_change_pass)
    private void changePass(View v) {
        startActivity(new Intent(this, ChangePassword.class));
    }

    @Event(R.id.tv_change_phone)
    private void changePhone(View v) {
        startActivity(new Intent(this, ChangePhoneFirst.class));
    }

    @Event(R.id.tv_add_bank)
    private void addbankClick(View v) {
        startActivity(new Intent(this, BankcardAdd.class));
    }

    @Event(R.id.tv_bank_added)
    private void addedClick(View v) {
        startActivity(new Intent(this, BankcardAdded.class));
    }

    @Event(R.id.tv_exit)
    private void exitClick(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * 查询用户银行卡数量
     */
    private void queryBank() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.BLANK_COUNT);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        CountResult countResult = new Gson().fromJson(result, CountResult.class);
        if (countResult != null) {
            if (countResult.getCode().equals("000")) {
                bankCount.setText(getString(R.string.blank).replace("x", countResult.getObj() + ""));
            } else {
                Utils.makeToast(this, countResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
