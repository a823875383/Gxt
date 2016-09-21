package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;
import com.jsqix.utils.LogWriter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.util.Code;

/**
 * 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseCompat {
    @ViewInject(R.id.et_name)
    private EditText loginName;//登录名
    @ViewInject(R.id.et_pass)
    private EditText loginPass;//密码
    @ViewInject(R.id.et_code)
    private EditText loginCode;//验证码
    @ViewInject(R.id.iv_code)
    private ImageView imgCode;//登录名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        imgCode.setImageBitmap(Code.getInstance()
                .setHeight(45)
                .setWidth(100)
                .createBitmap());
        LogWriter.e("TAG", Code.getInstance().getCode());
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

    @Event(R.id.tv_forget)
    private void forgetClick(View v) {
        startActivity(new Intent(this, ForgetFirst.class));
    }

    @Event(R.id.bt_login)
    private void loginClick(View v) {
        if (loginName.getText().toString().trim().equals("0")) {
            startActivity(new Intent(this, PurchaserMain.class));
        } else if (loginName.getText().toString().trim().equals("1")) {
            startActivity(new Intent(this, DoubleMain.class));
        } else {
            startActivity(new Intent(this, SupplierMain.class));
        }
    }


}
