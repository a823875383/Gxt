package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

@ContentView(R.layout.activity_city_select)
public class CitySelect extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.cityType)
    private EditText cityType;
    @ViewInject(R.id.parentID)
    private EditText parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText("选择城市");
    }

    @Override
    protected void initView() {
    }

    @Event(R.id.bt_confirm)
    private void searchClick(View v) {
        getData();
    }

    private void getData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("cityType", CommUtils.textToString(cityType));
        paras.put("timeStamp", System.currentTimeMillis());
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("userId", aCache.getAsString(Constant.U_ID));
        unParas.put("parentId", CommUtils.textToString(parentId));
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.PRO_LIST);
    }

    @Override
    public void getCallback(int resultCode, String result) {

    }
}
