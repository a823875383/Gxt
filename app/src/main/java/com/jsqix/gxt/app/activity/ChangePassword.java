package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.ApiClient;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

@ContentView(R.layout.activity_recharge_forget)
public class ChangePassword extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.et_pass)
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.title_change_pass));
    }

    @Override
    protected void initView() {
    }

    @Event(R.id.bt_done)
    private void doneClick(View v) {
        if (StringUtils.isEmpty(CommUtils.textToString(pass))) {
            Utils.makeToast(this, "请输入密码");
        } else {
            updatePassWord();
        }
    }

    private void updatePassWord() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("plat_id", ApiClient.P_ID);
        unParas.put("acct", "18662539527");
        Map<String, Object> paras = new HashMap<>();
//        paras.put("plat_id", ApiClient.P_ID);
//        paras.put("acct", "18662539527");
        paras.put("loginPwd", CommUtils.textToString(pass));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, /*unParas,*/ paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.UPDATE_PWD);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());

        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
