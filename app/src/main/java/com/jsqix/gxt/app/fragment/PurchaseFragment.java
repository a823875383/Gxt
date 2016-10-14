package com.jsqix.gxt.app.fragment;


import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.DoubleMain;
import com.jsqix.gxt.app.activity.OrderSubmit;
import com.jsqix.gxt.app.activity.PurchaserMain;
import com.jsqix.gxt.app.adapter.CartListAdapter;
import com.jsqix.gxt.app.obj.CartListResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 采购单页面
 */
@ContentView(R.layout.fragment_purchase)
public class PurchaseFragment extends BaseFragment implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener<ListView>, CartListAdapter.SelectListener {
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;
    @ViewInject(R.id.tv_right)
    private TextView tvEdit;
    @ViewInject(R.id.lin_data)
    private LinearLayout dataLayout;
    @ViewInject(R.id.lin_no_data)
    private LinearLayout emptyLayout;
    @ViewInject(R.id.refreshListView)
    private PullToRefreshListView refreshListView;

    @ViewInject(R.id.cb_choose)
    private CheckBox choose;
    @ViewInject(R.id.lin_total)
    private LinearLayout totalLayout;
    @ViewInject(R.id.tv_total)
    private TextView totlaMoney;

    @ViewInject(R.id.bt_settle)
    private Button settleBt;
    @ViewInject(R.id.bt_delete)
    private Button deleteBt;

    private CustomDialog deleteDialog;

    private boolean isEdit = false;

