package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.BankManageAdapter;
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
import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * 我的银行卡
 */
@ContentView(R.layout.activity_bankcard_added)
public class BankcardAdded extends BaseToolActivity implements HttpGet.InterfaceHttpGet, BankManageAdapter.bankOperaListener {
    @ViewInject(R.id.listView)
    private ListView listView;

    private List<BankListResult.ObjBean> data = new ArrayList<>();
    private BankManageAdapter adapter;

    final static int BANK_LIST = 0x0001, BANK_DELETE = 0x0010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.banks));
    }

    @Override
    protected void initView() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_bank_add);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mRight.setCompoundDrawables(drawable, null, null, null);

        adapter = new BankManageAdapter(this, R.layout.item_bank_added, data);
        adapter.setOperaListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankList();
    }

    @Event(R.id.tv_right)
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
        get.setResultCode(BANK_LIST);
        get.execute(RequestIP.BACK_LISTS);
    }

    /**
     * 删除银行卡
     */
    private void deleteBank(int cardId) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        paras.put("cardId", cardId);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(BANK_DELETE);
        get.execute(RequestIP.DELETE_CARD);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case BANK_LIST:
                dataResult(result);
                break;
            case BANK_DELETE:
                deleteResult(result);
                break;
        }
    }

    private void deleteResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                getBankList();
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void dataResult(String result) {
        BankListResult listResult = new Gson().fromJson(result, BankListResult.class);
        if (listResult != null) {
            if (listResult.getCode().equals("000")) {
                data.clear();
                data.addAll(listResult.getObj());
                adapter.notifyDataSetChanged();
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
    public void bankDelete(int cardId) {
        deleteBank(cardId);
    }
}
