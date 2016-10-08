package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.CityAdapter;
import com.jsqix.gxt.app.obj.CityObj;
import com.jsqix.gxt.app.obj.CityResult;
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

@ContentView(R.layout.activity_city_select)
public class CitySelect extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    private int cityType = 1;
    private String parentId = "0";
    @ViewInject(R.id.listView)
    private ListView listView;
    private List<CityObj> data = new ArrayList<>();
    private CityAdapter adapter;

    private CityObj proObject, cityObj, areaObj;

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
        adapter = new CityAdapter(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        getData();
    }

    @Override
    protected void initVariable() {
        super.initVariable();
//        cityType = getIntent().getExtras().getString(Constant.CITY_TYPE, "");
//        parentId = getIntent().getExtras().getString(Constant.PARENT_ID, "");
    }

    @Event(value = R.id.listView, type = AbsListView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CityObj obj = data.get(position);
        if (cityType == 1) {
            proObject = obj;
        } else if (cityType == 2) {
            cityObj = obj;
        } else if (cityType == 3) {
            areaObj = obj;
        }
        cityType++;
        parentId = obj.getCity_id() + "";
        if (cityType > 3) {
            Intent intent = getIntent();
            intent.putExtra("pro", proObject);
            intent.putExtra("city", cityObj);
            intent.putExtra("area", areaObj);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        getData();
    }

    private void getData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("cityType", cityType);
        paras.put("timeStamp", System.currentTimeMillis());
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("userId", aCache.getAsString(Constant.U_ID));
        unParas.put("parentId", parentId);
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.PRO_LIST);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        CityResult cityResult = new Gson().fromJson(result, CityResult.class);
        if (cityResult != null) {
            if (cityResult.getCode().equals("000")) {
                data.clear();
                data.addAll(cityResult.getObj());
                adapter.notifyDataSetChanged();
                listView.setSelection(0);
            } else {
                Utils.makeToast(this, cityResult.getMsg());
            }

        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }

    }
}
