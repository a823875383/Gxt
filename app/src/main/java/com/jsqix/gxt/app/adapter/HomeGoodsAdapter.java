package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.MerchandiseInfo;
import com.jsqix.gxt.app.activity.OrderSubmit;
import com.jsqix.gxt.app.obj.HomeMerchandiseResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * Created by dongqing on 16/9/28.
 */

public class HomeGoodsAdapter extends CommonAdapter<HomeMerchandiseResult.ObjBean.ItemListBean> {
    private ImageOptions options;

    private CustomDialog MoqDialog;
    private ImageView reduceNum, addNum;
    private EditText moqNum;
    private Button btConfirm, btCancel;

    private UpdateNum update;

    public HomeGoodsAdapter(Context context, final int layoutId, List<HomeMerchandiseResult.ObjBean.ItemListBean> datas) {
        super(context, layoutId, datas);
        options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_CENTER).build();
        initMoqDialog();
    }

    public void setUpdate(UpdateNum update) {
        this.update = update;
    }

    private void initMoqDialog() {
        MoqDialog = new CustomDialog(mContext);
        View layout = LayoutInflater.from(mContext).inflate(R.layout.view_goods_moq, null);
        reduceNum = (ImageView) layout.findViewById(R.id.iv_reduce);
        addNum = (ImageView) layout.findViewById(R.id.iv_add);
        moqNum = (EditText) layout.findViewById(R.id.tv_num);
        btConfirm = (Button) layout.findViewById(R.id.bt_confirm);
        btCancel = (Button) layout.findViewById(R.id.bt_cancel);
        MoqDialog.setView(layout);
        MoqDialog.setParas(0.9f, 0);
        reduceNum.setOnClickListener(v -> {
            HomeMerchandiseResult.ObjBean.ItemListBean item = (HomeMerchandiseResult.ObjBean.ItemListBean) MoqDialog.getObject();
            int num = StringUtils.toInt(CommUtils.textToString(moqNum));
            if (num > item.getMin_num()) {
                num--;
                moqNum.setText("" + num);
            }
        });
        addNum.setOnClickListener(v -> {
            HomeMerchandiseResult.ObjBean.ItemListBean item = (HomeMerchandiseResult.ObjBean.ItemListBean) MoqDialog.getObject();
            int num = StringUtils.toInt(CommUtils.textToString(moqNum));
            if (num < item.getStock()) {
                num++;
                moqNum.setText("" + num);
            }
        });
        btConfirm.setOnClickListener(v -> {
            HomeMerchandiseResult.ObjBean.ItemListBean item = (HomeMerchandiseResult.ObjBean.ItemListBean) MoqDialog.getObject();
            int num = StringUtils.toInt(CommUtils.textToString(moqNum));
            if (item.getStock() == 0) {
                Utils.makeToast(mContext, "该商品暂无库存,请选择其他商品");
            } else {
                if (num < item.getMin_num()) {
                    moqNum.setText(item.getMin_num() + "");
                    Utils.makeToast(mContext, "该商品起订量为" + item.getMin_num());
                } else if (num > item.getStock()) {
                    moqNum.setText(item.getStock() + "");
                    Utils.makeToast(mContext, "您订购的超过该商品的库存了!");
                }
                if (update != null) {
                    update.update(CommUtils.textToString(moqNum));
                }
            }
            MoqDialog.dismiss();
        });
        btCancel.setOnClickListener(v -> {
            MoqDialog.dismiss();
        });
    }

    @Override
    protected void convert(ViewHolder viewHolder, HomeMerchandiseResult.ObjBean.ItemListBean item, int position) {
        ImageView goods_img = viewHolder.getView(R.id.iv_goods_img);
        TextView tvNum = viewHolder.getView(R.id.tv_num);
        x.image().bind(goods_img, Constant.IMG_SERVICE + item.getGoods_image(), options);
        viewHolder.setText(R.id.tv_goods_name, item.getProduct_name());
        viewHolder.setText(R.id.tv_goods_stock, item.getStock() + "");
        viewHolder.setText(R.id.tv_goods_moq, item.getMin_num() + "");
        viewHolder.setText(R.id.tv_goods_price, mContext.getString(R.string.rmb) + item.getWholesale_price());
        viewHolder.setText(R.id.tv_num, item.getMin_num() + "");
        viewHolder.getConvertView().setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MerchandiseInfo.class);
            intent.putExtra(Constant.ID, item.getProduct_id());
            mContext.startActivity(intent);
        });
        viewHolder.setOnClickListener(R.id.tv_num, v -> {
            if (!MoqDialog.isShowing()) {
                MoqDialog.setObject(item);
                moqNum.setText(CommUtils.textToString(tvNum));
                moqNum.setSelection(CommUtils.textToString(moqNum).length());
                MoqDialog.show();
                setUpdate(num -> {
                    tvNum.setText(num);
                });
            }
        });
        viewHolder.setOnClickListener(R.id.iv_reduce, v -> {
            int num = StringUtils.toInt(CommUtils.textToString(tvNum));
            if (num > item.getMin_num()) {
                num--;
                tvNum.setText("" + num);
            }
        });
        viewHolder.setOnClickListener(R.id.iv_add, v -> {
            int num = StringUtils.toInt(CommUtils.textToString(tvNum));
            if (num < item.getStock()) {
                num++;
                tvNum.setText("" + num);
            }
        });
        viewHolder.setOnClickListener(R.id.bt_buy, v -> {
            if (item.getStock() == 0) {
                Utils.makeToast(mContext, "该商品暂无库存,请选择其他商品");
            } else {
                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cartNumber", CommUtils.textToString(tvNum));
                map.put("productId", item.getProduct_id());
                data.add(map);
                Intent intent = new Intent(mContext, OrderSubmit.class);
                intent.putExtra(Constant.BUY_TYPE, 0);
                intent.putExtra(Constant.DATA, new Gson().toJson(data));
                mContext.startActivity(intent);
            }

        });
        viewHolder.setOnClickListener(R.id.bt_cart, v -> {

        });
    }

    interface UpdateNum {
        void update(String num);
    }
}
