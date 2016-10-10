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

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 供应商订单页面
 */
@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseToolActivity implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<ListView>, OrderListAdapter.SupplierListener {
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    private String title;
    private int type = 0;
    private int pageNum = 1;
    private boolean hasNext = true;

    final static int ORDER_LIST = 0x0001, ORDER_OP = 0x0010;

    private List<OrderListResult.ObjBean.ItemListBean> data = new ArrayList<>();
    private OrderListAdapter adapter;

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
        getOrdertList();
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        title = getIntent().getExtras().getString(Constant.TITLE, "");
        type = getIntent().getExtras().getInt(Constant.ORDER_TYPE, 0);
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

    /**
     * 订单操作
     *
     * @param type        操作事项 1:发货 2：确认退货 3：同意退货 4：不同意退货 5：确认收货
     * @param orderId     主订单id
     * @param detailId    订单详情id
     * @param expressType 快递公司类型
     * @param expressNo   快递单号
     * @param note        不同意退货理由
     */
    private void orderOperate(int type, int orderId, int detailId, int expressType, String expressNo, String note) {
        Map<String, Object> unParas = new HashMap<>();
        if (orderId != -1) {
            unParas.put("orderId", orderId);
        }
        if (detailId != -1) {
            unParas.put("deatilId", detailId);
        }
        if (expressType != -1) {
            unParas.put("expressType", expressType);
        }
        if (!StringUtils.isEmpty(expressNo)) {
            unParas.put("expressNo", expressNo);
        }
        if (!StringUtils.isEmpty(note)) {
            unParas.put("note", note);
        }
        Map<String, Object> paras = new HashMap<>();
        paras.put("type", type);
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_OP);
        get.execute(RequestIP.UPT_SELLER_ORDER);
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
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                pageNum = 1;
                getOrdertList();
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
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
    public void agreeRefund(OrderListResult.ObjBean.ItemListBean listBean, int position) {
        orderOperate(2, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", "");//确认退款

    }

    @Override
    public void agreeReturn(OrderListResult.ObjBean.ItemListBean listBean, int position) {
        orderOperate(3, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", "");//同意退货
    }

    @Override
    public void disagreeReturn(OrderListResult.ObjBean.ItemListBean listBean, String reason, int position) {
        orderOperate(4, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", reason);//不同意退货
    }

    @Override
    public void logistics(OrderListResult.ObjBean.ItemListBean listBean, int expressType, String expressNo, int position) {
        orderOperate(1, listBean.getId(), -1, expressType, expressNo, "");//发货
    }

    @Override
    public void receiptOrder(OrderListResult.ObjBean.ItemListBean listBean, int position) {
        orderOperate(5, -1, listBean.getOrder_list().get(position).getDetail_id(), -1, "", "");//确认收货
    }
}
