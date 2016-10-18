package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.OrderInfoResult;
import com.jsqix.gxt.app.obj.OrderObj;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.OrderDialogUtils;
import com.jsqix.gxt.app.utils.OrderOpUtils;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;

@ContentView(R.layout.activity_order_info)
public class OrderInfo extends BaseToolActivity implements HttpGet.InterfaceHttpGet, OrderDialogUtils.PurchaseListener, OrderOpUtils.DataResultListener {
    @ViewInject(R.id.tv_update_status)
    private TextView updateStatus;
    @ViewInject(R.id.tv_update_date)
    private TextView updateDate;
    @ViewInject(R.id.tv_name)
    private TextView receiveName;
    @ViewInject(R.id.tv_phone)
    private TextView receivePhone;
    @ViewInject(R.id.tv_address)
    private TextView receiveAddress;

    @ViewInject(R.id.lin_goods)
    private LinearLayout layoutOrder;

    @ViewInject(R.id.tv_order_no)
    private TextView orderNo;

    @ViewInject(R.id.lin_op)
    private LinearLayout opLayout;
    @ViewInject(R.id.bt_op_01)
    private Button btOp1;
    @ViewInject(R.id.bt_op_02)
    private Button btOp2;

    @ViewInject(R.id.tv_merchandise_total)
    private TextView goodsTotal;
    @ViewInject(R.id.tv_order_total)
    private TextView orderTotal;
    @ViewInject(R.id.tv_pay_total)
    private TextView payTotal;
    @ViewInject(R.id.tv_order_date_01)
    private TextView orderdDate_1;
    @ViewInject(R.id.tv_order_date_02)
    private TextView orderdDate_2;
    @ViewInject(R.id.tv_order_date_03)
    private TextView orderdDate_3;
    @ViewInject(R.id.tv_order_date_04)
    private TextView orderdDate_4;

    private int orderId, type;

