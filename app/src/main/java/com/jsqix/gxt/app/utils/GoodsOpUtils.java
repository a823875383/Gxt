package com.jsqix.gxt.app.utils;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/14.
 */

public class GoodsOpUtils implements HttpGet.InterfaceHttpGet {
    final static int GOODS_STATUS = 0x00010, GOODS_STOCK = 0x0011;

    private BaseCompat mContext;
    private DataResultListener resultListener;

    public GoodsOpUtils(BaseCompat mContext) {
        this.mContext = mContext;
    }

    public void setResultListener(DataResultListener resultListener) {
        this.resultListener = resultListener;
    }

    /**
     * 上架、下架商品
     *
     * @param goodsId
     * @param status
     */
    public void updateStatus(int goodsId, int status) {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("goodsId", goodsId);
        unParas.put("goodsStatus", status);
        unParas.put("timeStamp", System.currentTimeMillis());
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(GOODS_STATUS);
        get.execute(RequestIP.UPDATE_GOODS_STATUS);
    }

    /**
     * 更改商品库存、订购量
     *
     * @param productId
     * @param stock
     * @param moq
     */
    public void updateGoods(int productId, int stock, int moq) {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("productId", productId);
        unParas.put("minNum", moq);
        unParas.put("stock", stock);
        unParas.put("timeStamp", System.currentTimeMillis());
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(GOODS_STOCK);
        get.execute(RequestIP.UPDATE_PRODUCT_MINNUM_AND_STOCK);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {

            case GOODS_STATUS:

            case GOODS_STOCK:
                BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                if (baseBean != null) {
                    Utils.makeToast(mContext, baseBean.getMsg());
                    if (baseBean.getCode().equals("000")) {
                        if (resultListener != null) {
                            resultListener.refreshData();
                        }
                    } else {
                        Utils.makeToast(mContext, baseBean.getMsg());
                    }
                } else {
                    Utils.makeToast(mContext, mContext.getString(R.string.network_timeout));
                }
                break;
        }
    }

    public interface DataResultListener {
        void refreshData();
    }
}
