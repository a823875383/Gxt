package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;
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

@ContentView(R.layout.activity_forget_next)
public class ForgetNext extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.tv_phone)
    private TextView phone;
    @ViewInject(R.id.et_code)
    private EditText code;
    @ViewInject(R.id.et_send)
    private Button send;

    final static int SEND_CODE = 0x0001, CHECK_CODE = 0x0010;

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            send.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            send.setEnabled(true);
            send.setText("重新发送");
        }
    };

    private String registePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.title_forget));
    }

    @Override
    protected void initView() {
        phone.setText("+86 " + registePhone);
        smsCode();
        downTimer.start();
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        registePhone = getIntent().getStringExtra(Constant.NAME);
    }

    @Event(R.id.bt_next)
    private void nextClick(View v) {
        if (StringUtils.isEmpty(CommUtils.textToString(code))) {

        } else {
            validateCode();
        }
    }

    /**
     * 发送验证码
     */
    private void smsCode() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("plat_id", ApiClient.P_ID);
        Map<String, Object> paras = new HashMap<>();
        paras.put("acct", registePhone);
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(SEND_CODE);
        get.execute(RequestIP.SEND_CODE);
    }

    private void validateCode() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("plat_id", ApiClient.P_ID);
        Map<String, Object> paras = new HashMap<>();
        paras.put("acct", registePhone);
        paras.put("code", CommUtils.textToString(code));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(CHECK_CODE);
        get.execute(RequestIP.CHECK_CODE);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case SEND_CODE:
                sendResult(result);
                break;
            case CHECK_CODE:
                checkResult(result);
                break;
        }

    }

    private void checkResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                Intent intent = new Intent(this, ForgetDone.class);
                intent.putExtra(Constant.NAME, registePhone);
                startActivity(intent);
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void sendResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                downTimer.start();
                send.setEnabled(false);
                send.setText("60s");
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
