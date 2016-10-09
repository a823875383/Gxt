package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.OrderPay;
import com.jsqix.gxt.app.obj.OrderListResult;
import com.jsqix.gxt.app.utils.Constant;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.x;

import java.util.List;

import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * Created by dongqing on 2016/10/9.
 */

public class OrderListAdapter extends CommonAdapter<OrderListResult.ObjBean.ItemListBean> {
    private int orderType = 0;
    private CustomDialog dialog;

    private TextView tvMsg;
    private Button btLeft,btRight;

    public OrderListAdapter(Context context, int layoutId, List<OrderListResult.ObjBean.ItemListBean> datas) {
        super(context, layoutId, datas);
        initDialog();
    }

    private void initDialog() {
        dialog=new CustomDialog(mContext);
//        View view =LayoutInflater.from(mContext).inflate(R.layout.view_order_disagree)
//        dialog.setView();
    }

    @Override
    protected void convert(ViewHolder viewHolder, OrderListResult.ObjBean.ItemListBean item, int position) {
        viewHolder.setVisible(R.id.lin_order_date, true);
        viewHolder.setVisible(R.id.tv_order_status, false);
        viewHolder.setText(R.id.tv_order_no, item.getId() + "");
        viewHolder.setText(R.id.tv_order_date, item.getAdd_time());
        LinearLayout layoutOrder = viewHolder.getView(R.id.lin_goods);
        viewHolder.setVisible(R.id.lin_op, false);
        layoutOrder.removeAllViewsInLayout();
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
            Button btn_op = (Button) view.findViewById(R.id.bt_op);
            LinearLayout goods_op = (LinearLayout) view.findViewById(R.id.lin_op);
            x.image().bind(goods_image, Constant.IMG_SERVICE + orderListBean.getGoods_image());
            goods_name.setText(orderListBean.getProduct_name());
            goods_stock.setText(orderListBean.getStock() + "");
            goods_moq.setText(orderListBean.getMin_num() + "");
            goods_num.setText("x" + orderListBean.getNum());
            goods_price.setText(mContext.getString(R.string.rmb) + orderListBean.getWholesale_price());
            goods_status.setVisibility(View.VISIBLE);
            goods_op.setVisibility(View.GONE);

            if (item.getOrder_status() == 100401) {
                if (orderListBean.getReturn_status() == 0) {
                    goods_status.setText("待付款");
                    viewHolder.setVisible(R.id.lin_op, true);
                    viewHolder.setVisible(R.id.bt_op_01, true);
                    viewHolder.setVisible(R.id.bt_op_02, true);
                    viewHolder.setText(R.id.bt_op_01, "付款");
                    viewHolder.setText(R.id.bt_op_02, "删除");
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
                    goods_op.setVisibility(View.VISIBLE);
                    btn_op.setText("退款");
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
            if (item.getOrder_status() > 100402 && orderListBean.getReturn_status() == 0) {
                goods_op.setVisibility(View.VISIBLE);
                btn_op.setText("退货");
                if (item.getOrder_status() == 100403 && orderType != 3) {
                    viewHolder.setVisible(R.id.lin_op, true);
                    viewHolder.setVisible(R.id.bt_op_01, true);
                    viewHolder.setVisible(R.id.bt_op_02, false);
                    viewHolder.setText(R.id.bt_op_01, "确认收货");
                }

            }
            if (orderListBean.getReturn_status() == 100503) {
                btn_op.setText("填写物流信息");
                goods_op.setVisibility(View.VISIBLE);
            } else if (orderListBean.getDec_status() == 2 && orderListBean.getDay1() < 8) {
                btn_op.setText("查看卖家反馈");
                goods_op.setVisibility(View.VISIBLE);
            }
            btn_op.setOnClickListener(new InnerListner(item));
            layoutOrder.addView(view);
            num += orderListBean.getNum();
            sum += orderListBean.getNum() * orderListBean.getWholesale_price();
        }
        viewHolder.setText(R.id.tv_goods_num, num + "");
        viewHolder.setText(R.id.tv_order_price, sum + "");

        viewHolder.setOnClickListener(R.id.bt_op_01, new OuterListner(item));
        viewHolder.setOnClickListener(R.id.bt_op_02, new OuterListner(item));
    }

    class InnerListner implements View.OnClickListener {
        private OrderListResult.ObjBean.ItemListBean listBean;

        public InnerListner(OrderListResult.ObjBean.ItemListBean listBean) {
            this.listBean = listBean;
        }


        @Override
        public void onClick(View v) {
            if (CommUtils.textToString((Button) v).equals("退款")) {

            } else if (CommUtils.textToString((Button) v).equals("退货")) {
            } else if (CommUtils.textToString((Button) v).equals("填写物流信息")) {
            } else if (CommUtils.textToString((Button) v).equals("查看卖家反馈")) {
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
                intent.putExtra(Constant.DATA, listBean.getOrder_totals());
                mContext.startActivity(intent);
            } else if (CommUtils.textToString((Button) v).equals("删除")) {
            } else if (CommUtils.textToString((Button) v).equals("确认收货")) {
            }

        }
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
