package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.OrderSureResult;
import com.jsqix.gxt.app.utils.Constant;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by dongqing on 16/10/8.
 */

public class SureOrderAdapter extends CommonAdapter<OrderSureResult.ObjBean.ProductlistBean> {
    public SureOrderAdapter(Context context, int layoutId, List<OrderSureResult.ObjBean.ProductlistBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, OrderSureResult.ObjBean.ProductlistBean item, int position) {
        ImageView goods_image = viewHolder.getView(R.id.iv_goods_img);
        x.image().bind(goods_image, Constant.IMG_SERVICE + item.getGoods_image());
        viewHolder.setText(R.id.tv_goods_name, item.getProduct_name());
        viewHolder.setText(R.id.tv_goods_stock, item.getStock() + "");
        viewHolder.setText(R.id.tv_goods_moq, item.getMin_num() + "");
        viewHolder.setText(R.id.tv_order_num, "x"+item.getCart_number());
        viewHolder.setText(R.id.tv_goods_price, mContext.getString(R.string.rmb) + item.getWholesale_price());
        viewHolder.setVisible(R.id.lin_op,false);

    }
}
