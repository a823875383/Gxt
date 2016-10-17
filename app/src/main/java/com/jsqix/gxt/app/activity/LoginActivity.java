package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.app.AppContext;
import com.jsqix.gxt.app.obj.LoginResult;
import com.jsqix.gxt.app.utils.Constant;
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

/**
 * 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseCompat implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.et_name)
    private EditText loginName;//登录名
    @ViewInject(R.id.et_pass)
    private EditText loginPass;//密码
    @ViewInject(R.id.et_code)
    private EditText loginCode;//登录名
//    @ViewInject(R.id.iv_code)
//    private ImageView imgCode;//验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
//        imgCode.setImageBitmap(Code.getInstance()
//                .setHeight(45)
//                .setWidth(100)
//                .createBitmap());
//        LogWriter.e("TAG", Code.getInstance().getCode());
    }

    @Override
    protected void initVariable() {

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

    @Event(R.id.tv_forget)
    private void forgetClick(View v) {
        startActivity(new Intent(this, ForgetFirst.class));
    }

    @Event(R.id.bt_login)
    private void loginClick(View v) {
        if (CommUtils.textToString(loginName).equals("0")) {
            startActivity(new Intent(this, SupplierMain.class));
        } else if (CommUtils.textToString(loginName).equals("1")) {
            startActivity(new Intent(this, DoubleMain.class));
        } else {
            login();
        }
    }

    /**
     * 登录
     */
    private void login() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("phone", CommUtils.textToString(loginName));
        paras.put("loginPwd", CommUtils.textToString(loginPass));
        paras.put("timeStamp", System.currentTimeMillis());
        Map<String, Object> unparas = new HashMap<>();
        unparas.put("plat_id", ApiClient.P_ID);
        HttpGet get = new HttpGet(this, unparas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.LOGIN);

    }


    @Override
    public void getCallback(int resultCode, String result) {
        LoginResult resultBean = new Gson().fromJson(result, LoginResult.class);
        if (resultBean != null) {
            if (resultBean.getCode().equals("000")) {
                if (resultBean.getObj().getSuppliter_status() == 1) {//供应商
                    if (resultBean.getObj().getProcure_status() == 1) {//采购商
                        startActivity(new Intent(this, DoubleMain.class));
                        aCache.put(Constant.U_IDENTITY, 2);
                    } else {
                        startActivity(new Intent(this, SupplierMain.class));
                        aCache.put(Constant.U_IDENTITY, 1);
                    }
                } else if (resultBean.getObj().getProcure_status() == 1) {//采购商
                    startActivity(new Intent(this, PurchaserMain.class));
                    aCache.put(Constant.U_IDENTITY, 0);
                }
                aCache.put(Constant.USER, resultBean.getObj());
                aCache.put(Constant.U_ID, resultBean.getObj().getId());
                finish();
            } else {
                Utils.makeToast(this, resultBean.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return AppContext.getInstance().closeAppByBack(keyCode, event);
    }
}
