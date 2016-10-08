package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.AddressManageAdapter;
import com.jsqix.gxt.app.obj.AddressListResult;
import com.jsqix.gxt.app.obj.AddressObj;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;

@ContentView(R.layout.activity_address_manage)
public class AddressManage extends BaseToolActivity implements HttpGet.InterfaceHttpGet, AddressManageAdapter.UpdateDefaultListner {
    @ViewInject(R.id.lin_data)
    private LinearLayout dataLayout;
    @ViewInject(R.id.lin_no_data)
    private LinearLayout noDataLayout;
    @ViewInject(R.id.listView)
    private ListView listView;
    private List<AddressObj> data = new ArrayList<>();
    private AddressManageAdapter adapter;

    final static int ADDR_QUERY = 0x0001, ADDR_DEFALUT = 0x0011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.address_manage));
    }

    @Override
    protected void initView() {
        adapter = new AddressManageAdapter(this, R.layout.item_address_manage, data);
        adapter.setUpdateDefaultListner(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    @Event(value = {R.id.tv_add_address, R.id.lin_add})
    private void AddrClick(View v) {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra(Constant.TITLE, getString(R.string.title_add_address));
        startActivity(intent);
    }


    /**
     * 获取收货地址
     */
    private void getAddress() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("userId", aCache.getAsString(Constant.U_ID));
        Map<String, Object> Paras = new HashMap<>();
        Paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, unParas, Paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ADDR_QUERY);
        get.execute(RequestIP.ADDRESS_LIST);
    }

    /**
     * 修改默认地址
     */
    @Override
    public void updateDefault(int addressId, int whetherDefault) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("id", addressId);
        paras.put("whetherDefault", whetherDefault);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ADDR_DEFALUT);
        get.execute(RequestIP.UPDATE_DEFULT);
    }


    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case ADDR_QUERY:
                dataResult(result);
                break;
            case ADDR_DEFALUT:
                defaultResult(result);
                break;
        }
    }

    private void defaultResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            if (baseBean.getCode().equals("000")) {
                getAddress();
            }
            Utils.makeToast(this, baseBean.getMsg());
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void dataResult(String result) {
        AddressListResult addressResult = new Gson().fromJson(result, AddressListResult.class);
        if (addressResult != null) {
            if (addressResult.getCode().equals("000")) {
                data.clear();
                data.addAll(addressResult.getObj());
                adapter.notifyDataSetChanged();
                if (data.size() > 0) {
                    dataLayout.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                } else {
                    dataLayout.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            } else {
                Utils.makeToast(this, addressResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
