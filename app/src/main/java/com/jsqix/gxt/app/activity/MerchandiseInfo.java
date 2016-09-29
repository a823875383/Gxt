package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.GoodsDetailResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

@ContentView(R.layout.activity_merchandise_info)
public class MerchandiseInfo extends BaseCompat implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.title_bar)
    private RelativeLayout titleBar;

    @ViewInject(R.id.fragment_merchandise)
    private LinearLayout merchandise;
    @ViewInject(R.id.tv_goods_name)
    private TextView goodsName;
    @ViewInject(R.id.tv_goods_price)
    private TextView goodsPrice;
    @ViewInject(R.id.tv_goods_stock)
    private TextView goodsStock;
    @ViewInject(R.id.tv_goods_moq)
    private TextView goodsMOQ;
    @ViewInject(R.id.tv_spec)
    private TextView tvSpec;

    @ViewInject(R.id.fragment_mer_detail)
    private LinearLayout merDetail;
    @ViewInject(R.id.webview)
    private WebView webView;

    @ViewInject(R.id.bt_buy)
    private Button buy;
    @ViewInject(R.id.bt_cart)
    private Button cart;

    private CustomDialog specDialog;

    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) titleBar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
        titleBar.setLayoutParams(lp);

    }

    @Override
    protected void initView() {

        initSpecDialog();

        getProductDetail();

    }

    @Override
    protected void initVariable() {
        productId = getIntent().getExtras().getInt(Constant.ID);
    }

    private void initSpecDialog() {
        specDialog = new CustomDialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.view_merchandise_spec, null);
        specDialog.setView(view, Gravity.BOTTOM);
        specDialog.setParas(1, 0);
    }

    @Event(R.id.tv_left)
    private void backClick(View v) {
        finish();
    }

    @Event(R.id.bt_buy)
    private void buyClick(View v) {
        startActivity(new Intent(this, OrderSubmit.class));
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected boolean isShowNetOff() {
        return true;
    }

    @Override
    protected boolean isStatusWhite() {
        return true;
    }

    /**
     * 获取商品详情
     */
    private void getProductDetail() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("productId", productId);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.GET_PRODUCT_DETAIL);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        GoodsDetailResult detailResult = new Gson().fromJson(result, GoodsDetailResult.class);
        if (detailResult != null) {
            if (detailResult.getCode().equals("000")) {
                goodsName.setText(detailResult.getObj().getProduct_name());
                goodsPrice.setText(detailResult.getObj().getWholesale_price()+"");
                goodsStock.setText(detailResult.getObj().getStock()+"");
                goodsMOQ.setText(detailResult.getObj().getMin_num()+"");
                StringBuffer spec = new StringBuffer(detailResult.getObj().getTag_detail_name1());
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name2())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name2());
                }
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name3())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name3());
                }
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name4())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name4());
                }
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name5())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name5());
                }

                tvSpec.setText(spec.toString());
            } else {
                Utils.makeToast(this, detailResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
