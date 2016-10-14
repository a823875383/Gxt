package com.jsqix.gxt.app.fragment;


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

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 采购商每项订单页面
 */
@ContentView(R.layout.fragment_order)
public class OrderFragment extends BaseFragment implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<ListView>, OrderDialogUtils.PurchaseListener, OrderOpUtils.DataResultListener {
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    private int type = 0;
    private int pageNum = 1;
    private boolean hasNext = true;


    private List<OrderObj> data = new ArrayList<>();
    private OrderListAdapter adapter;
    private OrderOpUtils opUtils;

    final static int ORDER_LIST = 0x0001;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        refreshListView.setHeaderLayout(new RefreshHeader(mContext));
        refreshListView.setFooterLayout(new RefreshFooter(mContext));

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new OrderListAdapter(mContext, R.layout.item_order, data);
        adapter.setOrderType(type);
        adapter.setPurchaseListener(this);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(this);
//        refreshListView.setEmptyView();

        opUtils = new OrderOpUtils(mContext);
        opUtils.setResultListener(this);
    }

    @Override
    protected void getArgument() {
        if (getArguments() != null) {
            type = getArguments().getInt(Constant.ORDER_TYPE, 0);
        }
    }

    @Override
    public void onResume() {
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
        paras.put("userId", ACache.get(mContext).getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_LIST);
        get.execute(RequestIP.ORDER_BUYER_LIST);

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
                Utils.makeToast(mContext, listResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
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
                Utils.makeToast(mContext, getString(R.string.no_more_data));
            }, 1000);
        }
    }

    @Override
    public void deleOrder(OrderObj listBean) {
        opUtils.orderBuyerOperate(2, listBean.getId(), -1, -1, "", "", "");//删除订单
    }

    @Override
    public void logistics(OrderObj listBean, int expressType, String expressNo, int position) {
        opUtils.orderBuyerOperate(5, -1, listBean.getOrder_list().get(position).getDetail_id(), expressType, expressNo, "", "");//填写物流信息
    }

    @Override
    public void receiptOrder(OrderObj listBean, int position) {
        opUtils.orderBuyerOperate(6, listBean.getId(), -1, -1, "", "", "");//确认收货
    }

    @Override
    public void refreshData() {
        pageNum = 1;
        getOrdertList();
    }
}
