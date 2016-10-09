package com.jsqix.gxt.app.fragment;


import android.os.Handler;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.OrderListAdapter;
import com.jsqix.gxt.app.obj.OrderListResult;
import com.jsqix.gxt.app.utils.Constant;
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
public class OrderFragment extends BaseFragment implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<ListView> {
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    private int type = 0;
    private int pageNum = 1;
    private boolean hasNext = true;

    private List<OrderListResult.ObjBean.ItemListBean> data = new ArrayList<>();
    private OrderListAdapter adapter;

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
        refreshListView.setAdapter(adapter);
        refreshListView.setOnRefreshListener(this);
        getOrdertList();
    }

    @Override
    protected void getArgument() {
        if (getArguments() != null) {
            type = getArguments().getInt(Constant.ORDER_TYPE, 0);
        }
    }

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
        get.execute(RequestIP.ORDER_BUYER_LIST);

    }

    @Override
    public void getCallback(int resultCode, String result) {
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
}
