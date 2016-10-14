package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.MerchandiseManageResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.GoodsDialogUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.x;

import java.util.List;

/**
 * Created by dongqing on 2016/10/13.
 */

public class GoodsManageAdapter extends CommonAdapter<MerchandiseManageResult.ObjBean.ItemListBean> {
    private GoodsDialogUtils dialogUtils;


    public GoodsManageAdapter(Context context, int layoutId, List<MerchandiseManageResult.ObjBean.ItemListBean> datas) {
        super(context, layoutId, datas);
        dialogUtils = new GoodsDialogUtils(context);
    }

    public void setOpListener(GoodsDialogUtils.MerchandiseOpListener opListener) {
        dialogUtils.setOpListener(opListener);
    }


    @Override
    protected void convert(ViewHolder viewHolder, MerchandiseManageResult.ObjBean.ItemListBean item, int position) {
        ImageView goods_image = viewHolder.getView(R.id.iv_goods_img);
        x.image().bind(goods_image, Constant.IMG_SERVICE + item.getGoods_image());
        viewHolder.setText(R.id.tv_goods_name, item.getGoods_name());
        viewHolder.setText(R.id.tv_goods_no, item.getGoods_id() + "");
        if (item.getGoods_status() == 0) {
            viewHolder.setText(R.id.tv_goods_status, "已下架");
            viewHolder.setBackgroundColor(R.id.bt_goods_op, mContext.getResources().getColor(R.color.red));
            viewHolder.setText(R.id.bt_goods_op, "上架");
        } else {
            viewHolder.setText(R.id.tv_goods_status, "出售中");
            viewHolder.setBackgroundColor(R.id.bt_goods_op, mContext.getResources().getColor(R.color.green));
            viewHolder.setText(R.id.bt_goods_op, "操作");

        }
        viewHolder.setOnClickListener(R.id.bt_goods_op, v -> {
            dialogUtils.setGoodsId(item.getGoods_id());
            if (item.getGoods_status() == 0) {
                //要上架
                dialogUtils.getConfirmDialog().show();
            } else {
                //修改
                dialogUtils.getOperateStock().setText("");
                dialogUtils.getOperateMoq().setText("");
                dialogUtils.getGoodsStandard();
            }
        });

    }


    public void dismissDialog() {
        dialogUtils.dismissDialog();
    }
}
