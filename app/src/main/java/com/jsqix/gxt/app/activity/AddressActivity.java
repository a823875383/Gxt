package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.AddressObj;
import com.jsqix.gxt.app.obj.AddressResult;
import com.jsqix.gxt.app.obj.CityObj;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

@ContentView(R.layout.activity_address)
public class AddressActivity extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.et_name)
    private EditText addressName;
    @ViewInject(R.id.et_phone)
    private EditText addressPhone;
    @ViewInject(R.id.et_address)
    private EditText addressStreet;
    @ViewInject(R.id.cb_choose)
    private CheckBox addressDefalut;
    @ViewInject(R.id.lin_choose)
    private LinearLayout addressChoose;
    @ViewInject(R.id.lin_delete)
    private LinearLayout addressDelete;
    @ViewInject(R.id.tv_area)
    private TextView addressArea;

    private String title;
    private AddressObj addressBean;
    private int addressId = 0, provinceId, cityId, countyId;
    private OptionsPickerView<String> areaPicker;

    final static int ADDR_QUERY = 0x0001, ADDR_ADD = 0x0010, ADDR_MODIFY = 0x0011, ADDR_DELETE = 0x0100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(title);

    }

    @Override
    protected void initView() {
//        areaPicker = new OptionsPickerView<>(this);
//        final ArrayList<String> pros = CityUtils.getInstance(this).getPro();
//        final ArrayList<ArrayList<String>> cities = CityUtils.getInstance(this).getCity();
//        final ArrayList<ArrayList<ArrayList<String>>> areas = CityUtils.getInstance(this).getArea();
//        areaPicker.setPicker(pros, cities, areas, true);
//        areaPicker.setCyclic(false);
//        int option1 = pros.size() / 2;
//        int option2 = cities.get(option1).size() / 2;
//        int option3 = areas.get(option1).get(option2).size() / 2;
//        areaPicker.setSelectOptions(option1, option2, option3);
//        areaPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                addressArea.setCompoundDrawables(null, null, drawable, null);
//                addressArea.setText(pros.get(options1) + "-" + cities.get(options1).get(option2) + "-" + areas.get(options1).get(option2).get(options3));
//            }
//        });
        mRight.setText(getString(R.string.save));
        mRight.setTextColor(getResources().getColor(R.color.green));
        if (addressId != 0) {
            addressDelete.setVisibility(View.VISIBLE);
            addressChoose.setVisibility(View.GONE);
            getAddress();
        } else {
            addressDelete.setVisibility(View.GONE);
            addressChoose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        title = getIntent().getExtras().getString(Constant.TITLE, "");
//        addressBean = (AddressListResult.ObjBean) getIntent().getSerializableExtra(Constant.DATA);
        addressId = getIntent().getExtras().getInt(Constant.ID, 0);
    }

    private void initAddress() {
        addressName.setText(addressBean.getConsignee());
        addressPhone.setText(addressBean.getPhone());
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        addressArea.setCompoundDrawables(null, null, drawable, null);
        provinceId = addressBean.getProvince_id();
        cityId = addressBean.getCity_id();
        countyId = addressBean.getCounty_id();
        addressArea.setText(addressBean.getPro_name() + addressBean.getCity_name() + addressBean.getContry_name());
        addressStreet.setText(addressBean.getStreet_address());
        addressDefalut.setChecked(addressBean.getWhether_default() == 1 ? true : false);
        addressDelete.setVisibility(View.VISIBLE);
    }

    @Event(R.id.tv_area)
    private void areaClick(View v) {
        //areaPicker.show();
        startActivityForResult(new Intent(this, CitySelect.class), 100);
    }

    @Event(R.id.tv_right)
    private void saveClick(View v) {
        if (addressId != 0) {
            updateAddress();
        } else {
            addAddress();
        }
    }

    @Event(R.id.lin_delete)
    private void deleteClick(View v) {
        deleteAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            addressArea.setCompoundDrawables(null, null, drawable, null);
            CityObj proObj = (CityObj) data.getExtras().getSerializable("pro");
            CityObj cityObj = (CityObj) data.getExtras().getSerializable("city");
            CityObj areaObj = (CityObj) data.getExtras().getSerializable("area");
            addressArea.setText(proObj.getCity_name() + cityObj.getCity_name() + areaObj.getCity_name());
            provinceId = proObj.getCity_id();
            cityId = cityObj.getCity_id();
            countyId = areaObj.getCity_id();
        }
    }

    /**
     * 查询收货地址
     */
    private void getAddress() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("userId", aCache.getAsString(Constant.U_ID));
        Map<String, Object> paras = new HashMap<>();
        paras.put("addressId", addressId);
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.setResultCode(ADDR_QUERY);
        get.execute(RequestIP.QUERY_ADDRESS_BY_ID);
    }

    /**
     * 增加收货地址
     */
    private void addAddress() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("userId", aCache.getAsString(Constant.U_ID));
        Map<String, Object> paras = new HashMap<>();
        paras.put("consignee", CommUtils.textToString(addressName));
        paras.put("phone", CommUtils.textToString(addressPhone));
        paras.put("provinceId", provinceId);
        paras.put("cityId", cityId);
        paras.put("countyId", countyId);
        paras.put("streetAddress", CommUtils.textToString(addressStreet));
        paras.put("whetherDefault", addressDefalut.isChecked() ? 0 : 1);
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.setResultCode(ADDR_ADD);
        get.execute(RequestIP.ADD_ADDRESS);
    }

    /**
     * 修改收货地址
     */
    private void updateAddress() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("userId", aCache.getAsString(Constant.U_ID));
        Map<String, Object> paras = new HashMap<>();
        paras.put("id", addressId);
        paras.put("consignee", CommUtils.textToString(addressName));
        paras.put("phone", CommUtils.textToString(addressPhone));
        paras.put("provinceId", provinceId);
        paras.put("cityId", cityId);
        paras.put("countyId", countyId);
        paras.put("streetAddress", CommUtils.textToString(addressStreet));
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.setResultCode(ADDR_MODIFY);
        get.execute(RequestIP.UPDATE_ADDRESS);
    }

    private void deleteAddress() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("id", addressId);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.setResultCode(ADDR_DELETE);
        get.execute(RequestIP.DELETE_ADDRESS);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case ADDR_QUERY:
                dataResult(result);
                break;
            case ADDR_ADD:

            case ADDR_MODIFY:

            case ADDR_DELETE:
                BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                if (baseBean != null) {
                    if (baseBean.getCode().equals("000")) {
                        finish();
                    }
                    Utils.makeToast(this, baseBean.getMsg());
                } else {
                    Utils.makeToast(this, getString(R.string.network_timeout));
                }
                break;

        }
        loadingUtils.dismiss();
    }

    private void dataResult(String result) {
        AddressResult addressResult = new Gson().fromJson(result, AddressResult.class);
        if (addressResult != null) {
            if (addressResult.getCode().equals("000")) {
                addressBean = addressResult.getObj();
                initAddress();
            } else {
                Utils.makeToast(this, addressResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));

        }
    }
}
