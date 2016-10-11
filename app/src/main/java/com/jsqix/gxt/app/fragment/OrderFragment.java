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
import com.jsqix.utils.StringUtils;
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
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 采购商每项订单页面
 */
@ContentView(R.layout.fragment_order)
public class OrderFragment extends BaseFragment implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<ListView>, OrderListAdapter.PurchaseListener {
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    private int type = 0;
    private int pageNum = 1;
    private boolean hasNext = true;


    private List<OrderListResult.ObjBean.ItemListBean> data = new ArrayList<>();
    private OrderListAdapter adapter;

    final static int ORDER_LIST = 0x0001, ORDER_OP = 0x0010;

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

    /**
     * 订单操作
     *
     * @param type        操作事项 2： 删除订单 3：退款 4：退货 5：填写物流信息 6：确认收货
     * @param orderId     主订单id
     * @param detailId    订单详情id
     * @param expressType 快递公司类型
     * @param expressNo   快递单号
     * @param reason      退货原因
     * @param dec         退货理由
     */
    private void orderOperate(int type, int orderId, int detailId, int expressType, String expressNo, String reason, String dec) {
        Map<String, Object> unParas = new HashMap<>();
        if (orderId != -1) {
            unParas.put("orderId", orderId);
        }
        if (detailId != -1) {
            unParas.put("detailId", detailId);
        }
        if (expressType != -1) {
            unParas.put("expressType", expressType);
        }
        if (!StringUtils.isEmpty(expressNo)) {
            unParas.put("expressNo", expressNo);
        }
        if (!StringUtils.isEmpty(reason)) {
            unParas.put("reason", reason);
        }
        if (!StringUtils.isEmpty(dec)) {
            unParas.put("dec", dec);
        }
        Map<String, Object> paras = new HashMap<>();
        paras.put("type", type);
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_OP);
        get.execute(RequestIP.UPT_BUYER_ORDER);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case ORDER_LIST:
                orderListResult(result);
                break;
            case ORDER_OP:
                orderOpResult(result);
                break;
        }
    }

    private void orderOpResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(mContext, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                pageNum = 1;
                getOrdertList();
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
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
    public void deleOrder(OrderListResult.ObjBean.ItemListBean listBean) {
        orderOperate(2, listBean.getId(), -1, -1, "", "", "");//删除订单
    }

    @Override
    public void logistics(OrderListResult.ObjBean.ItemListBean listBean, int expressType, String expressNo, int position) {
        orderOperate(5, -1, listBean.getOrder_list().get(position).getDetail_id(), expressType, expressNo, "", "");//填写物流信息
    }

    @Override
    public void receiptOrder(OrderListResult.ObjBean.ItemListBean listBean, int position) {
        orderOperate(6, listBean.getId(), -1, -1, "", "", "");//确认收货

    }
}
