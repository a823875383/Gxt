package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.BankChooseAdapter;
import com.jsqix.gxt.app.obj.BankListResult;
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

/**
 * 选择银行卡
 */
@ContentView(R.layout.activity_bankcrad_choose)
public class BankcradChoose extends BaseToolActivity implements HttpGet.InterfaceHttpGet, BankChooseAdapter.OnSelectListener {
    @ViewInject(R.id.listView)
    private ListView listView;

    private List<BankListResult.ObjBean> data = new ArrayList<>();
    private BankChooseAdapter adapter;

    int bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.bank_select));
    }

    @Override
    protected void initView() {
        adapter = new BankChooseAdapter(this, R.layout.item_bank_choice, data);
        adapter.setOnSelectListner(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        bankId = getIntent().getIntExtra(Constant.ID, 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankList();
    }

    @Event(R.id.tv_add_bank)
    private void addBankClick(View v) {
        startActivity(new Intent(this, BankcardAdd.class));

    }

    /**
     * 银行卡列表
     */
    private void getBankList() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.BACK_LISTS);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        BankListResult listResult = new Gson().fromJson(result, BankListResult.class);
        if (listResult != null) {
            if (listResult.getCode().equals("000")) {
                data.clear();
                data.addAll(listResult.getObj());
                adapter.notifyDataSetChanged();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getId() == bankId)
                        adapter.setSelectPosition(i);
                }
            } else {
                Utils.makeToast(this, listResult.getMsg());
                if (listResult.getCode().equals("004")) {
                    data.clear();
                    adapter.notifyDataSetChanged();
                }
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
