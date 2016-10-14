package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.GoodsManageAdapter;
import com.jsqix.gxt.app.obj.MerchandiseManageResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.GoodsDialogUtils;
import com.jsqix.gxt.app.utils.GoodsOpUtils;
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
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

@ContentView(R.layout.activity_supplier_merchandise)
public class SupplierMerchandise extends BaseToolActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, HttpGet.InterfaceHttpGet, GoodsDialogUtils.MerchandiseOpListener, GoodsOpUtils.DataResultListener {
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    private List<MerchandiseManageResult.ObjBean.ItemListBean> data = new ArrayList<>();
    private GoodsManageAdapter adapter;

    private GoodsOpUtils opUtils;

    private int pageNum = 1;
    private boolean hasNext = true;

    final static int GOODS_LIST = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.goods_manage));
        mTitle.setTextColor(Color.WHITE);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_back_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBack.setCompoundDrawables(drawable, null, null, null);
        titleBase.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void initView() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_search_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mRight.setCompoundDrawables(drawable, null, null, null);

        refreshListView.setHeaderLayout(new RefreshHeader(this));
        refreshListView.setFooterLayout(new RefreshFooter(this));

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new GoodsManageAdapter(this, R.layout.item_goods_manage, data);
        adapter.setOpListener(this);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(this);

        opUtils = new GoodsOpUtils(this);
        opUtils.setResultListener(this);

        getGoodsList();
    }

    /**
     * 获取商品
     */
    private void getGoodsList() {
        Map<String, Object> unParas = new HashMap<>();
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

    @Event(R.id.tv_right)
    private void searchClick(View v) {
        startActivity(new Intent(this, SupplierSearch.class));
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
        getGoodsList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (hasNext) {
            pageNum++;
            getGoodsList();
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
        getGoodsList();
        adapter.dismissDialog();
    }
}
