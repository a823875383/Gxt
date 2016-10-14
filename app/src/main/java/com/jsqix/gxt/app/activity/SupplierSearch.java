package com.jsqix.gxt.app.activity;

import android.os.Handler;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.GoodsManageAdapter;
import com.jsqix.gxt.app.obj.MerchandiseManageResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.GoodsDialogUtils;
import com.jsqix.gxt.app.utils.GoodsOpUtils;
import com.jsqix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;

/**
 * 商品管理搜索
 */
public class SupplierSearch extends MerchandiseSearch implements GoodsDialogUtils.MerchandiseOpListener, GoodsOpUtils.DataResultListener, HttpGet.InterfaceHttpGet {
    private int pageNum = 1;
    private boolean hasNext = true;

    final static int GOODS_LIST = 0x0001;

    @Override
    void search(String key) {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("keyword", key);
        unParas.put("pageNum", pageNum);
        unParas.put("pageSize", Constant.PAGE_SIZE);
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(GOODS_LIST);
        get.execute(RequestIP.PAGE_GOODS);
    }

    @Override
    protected void initView() {
        super.initView();
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new GoodsManageAdapter(this, R.layout.item_goods_manage, data);
        adapter.setOpListener(this);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(this);

        opUtils = new GoodsOpUtils(this);
        opUtils.setResultListener(this);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case GOODS_LIST:
                dataResult(result);
                break;

        }
    }

    private void dataResult(String result) {
        MerchandiseManageResult manageResult = new Gson().fromJson(result, MerchandiseManageResult.class);
        if (manageResult != null) {
            if (manageResult.getCode().equals("000")) {
                if (pageNum == 1) {
                    data.clear();
                }
                hasNext = manageResult.getObj().isHas_next_page();
                data.addAll(manageResult.getObj().getItem_list());
                adapter.notifyDataSetChanged();

            } else {
                Utils.makeToast(this, manageResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
        new Handler().postDelayed(() -> {
            refreshListView.onRefreshComplete();
        }, 500);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pageNum = 1;
        search(key);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (hasNext) {
            pageNum++;
            search(key);
        } else {
            new Handler().postDelayed(() -> {
                refreshListView.onRefreshComplete();
                Utils.makeToast(this, getString(R.string.no_more_data));
            }, 1000);
        }
    }

    /**
     * 上架、下架商品
     *
     * @param goodsId
     * @param status
     */
    @Override
    public void updateStatus(int goodsId, int status) {
        opUtils.updateStatus(goodsId, status);
    }

    /**
     * 更改商品库存、订购量
     *
     * @param productId
     * @param stock
     * @param moq
     */
    @Override
    public void updateGoods(int productId, int stock, int moq) {
        opUtils.updateGoods(productId, stock, moq);
    }

    @Override
    public void refreshData() {
        pageNum = 1;
        search(key);
        adapter.dismissDialog();
    }
}
