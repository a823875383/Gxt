package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.SureOrderAdapter;
import com.jsqix.gxt.app.obj.AddressObj;
import com.jsqix.gxt.app.obj.OrderSureResult;
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

@ContentView(R.layout.activity_order_submit)
public class OrderSubmit extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.tv_no_address)
    private TextView noAddress;
    @ViewInject(R.id.lin_default_address)
    private LinearLayout defaultAddress;
    @ViewInject(R.id.lin_address)
    private LinearLayout layoutAddress;
    @ViewInject(R.id.tv_name)
    private TextView addressName;
    @ViewInject(R.id.tv_phone)
    private TextView addressPhone;
    @ViewInject(R.id.tv_address)
    private TextView addressInfo;

    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.tv_goods_num)
    private TextView goodsNum;
    @ViewInject(R.id.tv_order_price)
    private TextView orderPrice;
    @ViewInject(R.id.tv_total)
    private TextView orderTotal;

    private List<OrderSureResult.ObjBean.ProductlistBean> data = new ArrayList<>();
    private SureOrderAdapter adapter;

    private String productListJson;
    private int buyType, addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.order_confirm));
        mTitle.setTextColor(Color.WHITE);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_back_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBack.setCompoundDrawables(drawable, null, null, null);
        titleBase.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void initView() {
        adapter = new SureOrderAdapter(this, R.layout.layout_order, data);
        listView.setAdapter(adapter);
        sureOrder();
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        productListJson = getIntent().getStringExtra(Constant.DATA);
        buyType = getIntent().getIntExtra(Constant.BUY_TYPE, 0);
    }

    @Event(R.id.bt_submit)
    private void submitClick(View v) {
        startActivity(new Intent(this, OrderPay.class));
    }

    @Event(R.id.lin_address)
    private void addressClick(View v) {
        Intent intent = new Intent(new Intent(this, AddressChoose.class));
        intent.putExtra(Constant.ID, addressId);
        startActivityForResult(intent, 100);
    }

    /**
     * 确认订单
     */
    private void sureOrder() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("productListJson", productListJson);
        paras.put("buyType", buyType);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.SURE_ORDER);
    }


    @Override
    public void getCallback(int resultCode, String result) {
        OrderSureResult sureResult = new Gson().fromJson(result, OrderSureResult.class);
        if (sureResult != null) {
            if (sureResult.getCode().equals("000")) {
                AddressObj addressBean = sureResult.getObj().getAddress();
                if (addressBean != null) {
                    noAddress.setVisibility(View.GONE);
                    defaultAddress.setVisibility(View.VISIBLE);
                    addressId = addressBean.getId();
                    addressName.setText(addressBean.getConsignee());
                    addressPhone.setText(addressBean.getPhone());
                    addressInfo.setText(addressBean.getPro_name() + addressBean.getCity_name() + addressBean.getContry_name() + addressBean.getStreet_address());

                } else {
                    noAddress.setVisibility(View.VISIBLE);
                    defaultAddress.setVisibility(View.GONE);
                }
                data.clear();
                data.addAll(sureResult.getObj().getProductlist());
                adapter.notifyDataSetChanged();

                goodsNum.setText(sureResult.getObj().getCounts() + "");
                orderPrice.setText(sureResult.getObj().getTotal_amt() + "");
                orderTotal.setText(getString(R.string.rmb) + sureResult.getObj().getTotal_amt());
            } else {
                Utils.makeToast(this, sureResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            AddressObj addressObj = (AddressObj) data.getSerializableExtra(Constant.DATA);
            noAddress.setVisibility(View.GONE);
            defaultAddress.setVisibility(View.VISIBLE);
            addressId = addressObj.getId();
            addressName.setText(addressObj.getConsignee());
            addressPhone.setText(addressObj.getPhone());
            addressInfo.setText(addressObj.getPro_name() + addressObj.getCity_name() + addressObj.getContry_name() + addressObj.getStreet_address());
        }
    }
}
