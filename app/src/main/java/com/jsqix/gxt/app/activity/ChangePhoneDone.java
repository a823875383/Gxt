package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.jsqix.gxt.app.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_forget_done)
public class ChangePhoneDone extends BaseToolActivity {
    @ViewInject(R.id.et_phone)
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.title_change_phone));

    }

    @Override
    protected void initView() {
        phone.setHint(getString(R.string.new_phone));
    }
}
