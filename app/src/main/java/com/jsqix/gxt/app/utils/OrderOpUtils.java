package com.jsqix.gxt.app.utils;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/13.
 */

public class OrderOpUtils implements HttpGet.InterfaceHttpGet {

    private BaseCompat mContext;

    private DataResultListener resultListener;

    final static int SELLER_OP = 0x666666, BUYER_OP = 0x888888;

    public OrderOpUtils(BaseCompat mContext) {
        this.mContext = mContext;
    }

    public void setResultListener(DataResultListener resultListener) {
        this.resultListener = resultListener;
    }

    /**
     * 卖家订单处理
     *
     * @param type        操作事项 1:发货 2：确认退货 3：同意退货 4：不同意退货 5：确认收货
     * @param orderId     主订单id
     * @param detailId    订单详情id
     * @param expressType 快递公司类型
     * @param expressNo   快递单号
     * @param note        不同意退货理由
     */
    public void orderSellerOperate(int type, int orderId, int detailId, int expressType, String expressNo, String note) {
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
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(SELLER_OP);
        get.execute(RequestIP.UPT_SELLER_ORDER);
    }

    /**
     * 买家订单处理
     *
     * @param type        操作事项 2： 删除订单 3：退款 4：退货 5：填写物流信息 6：确认收货
     * @param orderId     主订单id
     * @param detailId    订单详情id
     * @param expressType 快递公司类型
     * @param expressNo   快递单号
     * @param reason      退货原因
     * @param dec         退货理由
     */
    public void orderBuyerOperate(int type, int orderId, int detailId, int expressType, String expressNo, String reason, String dec) {
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
        get.setResultCode(BUYER_OP);
        get.execute(RequestIP.UPT_BUYER_ORDER);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case SELLER_OP:
            case BUYER_OP:
                BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
                if (baseBean != null) {
                    Utils.makeToast(mContext, baseBean.getMsg());
                    if (baseBean.getCode().equals("000")) {
                        if (resultListener != null) {
                            resultListener.refreshData();
                        }
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
