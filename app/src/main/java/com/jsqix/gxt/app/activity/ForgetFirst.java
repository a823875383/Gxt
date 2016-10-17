package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

/**
 *
 */
@ContentView(R.layout.activity_forget_first)
public class ForgetFirst extends BaseToolActivity {
    @ViewInject(R.id.et_name)
    private EditText phone;

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

    }

    @Event(R.id.bt_next)
    private void nextClick(View v) {
        if (StringUtils.isEmpty(CommUtils.textToString(phone))) {
            Utils.makeToast(this, "请输入手机号");
        } else if (StringUtils.notPhone(CommUtils.textToString(phone))) {
            Utils.makeToast(this, "手机号格式不正确");
        } else {
            Intent intent = new Intent(this, ForgetNext.class);
            intent.putExtra(Constant.NAME, CommUtils.textToString(phone));
            startActivity(intent);
        }
    }

}
