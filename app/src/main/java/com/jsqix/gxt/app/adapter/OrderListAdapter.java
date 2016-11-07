package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.OrderInfo;
import com.jsqix.gxt.app.obj.OrderObj;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.OrderDialogUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by dongqing on 2016/10/9.
 */

public class OrderListAdapter extends CommonAdapter<OrderObj> {
    private int orderType = 0;//订单类型

    private OrderDialogUtils dialogUtils;

    public OrderListAdapter(Context context, int layoutId, List<OrderObj> datas) {
        super(context, layoutId, datas);
        dialogUtils = new OrderDialogUtils(context);
    }

    public void setMerchantType(int merchantType) {
        dialogUtils.setMerchantType(merchantType);
    }

    public void setSupplierListener(OrderDialogUtils.SupplierListener supplierListener) {
        dialogUtils.setSupplierListener(supplierListener);
    }

    public void setPurchaseListener(OrderDialogUtils.PurchaseListener purchaseListener) {
        dialogUtils.setPurchaseListener(purchaseListener);
    }

    @Override
    protected void convert(ViewHolder viewHolder, OrderObj item, int position) {
        viewHolder.setVisible(R.id.lin_order_date, true);
        viewHolder.setVisible(R.id.tv_order_status, false);
        viewHolder.setText(R.id.tv_order_no, item.getId() + "");
        viewHolder.setText(R.id.tv_order_date, item.getAdd_time());
        LinearLayout layoutOrder = viewHolder.getView(R.id.lin_goods);
        if (dialogUtils.getMerchantType() == 0) {
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
            OrderObj.OrderListBean orderListBean = item.getOrder_list().get(i);
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
                    if (dialogUtils.getMerchantType() == 0) {//采购商
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
                    if (dialogUtils.getMerchantType() == 0) {//采购商
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
            if (dialogUtils.getMerchantType() == 0) {
                if (item.getOrder_status() > 100402 && orderListBean.getReturn_status() == 0 && item.getOrder_status() != 100405) {
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

            btn_op1.setOnClickListener(dialogUtils.new InnerListner(item, i));
            btn_op2.setOnClickListener(dialogUtils.new InnerListner(item, i));
            layoutOrder.addView(view);
            num += orderListBean.getNum();
            sum += orderListBean.getNum() * orderListBean.getWholesale_price();
        }
        viewHolder.setText(R.id.tv_goods_num, num + "");
        viewHolder.setText(R.id.tv_order_price, sum + "");

        viewHolder.setOnClickListener(R.id.bt_op_01, dialogUtils.new OuterListner(item));
        viewHolder.setOnClickListener(R.id.bt_op_02, dialogUtils.new OuterListner(item));

        viewHolder.getConvertView().setOnClickListener(v -> {
//            if (dialogUtils.getMerchantType() == 0) {
            Intent intent = new Intent(mContext, OrderInfo.class);
            intent.putExtra(Constant.ID, item.getId());
            if (orderType != 3) {
                intent.putExtra(Constant.ORDER_TYPE, 1);
            } else {
                intent.putExtra(Constant.ORDER_TYPE, 2);
            }
            if (dialogUtils.getMerchantType() == 0) {
                intent.putExtra(Constant.BUY_TYPE, 0);
            } else {
                intent.putExtra(Constant.BUY_TYPE, 1);
            }
            mContext.startActivity(intent);
//            }
        });
    }


    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

}
