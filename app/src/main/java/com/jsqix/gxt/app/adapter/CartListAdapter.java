package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.CartListResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.x;

import java.util.List;

import gxt.jsqix.com.mycommon.base.util.CommUtils;

/**
 * Created by dongqing on 2016/10/11.
 */

public class CartListAdapter extends CommonAdapter<CartListResult.ObjBean.ItemListBean> {
    private SelectListener selectListener;

    public CartListAdapter(Context context, int layoutId, List<CartListResult.ObjBean.ItemListBean> datas) {
        super(context, layoutId, datas);
    }

    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, CartListResult.ObjBean.ItemListBean item, int position) {
        viewHolder.setChecked(R.id.cb_choose, item.isSelect());
        ImageView goods_image = viewHolder.getView(R.id.iv_goods_img);
        TextView tvNum = viewHolder.getView(R.id.tv_num);
        x.image().bind(goods_image, Constant.IMG_SERVICE + item.getGoods_image());
        viewHolder.setText(R.id.tv_goods_name, item.getProduct_name());
        viewHolder.setText(R.id.tv_goods_stock, item.getStock() + "");
        viewHolder.setText(R.id.tv_goods_moq, item.getMin_num() + "");
        viewHolder.setText(R.id.tv_goods_price, mContext.getString(R.string.rmb) + item.getWholesale_price());
        viewHolder.setText(R.id.tv_num, item.getCart_number() + "");
        viewHolder.setOnClickListener(R.id.cb_choose, v -> {
            boolean isSelect = ((CheckBox) v).isChecked();
            item.setSelect(isSelect);
            notifyDataSetChanged();
            if (selectListener != null) {
                selectListener.onSelect();
            }
        });
        viewHolder.setOnClickListener(R.id.iv_reduce, v -> {
            int num = StringUtils.toInt(CommUtils.textToString(tvNum));
            if (num > item.getMin_num()) {
                num--;
                tvNum.setText("" + num);
            }
            item.setCart_number(StringUtils.toInt(CommUtils.textToString(tvNum)));
            if (selectListener != null) {
                selectListener.onSelect();
            }
        });
        viewHolder.setOnClickListener(R.id.iv_add, v -> {
            int num = StringUtils.toInt(CommUtils.textToString(tvNum));
            if (num < item.getStock()) {
                num++;
                tvNum.setText("" + num);
            }
            item.setCart_number(StringUtils.toInt(CommUtils.textToString(tvNum)));
            if (selectListener != null) {
                selectListener.onSelect();
            }
        });

    }

    public void setAllSelect() {
        for (CartListResult.ObjBean.ItemListBean listBean : mDatas) {
            listBean.setSelect(true);
            notifyDataSetChanged();
        }
        if (selectListener != null) {
            selectListener.onSelect();
        }
    }

    public void setAllUnselect() {
        for (CartListResult.ObjBean.ItemListBean listBean : mDatas) {
            listBean.setSelect(false);
            notifyDataSetChanged();
        }
        if (selectListener != null) {
            selectListener.onSelect();
        }
    }

    public boolean isAllSelect() {

        if (getSelectNum() == getCount()) {
            return true;
        }
        return false;
    }

    public int getSelectNum() {
        int num = 0;
        for (CartListResult.ObjBean.ItemListBean listBean : mDatas) {
            if (listBean.isSelect() == true) {
                num++;
            }
        }
        return num;
    }

    public interface SelectListener {
        void onSelect();
    }
}
