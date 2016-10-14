package com.jsqix.gxt.app.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.OrderPay;
import com.jsqix.gxt.app.activity.ReturnRequest;
import com.jsqix.gxt.app.adapter.ExpressAdapter;
import com.jsqix.gxt.app.obj.ExpressResult;
import com.jsqix.gxt.app.obj.OrderObj;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * Created by dongqing on 2016/10/13.
 * 订单对话框处理类
 */

public class OrderDialogUtils implements HttpGet.InterfaceHttpGet {
    private CustomDialog confirmDialog;//确认对话框
    private TextView confirmTvMsg;
    private Button confirmBtLeft, confirmBtRight;

    private CustomDialog logisticsDialog;//物流对话框
    private ImageView logisticsClose;
    private TextView logisticsTvTitle;
    private Spinner logisticsCompany;
    private EditText logisticsNumber;
    private Button logisticsBtLeft, logisticsBtRight;

    private CustomDialog supplierDialog;//不同意反馈对话框
    private ImageView supplierClose;
    private EditText supplierReason;
    private Button supplierBtLeft, supplierBtRight;

    private CustomDialog purchaseDialog;//查看反馈对话框
    private ImageView purchaseClose;
    private TextView purchaseAdvice;

    private int merchantType = 0;//商家类型
    private Context mContext;
    private PurchaseListener purchaseListener;
    private SupplierListener supplierListener;

    public OrderDialogUtils(Context mContext) {
        this.mContext = mContext;
        initDialog();

    }

    private void initDialog() {
        confirmDialog = new CustomDialog(mContext);
        View confirmView = LayoutInflater.from(mContext).inflate(R.layout.view_order_refund, null);
        confirmTvMsg = (TextView) confirmView.findViewById(R.id.tv_msg);
        confirmBtLeft = (Button) confirmView.findViewById(R.id.bt_confirm);
        confirmBtRight = (Button) confirmView.findViewById(R.id.bt_cancel);
        confirmBtRight.setOnClickListener(v -> {
            confirmDialog.dismiss();
        });
        confirmDialog.setView(confirmView);

        logisticsDialog = new CustomDialog(mContext);
        View logisticsView = LayoutInflater.from(mContext).inflate(R.layout.view_order_delivery, null);
        logisticsClose = (ImageView) logisticsView.findViewById(R.id.iv_close);
        logisticsTvTitle = (TextView) logisticsView.findViewById(R.id.tv_title);
        logisticsCompany = (Spinner) logisticsView.findViewById(R.id.logistics_company);
        logisticsNumber = (EditText) logisticsView.findViewById(R.id.et_logistics_number);
        logisticsBtLeft = (Button) logisticsView.findViewById(R.id.bt_submit);
        logisticsBtRight = (Button) logisticsView.findViewById(R.id.bt_cancel);
        logisticsClose.setOnClickListener(v -> {
            logisticsDialog.dismiss();
        });
        logisticsBtRight.setOnClickListener(v -> {
            logisticsDialog.dismiss();
        });
        logisticsDialog.setView(logisticsView);

        supplierDialog = new CustomDialog(mContext);
        View supplierView = LayoutInflater.from(mContext).inflate(R.layout.view_order_disagree, null);
        supplierClose = (ImageView) supplierView.findViewById(R.id.iv_close);
        supplierReason = (EditText) supplierView.findViewById(R.id.et_reason);
        supplierBtLeft = (Button) supplierView.findViewById(R.id.bt_submit);
        supplierBtRight = (Button) supplierView.findViewById(R.id.bt_cancel);
        supplierClose.setOnClickListener(v -> {
            supplierDialog.dismiss();
        });
        supplierBtRight.setOnClickListener(v -> {
            supplierDialog.dismiss();
        });
        supplierDialog.setView(supplierView);

        purchaseDialog = new CustomDialog(mContext);
        View purchaseView = LayoutInflater.from(mContext).inflate(R.layout.view_order_advice, null);
        purchaseClose = (ImageView) purchaseView.findViewById(R.id.iv_close);
        purchaseAdvice = (TextView) purchaseView.findViewById(R.id.tv_advice);
        purchaseClose.setOnClickListener(v -> {
            purchaseDialog.dismiss();
        });
        purchaseDialog.setView(purchaseView);
    }


    public void setPurchaseListener(PurchaseListener purchaseListener) {
        this.purchaseListener = purchaseListener;
    }

    public void setSupplierListener(SupplierListener supplierListener) {
        this.supplierListener = supplierListener;
    }

