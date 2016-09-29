package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.HomeMerchandiseResult;
import com.jsqix.gxt.app.utils.Constant;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by dongqing on 16/9/28.
 */

public class HomeGoodsAdapter extends CommonAdapter {
    private ImageOptions options;

    public HomeGoodsAdapter(Context context, final int layoutId, List datas) {
        super(context, layoutId, datas);
        options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_CENTER).build();
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        HomeMerchandiseResult.ObjBean.ItemListBean bean = (HomeMerchandiseResult.ObjBean.ItemListBean) item;
        ImageView goods_img = viewHolder.getView(R.id.iv_goods_img);
        x.image().bind(goods_img, Constant.IMG_SERVICE + bean.getGoods_image(), options);
        viewHolder.setText(R.id.tv_goods_name, bean.getProduct_name());
        viewHolder.setText(R.id.tv_goods_stock, bean.getStock() + "");
        viewHolder.setText(R.id.tv_goods_moq, bean.getMin_num() + "");
        viewHolder.setText(R.id.tv_goods_price, "¥ " + bean.getWholesale_price());
        viewHolder.setText(R.id.tv_num, bean.getMin_num() + "");

    }
}
