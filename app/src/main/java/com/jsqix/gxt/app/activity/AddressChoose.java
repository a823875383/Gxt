package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.AddressChooseAdapter;
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

@ContentView(R.layout.activity_address_manage)
public class AddressChoose extends BaseToolActivity implements HttpGet.InterfaceHttpGet, AddressChooseAdapter.OnSelectListner {
    @ViewInject(R.id.lin_data)
    private LinearLayout dataLayout;
    @ViewInject(R.id.lin_no_data)
    private LinearLayout noDataLayout;
    @ViewInject(R.id.listView)
    private ListView listView;
    private List<AddressObj> data = new ArrayList<>();
    private AddressChooseAdapter adapter;

    private int addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.address_choose));
    }

    @Override
    protected void initView() {
        mRight.setText(getString(R.string.manage));
        mRight.setTextColor(getResources().getColor(R.color.green));

        adapter = new AddressChooseAdapter(this, R.layout.item_address_choose, data);
        listView.setAdapter(adapter);
        adapter.setOnSelectListner(this);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        addressId = getIntent().getIntExtra(Constant.ID, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    @Event(R.id.tv_right)
    private void manaClick(View v) {
        startActivity(new Intent(this, AddressManage.class));
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
        get.execute(RequestIP.ADDRESS_LIST);
    }


    @Override
    public void getCallback(int resultCode, String result) {
        AddressListResult addressResult = new Gson().fromJson(result, AddressListResult.class);
        if (addressResult != null) {
            if (addressResult.getCode().equals("000")) {
                data.clear();
                data.addAll(addressResult.getObj());
                adapter.notifyDataSetChanged();
                if (data.size() > 0) {
                    dataLayout.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getId() == addressId)
                            adapter.setSelectPosition(i);
                    }

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

    @Override
    public void onSelect(int position) {
        Intent intent = getIntent();
        intent.putExtra(Constant.DATA, data.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}