    public class InnerListner implements View.OnClickListener {
        private OrderObj listBean;
        private int position;

        public InnerListner(OrderObj listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ReturnRequest.class);
            intent.putExtra(Constant.DATA, listBean.getOrder_list().get(position).getDetail_id());
            if (CommUtils.textToString((Button) v).equals("退款")) {
                intent.putExtra(Constant.ORDER_TYPE, 3);
                mContext.startActivity(intent);

            } else if (CommUtils.textToString((Button) v).equals("退货")) {
                intent.putExtra(Constant.ORDER_TYPE, 4);
                mContext.startActivity(intent);

            } else if (CommUtils.textToString((Button) v).equals("填写物流信息")) {
                getLogisticsTvTitle().setText(mContext.getString(R.string.logistics_info));
//                logisticsDialog.show();
                getLogisticsBtLeft().setOnClickListener(new DeliveryListener(listBean, position));
                getExpress();

            } else if (CommUtils.textToString((Button) v).equals("查看卖家反馈")) {
                getPurchaseAdvice().setText(listBean.getOrder_list().get(position).getDec());
                getPurchaseDialog().show();

            } else if (CommUtils.textToString((Button) v).equals("确认退款")) {
                getConfirmTvMsg().setText(mContext.getString(R.string.refund_or_agree));
                getConfirmBtLeft().setOnClickListener(new ConfirmListener(listBean, position));
                getConfirmDialog().show();

            } else if (CommUtils.textToString((Button) v).equals("同意退货")) {
                getConfirmTvMsg().setText(mContext.getString(R.string.return_or_agree));
                getConfirmBtLeft().setOnClickListener(new ConfirmListener(listBean, position));
                getConfirmDialog().show();

            } else if (CommUtils.textToString((Button) v).equals("不同意退货")) {
                getSupplierBtLeft().setOnClickListener(new DisagreeListener(listBean, position));
                getSupplierDialog().show();

            } else if (CommUtils.textToString((Button) v).equals("确认收货")) {
                getConfirmTvMsg().setText(mContext.getString(R.string.receive_or));
                getConfirmBtLeft().setOnClickListener(new ConfirmListener(listBean, position));
                getConfirmDialog().show();

            }
        }
    }

    public class OuterListner implements View.OnClickListener {
        private OrderObj listBean;

        public OuterListner(OrderObj listBean) {
            this.listBean = listBean;
        }

        @Override
        public void onClick(View v) {
            if (CommUtils.textToString((Button) v).equals("付款")) {
                Intent intent = new Intent(mContext, OrderPay.class);
                intent.putExtra(Constant.ID, listBean.getId());
                mContext.startActivity(intent);

            } else if (CommUtils.textToString((Button) v).equals("删除")) {
                getConfirmTvMsg().setText(mContext.getString(R.string.delete_or_agree));
                getConfirmBtLeft().setOnClickListener(new ConfirmListener(listBean, -1));
                getConfirmDialog().show();

            } else if (CommUtils.textToString((Button) v).equals("发货")) {
                getLogisticsTvTitle().setText(mContext.getString(R.string.deliver));
//                logisticsDialog.show();
                getLogisticsBtLeft().setOnClickListener(new DeliveryListener(listBean, -1));
                getExpress();
            } else if (CommUtils.textToString((Button) v).equals("确认收货")) {
                getConfirmTvMsg().setText(mContext.getString(R.string.receive_or));
                getConfirmBtLeft().setOnClickListener(new ConfirmListener(listBean, -1));
                getConfirmDialog().show();

            }

        }
    }

    class ConfirmListener implements View.OnClickListener {
        private OrderObj listBean;
        private int position;

        public ConfirmListener(OrderObj listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (CommUtils.textToString(getConfirmTvMsg()).equals(mContext.getString(R.string.refund_or_agree))) {
                if (supplierListener != null) {//同意退款
                    supplierListener.agreeRefund(listBean, position);
                }
            } else if (CommUtils.textToString(getConfirmTvMsg()).equals(mContext.getString(R.string.return_or_agree))) {
                if (supplierListener != null) {//同意退货
                    supplierListener.agreeReturn(listBean, position);
                }
            } else if (CommUtils.textToString(getConfirmTvMsg()).equals(mContext.getString(R.string.delete_or_agree))) {
                if (purchaseListener != null) {//删除订单
                    purchaseListener.deleOrder(listBean);
                }
            } else if (CommUtils.textToString(getConfirmTvMsg()).equals(mContext.getString(R.string.receive_or))) {
                if (merchantType == 0) {
                    if (purchaseListener != null) {//采购商确认收货
                        purchaseListener.receiptOrder(listBean, position);
                    }
                } else {
                    if (supplierListener != null) {//供应商确认收货
                        supplierListener.receiptOrder(listBean, position);
                    }
                }
            }
            getConfirmDialog().dismiss();

        }
    }

    class DeliveryListener implements View.OnClickListener {
        private OrderObj listBean;
        private int position;

        public DeliveryListener(OrderObj listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(CommUtils.textToString(getLogisticsNumber()))) {
                Utils.makeToast(mContext, "请填写物流单号");
            } else {
                if (CommUtils.textToString(getLogisticsTvTitle()).equals(mContext.getString(R.string.deliver))) {
                    if (supplierListener != null) {//供应商发货
                        supplierListener.logistics(listBean, ((ExpressResult.ObjBean) getLogisticsCompany().getSelectedItem()).getLABELNO(), CommUtils.textToString(getLogisticsNumber()), position);
                    }
                } else if (CommUtils.textToString(getLogisticsTvTitle()).equals(mContext.getString(R.string.logistics_info))) {
                    if (purchaseListener != null) {//采购商退货物流信息
                        purchaseListener.logistics(listBean, ((ExpressResult.ObjBean) getLogisticsCompany().getSelectedItem()).getLABELNO(), CommUtils.textToString(getLogisticsNumber()), position);
                    }
                }
            }
            getLogisticsDialog().dismiss();
        }
    }

    class DisagreeListener implements View.OnClickListener {
        private OrderObj listBean;
        private int position;

        public DisagreeListener(OrderObj listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(CommUtils.textToString(getSupplierReason()))) {
                Utils.makeToast(mContext, "请输入不同意退货理由");
            } else {
                if (supplierListener != null) {//供应商不同意退货
                    supplierListener.disagreeReturn(listBean, CommUtils.textToString(getSupplierReason()), position);
                }
            }
            getSupplierDialog().dismiss();
        }
    }

    /**
     * 获取物流公司
     */
    public void getExpress() {
        HttpGet get = new HttpGet(mContext, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.QUERY_EXPRESS);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        ExpressResult expressResult = new Gson().fromJson(result, ExpressResult.class);
        if (expressResult != null) {
            if (expressResult.getCode().equals("000")) {
                ExpressAdapter adapter = new ExpressAdapter(mContext, android.R.layout.simple_spinner_dropdown_item, expressResult.getObj());
                logisticsCompany.setAdapter(adapter);
                logisticsDialog.show();
            } else {
                Utils.makeToast(mContext, expressResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, mContext.getString(R.string.network_timeout));
        }
    }

    interface DoubleListener {
        //物流或发货
        void logistics(OrderObj listBean, int expressType, String expressNo, int position);

        //确认收货
        void receiptOrder(OrderObj listBean, int position);
    }

    //采购商操作订单
    public interface PurchaseListener extends DoubleListener {
        //删除订单
        void deleOrder(OrderObj listBean);
    }

    //供货商操作订单
    public interface SupplierListener extends DoubleListener {
        //确认退款
        void agreeRefund(OrderObj listBean, int position);

        //同意退货
        void agreeReturn(OrderObj listBean, int position);

        //不同意退货
        void disagreeReturn(OrderObj listBean, String reason, int position);

    }


    public CustomDialog getConfirmDialog() {
        return confirmDialog;
    }

    public TextView getConfirmTvMsg() {
        return confirmTvMsg;
    }

    public Button getConfirmBtLeft() {
        return confirmBtLeft;
    }

    public CustomDialog getLogisticsDialog() {
        return logisticsDialog;
    }

    public TextView getLogisticsTvTitle() {
        return logisticsTvTitle;
    }

    public Spinner getLogisticsCompany() {
        return logisticsCompany;
    }

    public EditText getLogisticsNumber() {
        return logisticsNumber;
    }

    public Button getLogisticsBtLeft() {
        return logisticsBtLeft;
    }

    public CustomDialog getSupplierDialog() {
        return supplierDialog;
    }

    public EditText getSupplierReason() {
        return supplierReason;
    }

    public Button getSupplierBtLeft() {
        return supplierBtLeft;
    }

    public CustomDialog getPurchaseDialog() {
        return purchaseDialog;
    }

    public TextView getPurchaseAdvice() {
        return purchaseAdvice;
    }

    public int getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(int merchantType) {
        this.merchantType = merchantType;
    }
}
