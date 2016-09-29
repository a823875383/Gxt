package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.MerchandiseInfo;
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

public class HomeGoodsAdapter extends CommonAdapter<HomeMerchandiseResult.ObjBean.ItemListBean> {
    private ImageOptions options;

    public HomeGoodsAdapter(Context context, final int layoutId, List<HomeMerchandiseResult.ObjBean.ItemListBean> datas) {
        super(context, layoutId, datas);
        options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_CENTER).build();
    }

    @Override
    protected void convert(ViewHolder viewHolder, HomeMerchandiseResult.ObjBean.ItemListBean item, int position) {
        ImageView goods_img = viewHolder.getView(R.id.iv_goods_img);
        x.image().bind(goods_img, Constant.IMG_SERVICE + item.getGoods_image(), options);
        viewHolder.setText(R.id.tv_goods_name, item.getProduct_name());
        viewHolder.setText(R.id.tv_goods_stock, item.getStock() + "");
        viewHolder.setText(R.id.tv_goods_moq, item.getMin_num() + "");
        viewHolder.setText(R.id.tv_goods_price, mContext.getString(R.string.rmb) + item.getWholesale_price());
        viewHolder.setText(R.id.tv_num, item.getMin_num() + "");
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MerchandiseInfo.class);
                intent.putExtra(Constant.ID,item.getProduct_id());
                mContext.startActivity(intent);
            }
        });
    }
}
