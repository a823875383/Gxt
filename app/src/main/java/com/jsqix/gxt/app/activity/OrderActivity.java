package com.jsqix.gxt.app.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.OrderListAdapter;
import com.jsqix.gxt.app.obj.OrderListResult;
import com.jsqix.gxt.app.obj.OrderObj;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.OrderDialogUtils;
import com.jsqix.gxt.app.utils.OrderOpUtils;
import com.jsqix.utils.ACache;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
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

/**
 * 供应商订单页面
 */
@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseToolActivity implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<ListView>, OrderDialogUtils.SupplierListener, OrderOpUtils.DataResultListener {
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    private String title;
    private int type = 0;
    private int pageNum = 1;
    private boolean hasNext = true;

    final static int ORDER_LIST = 0x0001;

    private List<OrderObj> data = new ArrayList<>();
    private OrderListAdapter adapter;
    private OrderOpUtils opUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(title);
        mTitle.setTextColor(Color.WHITE);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_back_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mBack.setCompoundDrawables(drawable, null, null, null);
        titleBase.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void initView() {
        refreshListView.setHeaderLayout(new RefreshHeader(this));
        refreshListView.setFooterLayout(new RefreshFooter(this));

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new OrderListAdapter(this, R.layout.item_order, data);
        adapter.setOrderType(type);
        adapter.setMerchantType(1);
        adapter.setSupplierListener(this);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(this);

        opUtils = new OrderOpUtils(this);
        opUtils.setResultListener(this);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        title = getIntent().getExtras().getString(Constant.TITLE, "");
        type = getIntent().getExtras().getInt(Constant.ORDER_TYPE, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrdertList();
    }

    /**
     * 获取订单列表
     */
    private void getOrdertList() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("pageNum", pageNum);
        unParas.put("pageSize", Constant.PAGE_SIZE);
        Map<String, Object> paras = new HashMap<>();
        paras.put("type", type);
        paras.put("userId", ACache.get(this).getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_LIST);
        get.execute(RequestIP.ORDER_SELLER_LIST);

    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case ORDER_LIST:
                orderListResult(result);
                break;
        }
    }

    private void orderListResult(String result) {
        OrderListResult listResult = new Gson().fromJson(result, OrderListResult.class);
        if (listResult != null) {
            if (listResult.getCode().equals("000")) {
                if (pageNum == 1) {
                    data.clear();
                }
                hasNext = listResult.getObj().isHas_next_page();
                data.addAll(listResult.getObj().getItem_list());
                adapter.notifyDataSetChanged();
            } else {
                Utils.makeToast(this, listResult.getMsg());
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
        getOrdertList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (hasNext) {
            pageNum++;
            getOrdertList();
        } else {
            new Handler().postDelayed(() -> {
                refreshListView.onRefreshComplete();
                Utils.makeToast(this, getString(R.string.no_more_data));
            }, 1000);
        }
    }


    @Override
    public void agreeRefund(OrderObj listBean, int position) {
        opUtils.orderSellerOperate(2, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", "");//确认退款

    }

    @Override
    public void agreeReturn(OrderObj listBean, int position) {
        opUtils.orderSellerOperate(3, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", "");//同意退货
    }

    @Override
    public void disagreeReturn(OrderObj listBean, String reason, int position) {
        opUtils.orderSellerOperate(4, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", reason);//不同意退货
    }

    @Override
    public void logistics(OrderObj listBean, int expressType, String expressNo, int position) {
        opUtils.orderSellerOperate(1, listBean.getId(), -1, expressType, expressNo, "");//发货
    }

    @Override
    public void receiptOrder(OrderObj listBean, int position) {
        opUtils.orderSellerOperate(5, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", "");//确认收货
    }

    @Override
    public void refreshData() {
        pageNum = 1;
        getOrdertList();
    }
}
