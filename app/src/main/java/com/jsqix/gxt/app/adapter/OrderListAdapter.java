package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.OrderInfo;
import com.jsqix.gxt.app.activity.OrderPay;
import com.jsqix.gxt.app.activity.ReturnRequest;
import com.jsqix.gxt.app.obj.ExpressResult;
import com.jsqix.gxt.app.obj.OrderListResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.x;

import java.util.List;

import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * Created by dongqing on 2016/10/9.
 */

public class OrderListAdapter extends CommonAdapter<OrderListResult.ObjBean.ItemListBean> implements HttpGet.InterfaceHttpGet {
    private int orderType = 0;//订单类型
    private int merchantType = 0;//商家类型

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

    private PurchaseListener purchaseListener;
    private SupplierListener supplierListener;


    public OrderListAdapter(Context context, int layoutId, List<OrderListResult.ObjBean.ItemListBean> datas) {
        super(context, layoutId, datas);
        initDialog();
    }

    public void setPurchaseListener(PurchaseListener purchaseListener) {
        this.purchaseListener = purchaseListener;
    }

    public void setSupplierListener(SupplierListener supplierListener) {
        this.supplierListener = supplierListener;
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

    @Override
    protected void convert(ViewHolder viewHolder, OrderListResult.ObjBean.ItemListBean item, int position) {
        viewHolder.setVisible(R.id.lin_order_date, true);
        viewHolder.setVisible(R.id.tv_order_status, false);
        viewHolder.setText(R.id.tv_order_no, item.getId() + "");
        viewHolder.setText(R.id.tv_order_date, item.getAdd_time());
        LinearLayout layoutOrder = viewHolder.getView(R.id.lin_goods);
        if (merchantType == 0) {
            viewHolder.setVisible(R.id.lin_num, true);//采购商
        } else {
            viewHolder.setVisible(R.id.lin_num, false);//供应商
        }
        layoutOrder.removeAllViewsInLayout();
        viewHolder.setVisible(R.id.lin_op, false);
        viewHolder.setVisible(R.id.bt_op_01, false);
        viewHolder.setVisible(R.id.bt_op_02, false);
        int num = 0;
        double sum = 0;
        for (int i = 0; i < item.getOrder_list().size(); i++) {
            OrderListResult.ObjBean.ItemListBean.OrderListBean orderListBean = item.getOrder_list().get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_order, null);
            ImageView goods_image = (ImageView) view.findViewById(R.id.iv_goods_img);
            TextView goods_name = (TextView) view.findViewById(R.id.tv_goods_name);
            TextView goods_stock = (TextView) view.findViewById(R.id.tv_goods_stock);
            TextView goods_moq = (TextView) view.findViewById(R.id.tv_goods_moq);
            TextView goods_num = (TextView) view.findViewById(R.id.tv_order_num);
            TextView goods_price = (TextView) view.findViewById(R.id.tv_goods_price);
            TextView goods_status = (TextView) view.findViewById(R.id.tv_goods_status);
            Button btn_op1 = (Button) view.findViewById(R.id.bt_op_01);
            Button btn_op2 = (Button) view.findViewById(R.id.bt_op_02);
            RelativeLayout goods_op = (RelativeLayout) view.findViewById(R.id.lin_op);
            x.image().bind(goods_image, Constant.IMG_SERVICE + orderListBean.getGoods_image());
            goods_name.setText(orderListBean.getProduct_name());
            goods_stock.setText(orderListBean.getStock() + "");
            goods_moq.setText(orderListBean.getMin_num() + "");
            goods_num.setText("x" + orderListBean.getNum());
            goods_price.setText(mContext.getString(R.string.rmb) + orderListBean.getWholesale_price());
            goods_status.setVisibility(View.VISIBLE);
            goods_op.setVisibility(View.GONE);
            btn_op1.setVisibility(View.VISIBLE);
            btn_op2.setVisibility(View.GONE);

            if (item.getOrder_status() == 100401) {
                if (orderListBean.getReturn_status() == 0) {
                    if (merchantType == 0) {//采购商
                        goods_status.setText("待付款");
                        viewHolder.setVisible(R.id.lin_op, true);
                        viewHolder.setVisible(R.id.bt_op_01, true);
                        viewHolder.setVisible(R.id.bt_op_02, true);
                        viewHolder.setText(R.id.bt_op_01, "付款");
                        viewHolder.setText(R.id.bt_op_02, "删除");
                    }
                } else if (orderListBean.getReturn_status() == 100505) {
                    goods_status.setText("退款中");
                } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                    goods_status.setText("退货中");
                } else {
                    goods_status.setText("已退款");
                }
            } else if (item.getOrder_status() == 100402) {
                if (orderListBean.getReturn_status() == 0 || orderListBean.getReturn_status() == 100502) {
                    goods_status.setText("已付款");
                    if (merchantType == 0) {//采购商
                        goods_op.setVisibility(View.VISIBLE);
                        btn_op1.setText("退款");
                    } else {//供应商
                        if (orderListBean.getReturn_status() == 0) {
                            viewHolder.setVisible(R.id.lin_op, true);
                            viewHolder.setVisible(R.id.bt_op_01, true);
                            viewHolder.setVisible(R.id.bt_op_02, false);
                            viewHolder.setText(R.id.bt_op_01, "发货");
                        }
                    }
                } else if (orderListBean.getReturn_status() == 100505) {
                    goods_status.setText("退款中");
                } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                    goods_status.setText("退货中");
                } else {
                    goods_status.setText("已退款");
                }
            } else if (item.getOrder_status() == 100403) {
                if (orderListBean.getReturn_status() == 0 || orderListBean.getReturn_status() == 100502) {
                    goods_status.setText("已发货");
                } else if (orderListBean.getReturn_status() == 100505) {
                    goods_status.setText("退款中");
                } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                    goods_status.setText("退货中");
                } else {
                    goods_status.setText("已退款");
                }
            } else if (item.getOrder_status() == 100404) {
                if (orderListBean.getReturn_status() == 0 || orderListBean.getReturn_status() == 100502) {
                    goods_status.setText("交易成功");
                } else if (orderListBean.getReturn_status() == 100505) {
                    goods_status.setText("退款中");
                } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                    goods_status.setText("退货中");
                } else {
                    goods_status.setText("已退款");
                }
            } else if (item.getOrder_status() == 100405) {
                if (orderListBean.getReturn_status() == 0 || orderListBean.getReturn_status() == 100502) {
                    goods_status.setText("交易关闭");
                } else if (orderListBean.getReturn_status() == 100505) {
                    goods_status.setText("退款中");
                } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                    goods_status.setText("退货中");
                } else {
                    goods_status.setText("已退款");
                }
            } else if (item.getOrder_status() == 100406) {
                goods_status.setText("退款中");
            } else if (item.getOrder_status() == 100407) {
                goods_status.setText("已退款");
            }

            //采购商
            if (merchantType == 0) {
                if (item.getOrder_status() > 100402 && orderListBean.getReturn_status() == 0) {
                    goods_op.setVisibility(View.VISIBLE);
                    btn_op1.setText("退货");
                    if (item.getOrder_status() == 100403 && orderType != 3) {
                        viewHolder.setVisible(R.id.lin_op, true);
                        viewHolder.setVisible(R.id.bt_op_01, true);
                        viewHolder.setVisible(R.id.bt_op_02, false);
                        viewHolder.setText(R.id.bt_op_01, "确认收货");
                    }

                }
                if (orderListBean.getReturn_status() == 100503) {
                    btn_op1.setText("填写物流信息");
                    goods_op.setVisibility(View.VISIBLE);
                } else if (orderListBean.getDec_status() == 2 && orderListBean.getDay1() < 8) {
                    btn_op1.setText("查看卖家反馈");
                    goods_op.setVisibility(View.VISIBLE);
                }
            } else {
                //供应商
                if (orderListBean.getReturn_status() == 100501) {
                    btn_op1.setText("同意退货");
                    btn_op2.setText("不同意退货");
                    goods_op.setVisibility(View.VISIBLE);
                    btn_op2.setVisibility(View.VISIBLE);
                } else if (orderListBean.getReturn_status() == 100504) {
                    btn_op1.setText("确认收货");
                    goods_op.setVisibility(View.VISIBLE);
                } else if (orderListBean.getReturn_status() == 100505) {
                    btn_op1.setText("确认退款");
                    goods_op.setVisibility(View.VISIBLE);
                }
            }

            btn_op1.setOnClickListener(new InnerListner(item, i));
            btn_op2.setOnClickListener(new InnerListner(item, i));
            layoutOrder.addView(view);
            num += orderListBean.getNum();
            sum += orderListBean.getNum() * orderListBean.getWholesale_price();
        }
        viewHolder.setText(R.id.tv_goods_num, num + "");
        viewHolder.setText(R.id.tv_order_price, sum + "");

        viewHolder.setOnClickListener(R.id.bt_op_01, new OuterListner(item));
        viewHolder.setOnClickListener(R.id.bt_op_02, new OuterListner(item));

        viewHolder.getConvertView().setOnClickListener(v -> {
            if (merchantType == 0) {
                Intent intent = new Intent(mContext, OrderInfo.class);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 获取物流公司
     */
    private void getExpress() {
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

    class InnerListner implements View.OnClickListener {
        private OrderListResult.ObjBean.ItemListBean listBean;
        private int position;

        public InnerListner(OrderListResult.ObjBean.ItemListBean listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ReturnRequest.class);
            intent.putExtra(Constant.DATA, listBean);
            if (CommUtils.textToString((Button) v).equals("退款")) {
                mContext.startActivity(intent);

            } else if (CommUtils.textToString((Button) v).equals("退货")) {
                mContext.startActivity(intent);

            } else if (CommUtils.textToString((Button) v).equals("填写物流信息")) {
                logisticsTvTitle.setText(mContext.getString(R.string.logistics_info));
//                logisticsDialog.show();
                logisticsBtLeft.setOnClickListener(new DeliveryListener(listBean, position));
                getExpress();

            } else if (CommUtils.textToString((Button) v).equals("查看卖家反馈")) {
                purchaseAdvice.setText(listBean.getOrder_list().get(position).getDec());
                purchaseDialog.show();

            } else if (CommUtils.textToString((Button) v).equals("确认退款")) {
                confirmTvMsg.setText(mContext.getString(R.string.refund_or_agree));
                confirmBtLeft.setOnClickListener(new ConfirmListener(listBean, position));
                confirmDialog.show();

            } else if (CommUtils.textToString((Button) v).equals("同意退货")) {
                confirmTvMsg.setText(mContext.getString(R.string.return_or_agree));
                confirmBtLeft.setOnClickListener(new ConfirmListener(listBean, position));
                confirmDialog.show();

            } else if (CommUtils.textToString((Button) v).equals("不同意退货")) {
                supplierBtLeft.setOnClickListener(new DisagreeListener(listBean, position));
                supplierDialog.show();

            } else if (CommUtils.textToString((Button) v).equals("确认收货")) {
                confirmTvMsg.setText(mContext.getString(R.string.receive_or));
                confirmBtLeft.setOnClickListener(new ConfirmListener(listBean, position));
                confirmDialog.show();

            }
        }
    }

    class OuterListner implements View.OnClickListener {
        private OrderListResult.ObjBean.ItemListBean listBean;

        public OuterListner(OrderListResult.ObjBean.ItemListBean listBean) {
            this.listBean = listBean;
        }

        @Override
        public void onClick(View v) {
            if (CommUtils.textToString((Button) v).equals("付款")) {
                Intent intent = new Intent(mContext, OrderPay.class);
                intent.putExtra(Constant.ID, listBean.getId());
                mContext.startActivity(intent);

            } else if (CommUtils.textToString((Button) v).equals("删除")) {
                confirmTvMsg.setText(mContext.getString(R.string.delete_or_agree));
                confirmBtLeft.setOnClickListener(new ConfirmListener(listBean, -1));
                confirmDialog.show();

            } else if (CommUtils.textToString((Button) v).equals("发货")) {
                logisticsTvTitle.setText(mContext.getString(R.string.deliver));
//                logisticsDialog.show();
                logisticsBtLeft.setOnClickListener(new DeliveryListener(listBean, -1));
                getExpress();
            } else if (CommUtils.textToString((Button) v).equals("确认收货")) {
                confirmTvMsg.setText(mContext.getString(R.string.receive_or));
                confirmBtLeft.setOnClickListener(new ConfirmListener(listBean, -1));
                confirmDialog.show();

            }

        }
    }

    class ConfirmListener implements View.OnClickListener {
        private OrderListResult.ObjBean.ItemListBean listBean;
        private int position;

        public ConfirmListener(OrderListResult.ObjBean.ItemListBean listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (CommUtils.textToString(confirmTvMsg).equals(mContext.getString(R.string.refund_or_agree))) {
                if (supplierListener != null) {//同意退款
                    supplierListener.agreeRefund(listBean, position);
                }
            } else if (CommUtils.textToString(confirmTvMsg).equals(mContext.getString(R.string.return_or_agree))) {
                if (supplierListener != null) {//同意退货
                    supplierListener.agreeReturn(listBean, position);
                }
            } else if (CommUtils.textToString(confirmTvMsg).equals(mContext.getString(R.string.delete_or_agree))) {
                if (purchaseListener != null) {//删除订单
                    purchaseListener.deleOrder(listBean);
                }
            } else if (CommUtils.textToString(confirmTvMsg).equals(mContext.getString(R.string.receive_or))) {
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
            confirmDialog.dismiss();

        }
    }

    class DeliveryListener implements View.OnClickListener {
        private OrderListResult.ObjBean.ItemListBean listBean;
        private int position;

        public DeliveryListener(OrderListResult.ObjBean.ItemListBean listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(CommUtils.textToString(logisticsNumber))) {
                Utils.makeToast(mContext, "请填写物流单号");
            } else {
                if (CommUtils.textToString(logisticsTvTitle).equals(mContext.getString(R.string.deliver))) {
                    if (supplierListener != null) {//供应商发货
                        supplierListener.logistics(listBean, ((ExpressResult.ObjBean) logisticsCompany.getSelectedItem()).getLABELNO(), CommUtils.textToString(logisticsNumber), position);
                    }
                } else if (CommUtils.textToString(logisticsTvTitle).equals(mContext.getString(R.string.logistics_info))) {
                    if (purchaseListener != null) {//采购商退货物流信息
                        purchaseListener.logistics(listBean, ((ExpressResult.ObjBean) logisticsCompany.getSelectedItem()).getLABELNO(), CommUtils.textToString(logisticsNumber), position);
                    }
                }
            }
            logisticsDialog.dismiss();
        }
    }

    class DisagreeListener implements View.OnClickListener {
        private OrderListResult.ObjBean.ItemListBean listBean;
        private int position;

        public DisagreeListener(OrderListResult.ObjBean.ItemListBean listBean, int position) {
            this.listBean = listBean;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(CommUtils.textToString(supplierReason))) {
                Utils.makeToast(mContext, "请输入不同意退货理由");
            } else {
                if (supplierListener != null) {//供应商不同意退货
                    supplierListener.disagreeReturn(listBean, CommUtils.textToString(supplierReason), position);
                }
            }
            supplierDialog.dismiss();
        }
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public void setMerchantType(int merchantType) {
        this.merchantType = merchantType;
    }

    interface DoubleListener {
        //物流或发货
        void logistics(OrderListResult.ObjBean.ItemListBean listBean, int expressType, String expressNo, int position);

        //确认收货
        void receiptOrder(OrderListResult.ObjBean.ItemListBean listBean, int position);
    }

    //采购商操作订单
    public interface PurchaseListener extends DoubleListener {
        //删除订单
        void deleOrder(OrderListResult.ObjBean.ItemListBean listBean);
    }

    //供货商操作订单
    public interface SupplierListener extends DoubleListener {
        //确认退款
        void agreeRefund(OrderListResult.ObjBean.ItemListBean listBean, int position);

        //同意退货
        void agreeReturn(OrderListResult.ObjBean.ItemListBean listBean, int position);

        //不同意退货
        void disagreeReturn(OrderListResult.ObjBean.ItemListBean listBean, String reason, int position);

    }
}