    private List<CartListResult.ObjBean.ItemListBean> data = new ArrayList<>();
    private CartListAdapter adapter;
    final static int GET_CART = 0x0001, DELETE_CART = 0x0010;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) title_bar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        title_bar.setLayoutParams(lp);


        refreshListView.setHeaderLayout(new RefreshHeader(mContext));
        refreshListView.setFooterLayout(new RefreshFooter(mContext));
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshListView.setOnRefreshListener(this);
        adapter = new CartListAdapter(mContext, R.layout.item_cart, data);
        adapter.setSelectListener(this);
        refreshListView.setAdapter(adapter);

        settleBt.setText(getString(R.string.settlement).replace("x", 0 + ""));

        initDialog();
    }

    @Override
    protected void getArgument() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            getGoodsCart();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            getGoodsCart();
        }
    }

    private void initDialog() {
        deleteDialog = new CustomDialog(mContext);
        View deleteView = LayoutInflater.from(mContext).inflate(R.layout.view_order_refund, null);
        TextView tvMsg = (TextView) deleteView.findViewById(R.id.tv_msg);
        Button btLeft = (Button) deleteView.findViewById(R.id.bt_confirm);
        Button btRight = (Button) deleteView.findViewById(R.id.bt_cancel);
        tvMsg.setText("是否确定删除");
        btLeft.setOnClickListener(v -> {
            deleteCart();
        });
        btRight.setOnClickListener(v -> {
            deleteDialog.dismiss();
        });
        deleteDialog.setView(deleteView);
    }

    @Event(R.id.tv_right)
    private void rightClick(View v) {
        settleBt.setVisibility(View.GONE);
        deleteBt.setVisibility(View.GONE);
        totalLayout.setVisibility(View.GONE);
        if (CommUtils.textToString(tvEdit).equals(getString(R.string.edit))) {
            tvEdit.setText(getString(R.string.done));
            isEdit = true;
            deleteBt.setVisibility(View.VISIBLE);
        } else {
            tvEdit.setText(getString(R.string.edit));
            isEdit = false;
            settleBt.setVisibility(View.VISIBLE);
            totalLayout.setVisibility(View.VISIBLE);
            totalMoney();
        }
    }

    @Event(R.id.bt_delete)
    private void deleteClick(View v) {
        if (adapter.getSelectNum() == 0) {
            Utils.makeToast(mContext, "请至少选择一个商品");
        } else {
            deleteDialog.show();
        }

    }

    @Event(R.id.bt_settle)
    private void settleClick(View v) {
        if (adapter.getSelectNum() == 0) {
            Utils.makeToast(mContext, "请至少选择一个商品");
        } else {
            List<Map<String, Object>> list = new ArrayList<>();
            for (CartListResult.ObjBean.ItemListBean listBean : data) {
                if (listBean.isSelect()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("productId", listBean.getProduct_id());
                    map.put("cartNumber", listBean.getCart_number());
                    list.add(map);
                }
            }
            Intent intent = new Intent(mContext, OrderSubmit.class);
            intent.putExtra(Constant.BUY_TYPE, 1);
            intent.putExtra(Constant.DATA, new Gson().toJson(list));
            startActivity(intent);
        }
    }

    @Event(R.id.cb_choose)
    private void chooseClick(View v) {
        if (choose.isChecked()) {
            adapter.setAllSelect();
        } else {
            adapter.setAllUnselect();
        }
    }

    @Event(R.id.bt_home)
    private void homeClick(View v) {
        Intent intent = new Intent();
        if ((int) mContext.aCache.getAsObject(Constant.U_IDENTITY) == 0) {
            intent.setClass(mContext, PurchaserMain.class);
        } else {
            intent.setClass(mContext, DoubleMain.class);
        }
        startActivity(intent);
    }

    /**
     * 获取采购单列表
     */
    private void getGoodsCart() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        paras.put("pageNum", 1);
        paras.put("pageSize", 100);
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(GET_CART);
        get.execute(RequestIP.GOODS_CART);
    }

    /**
     * 删除采购单
     */
    private void deleteCart() {
        Map<String, Object> paras = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        for (CartListResult.ObjBean.ItemListBean listBean : data) {
            if (listBean.isSelect()) {
                sb.append(listBean.getGoods_id() + ",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        paras.put("goodsIds", sb.toString());
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(DELETE_CART);
        get.execute(RequestIP.DELETE_CART);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case GET_CART:
                dataResult(result);
                break;
            case DELETE_CART:
                BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                if (baseBean != null) {
                    Utils.makeToast(mContext, baseBean.getMsg());
                    if (baseBean.getCode().equals("000")) {
                        deleteDialog.dismiss();
                        getGoodsCart();
                    }
                } else {
                    Utils.makeToast(mContext, getString(R.string.network_timeout));
                }
                break;
        }

    }

    private void dataResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            if (baseBean.getCode().equals("000")) {
                CartListResult listResult = new Gson().fromJson(result, CartListResult.class);
                data.clear();
                data.addAll(listResult.getObj().getItem_list());
                adapter.notifyDataSetChanged();
                dataLayout.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
                tvEdit.setVisibility(View.VISIBLE);
                if (!isEdit) {
                    choose.setChecked(false);
                    totlaMoney.setText(getString(R.string.rmb) + "0.0");
                    settleBt.setText(getString(R.string.settlement).replace("x", 0 + ""));
                }
            } else {
                Utils.makeToast(mContext, baseBean.getMsg());
                dataLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.GONE);
                isEdit = false;
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
        if (refreshListView.isRefreshing()) {
            new Handler().postDelayed(() -> {
                refreshListView.onRefreshComplete();
            }, 500);
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        getGoodsCart();
    }

    @Override
    public void onSelect() {
        if (!isEdit) {
            totalMoney();
        }
        if (adapter.isAllSelect()) {
            choose.setChecked(true);
        } else {
            choose.setChecked(false);
        }
        settleBt.setText(getString(R.string.settlement).replace("x", adapter.getSelectNum() + ""));

    }

    private void totalMoney() {
        double sum = 0;
        for (CartListResult.ObjBean.ItemListBean listBean : data) {
            if (listBean.isSelect()) {
                sum += listBean.getCart_number() * listBean.getWholesale_price();
            }
        }
        totlaMoney.setText(getString(R.string.rmb) + sum);
    }
}
