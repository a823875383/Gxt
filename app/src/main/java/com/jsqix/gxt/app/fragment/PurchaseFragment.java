package com.jsqix.gxt.app.fragment;


import android.content.Intent;
import android.os.Handler;
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
    }

    @Override
    protected void getArgument() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            getGoodsCart();
        }
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

    }

    @Event(R.id.bt_settle)
    private void settleClick(View v) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CartListResult.ObjBean.ItemListBean listBean : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("productId", listBean.getProduct_id());
            map.put("cartNumber", listBean.getCart_number());
            list.add(map);
        }
        Intent intent = new Intent(mContext, OrderSubmit.class);
        intent.putExtra(Constant.BUY_TYPE, 1);
        intent.putExtra(Constant.DATA, new Gson().toJson(list));
        startActivity(intent);

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

    @Override
    public void getCallback(int resultCode, String result) {

        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            if (baseBean.getCode().equals("000")) {
                CartListResult listResult = new Gson().fromJson(result, CartListResult.class);
                data.clear();
                data.addAll(listResult.getObj().getItem_list());
                adapter.notifyDataSetChanged();
                choose.setChecked(false);
                totlaMoney.setText(getString(R.string.rmb) + "0.0");
                settleBt.setText(getString(R.string.settlement).replace("x", 0 + ""));
            } else {
                Utils.makeToast(mContext, baseBean.getMsg());
                dataLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.GONE);
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
        new Handler().postDelayed(() -> {
            refreshListView.onRefreshComplete();
        }, 500);
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