    private OrderDialogUtils dialogUtils;
    private OrderOpUtils opUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.order_info));
    }

    @Override
    protected void initView() {

        getOrderInfo();

        dialogUtils = new OrderDialogUtils(this);
        dialogUtils.setPurchaseListener(this);
        opUtils = new OrderOpUtils(this);
        opUtils.setResultListener(this);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        orderId = getIntent().getIntExtra(Constant.ID, 0);
        type = getIntent().getIntExtra(Constant.ORDER_TYPE, 1);
    }

    private void getOrderInfo() {
        Map<String, Object> unParas = new HashMap<>();
        unParas.put("type", type);
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("orderId", orderId);
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.execute(RequestIP.MY_ORDER_DETAIL);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        OrderInfoResult infoResult = new Gson().fromJson(result, OrderInfoResult.class);
        if (infoResult != null) {
            if (infoResult.getCode().equals("000")) {
                OrderObj item = infoResult.getObj();

                receiveName.setText(item.getContactname());
                receivePhone.setText(item.getContacttel());
                receiveAddress.setText(item.getP_name() + item.getC_name() + item.getX_name() + item.getAddress());
                goodsTotal.setText(getString(R.string.rmb) + item.getOrder_totals());
                orderTotal.setText(getString(R.string.rmb) + item.getOrder_totals());
                payTotal.setText(getString(R.string.rmb) + item.getOrder_totals());
                orderdDate_1.setText(getString(R.string.order_add_time) + item.getOrder_time());
                if (StringUtils.isEmpty(item.getPay_time())) {
                    orderdDate_2.setVisibility(View.GONE);
                } else {
                    orderdDate_2.setText(getString(R.string.order_pay_time) + item.getPay_time());
                }
                if (StringUtils.isEmpty(item.getDelivery_time())) {
                    orderdDate_3.setVisibility(View.GONE);
                } else {
                    orderdDate_3.setText(getString(R.string.order_deliver_time) + item.getDelivery_time());
                }
                if (StringUtils.isEmpty(item.getOrder_finish_time())) {
                    orderdDate_4.setVisibility(View.GONE);
                } else {
                    orderdDate_4.setText(getString(R.string.order_finish_time) + item.getOrder_finish_time());
                }
                if (!StringUtils.isEmpty(item.getOrder_finish_time())) {
                    updateDate.setText(item.getOrder_finish_time());
                } else if (!StringUtils.isEmpty(item.getDelivery_time())) {
                    updateDate.setText(item.getDelivery_time());
                } else if (!StringUtils.isEmpty(item.getPay_time())) {
                    updateDate.setText(item.getPay_time());
                } else {
                    updateDate.setText(item.getOrder_time());
                }
                opLayout.setVisibility(View.GONE);
                btOp1.setVisibility(View.GONE);
                btOp2.setVisibility(View.GONE);
                layoutOrder.removeAllViewsInLayout();
                for (int i = 0; i < item.getOrder_list().size(); i++) {
                    OrderObj.OrderListBean orderListBean = item.getOrder_list().get(i);
                    View view = LayoutInflater.from(this).inflate(R.layout.layout_order, null);
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
                    goods_price.setText(this.getString(R.string.rmb) + orderListBean.getWholesale_price());
                    goods_status.setVisibility(View.VISIBLE);
                    goods_op.setVisibility(View.GONE);
                    btn_op1.setVisibility(View.VISIBLE);
                    btn_op2.setVisibility(View.GONE);

                    if (item.getOrder_status() == 100401) {
                        if (orderListBean.getReturn_status() == 0) {
                            goods_status.setText("等待买家付款");
                            updateStatus.setText("您已下单，请及时付款！");
                            opLayout.setVisibility(View.VISIBLE);
                            btOp1.setVisibility(View.VISIBLE);
                            btOp2.setVisibility(View.GONE);
                            btOp1.setText("付款");
                            btOp2.setText("删除");
                        } else if (orderListBean.getReturn_status() == 100505) {
                            goods_status.setText("退款中");
                        } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                            goods_status.setText("退货中");
                        } else {
                            goods_status.setText("已退款");
                        }
                    } else if (item.getOrder_status() == 100402) {
                        if (orderListBean.getReturn_status() == 0) {
                            updateStatus.setText("您已付款，等待卖家发货！");
                            goods_status.setText("卖家未发货");
                            goods_op.setVisibility(View.VISIBLE);
                            btn_op1.setText("退款");
                        } else if (orderListBean.getReturn_status() == 100505) {
                            goods_status.setText("退款中");
                        } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                            goods_status.setText("退货中");
                        } else {
                            goods_status.setText("已退款");
                        }
                    } else if (item.getOrder_status() == 100403) {
                        if (orderListBean.getReturn_status() == 0) {
                            goods_status.setText("卖家已发货");
                            updateStatus.setText("卖家已发货，请您注意签收！");
                        } else if (orderListBean.getReturn_status() == 100503) {
                            goods_status.setText("卖家同意");
                        } else if (orderListBean.getReturn_status() == 100505) {
                            goods_status.setText("退款中");
                        } else if (orderListBean.getReturn_status() < 100505 && orderListBean.getReturn_status() != 100502) {
                            goods_status.setText("退货中");
                        } else {
                            goods_status.setText("已退款");
                        }
                    } else if (item.getOrder_status() == 100404) {
                        if (orderListBean.getReturn_status() == 0) {
                            goods_status.setText("交易成功");
                            updateStatus.setText("您的商品已确认收货,交易成功！");
                        } else if (orderListBean.getReturn_status() == 100502) {
                            goods_status.setText("卖家拒绝");
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
                            updateStatus.setText("您取消了订单,交易关闭！");
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
                    if (item.getOrder_status() > 100402 && orderListBean.getReturn_status() == 0 && item.getOrder_status() != 100405) {
                        goods_op.setVisibility(View.VISIBLE);
                        btn_op1.setText("退货");
                        if (item.getOrder_status() == 100403 && type != 2) {
                            opLayout.setVisibility(View.VISIBLE);
                            btOp1.setVisibility(View.VISIBLE);
                            btOp2.setVisibility(View.GONE);
                            btOp1.setText("确认收货");
                        }

                    }
                    if (orderListBean.getReturn_status() == 100503) {
                        btn_op1.setText("填写物流信息");
                        goods_op.setVisibility(View.VISIBLE);
                    }


                    btn_op1.setOnClickListener(dialogUtils.new InnerListner(item, i));
                    btn_op2.setOnClickListener(dialogUtils.new InnerListner(item, i));
                    layoutOrder.addView(view);
                }
                btOp1.setOnClickListener(dialogUtils.new OuterListner(item));
                btOp2.setOnClickListener(dialogUtils.new OuterListner(item));
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
        loadingUtils.dismiss();
    }

    @Override
    public void deleOrder(OrderObj listBean) {
        opUtils.orderBuyerOperate(2, listBean.getId(), -1, -1, "", "", "");//删除订单
    }

    @Override
    public void logistics(OrderObj listBean, int expressType, String expressNo, int position) {
        opUtils.orderBuyerOperate(5, -1, listBean.getOrder_list().get(position).getDetail_id(), expressType, expressNo, "", "");//填写物流信息
    }

    @Override
    public void receiptOrder(OrderObj listBean, int position) {
        opUtils.orderBuyerOperate(6, listBean.getId(), -1, -1, "", "", "");//确认收货
    }

    @Override
    public void refreshData() {
        getOrderInfo();
    }
}
