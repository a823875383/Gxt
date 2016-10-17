package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.BankListResult;
import com.jsqix.utils.DensityUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * Created by dongqing on 2016/10/17.
 */

public class BankManageAdapter extends CommonAdapter<BankListResult.ObjBean> {
    private bankOperaListener operaListener;

    private CustomDialog confirmDialog;
    private Button btConfirm;

    int bankId;

    public BankManageAdapter(Context context, int layoutId, List<BankListResult.ObjBean> datas) {
        super(context, layoutId, datas);
        initDialog();
    }

    private void initDialog() {
        confirmDialog = new CustomDialog(mContext);
        View confirmView = LayoutInflater.from(mContext).inflate(R.layout.view_order_refund, null);
        TextView tvTitleMsg = (TextView) confirmView.findViewById(R.id.tv_msg);
        btConfirm = (Button) confirmView.findViewById(R.id.bt_confirm);
        Button btCancel = (Button) confirmView.findViewById(R.id.bt_cancel);
        tvTitleMsg.setText("确定要删除该银行卡？");
        btConfirm.setOnClickListener(v -> {
            if (operaListener != null) {
                operaListener.bankDelete(bankId);
                confirmDialog.dismiss();
            }
        });
        btCancel.setOnClickListener(v -> {
            confirmDialog.dismiss();
        });
        confirmDialog.setView(confirmView);
    }

    public void setOperaListener(bankOperaListener operaListener) {
        this.operaListener = operaListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, BankListResult.ObjBean item, int position) {
        LinearLayout base = viewHolder.getView(R.id.lin_base);
        if (item.getBank_no() == 102801) {
            viewHolder.setText(R.id.tv_bank_name, "中国建设银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_ccb);
            base.setBackgroundDrawable(getGradient(Color.BLUE));

        } else if (item.getBank_no() == 102802) {
            viewHolder.setText(R.id.tv_bank_name, "中国工商银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_icbc);
            base.setBackgroundDrawable(getGradient(Color.RED));

        } else if (item.getBank_no() == 102803) {
            viewHolder.setText(R.id.tv_bank_name, "中国农业银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_abc);
            base.setBackgroundDrawable(getGradient(Color.GREEN));

        } else if (item.getBank_no() == 102804) {
            viewHolder.setText(R.id.tv_bank_name, "中国交通银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_comm);
            base.setBackgroundDrawable(getGradient(Color.BLUE));

        } else if (item.getBank_no() == 102805) {
            viewHolder.setText(R.id.tv_bank_name, "中国银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_boc);
            base.setBackgroundDrawable(getGradient(Color.RED));

        } else if (item.getBank_no() == 102806) {
            viewHolder.setText(R.id.tv_bank_name, "中国招商银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_cmb);
            base.setBackgroundDrawable(getGradient(Color.RED));

        } else if (item.getBank_no() == 102807) {
            viewHolder.setText(R.id.tv_bank_name, "中国民生银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_cmbc);
            base.setBackgroundDrawable(getGradient(Color.GREEN));

        } else if (item.getBank_no() == 102808) {
            viewHolder.setText(R.id.tv_bank_name, "浦东发展银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_spdb);
            base.setBackgroundDrawable(getGradient(Color.BLUE));

        } else if (item.getBank_no() == 102809) {
            viewHolder.setText(R.id.tv_bank_name, "邮政储蓄银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_psbc);
            base.setBackgroundDrawable(getGradient(Color.GREEN));

        } else if (item.getBank_no() == 102810) {
            viewHolder.setText(R.id.tv_bank_name, "中国中信银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_citic);
            base.setBackgroundDrawable(getGradient(Color.RED));

        } else if (item.getBank_no() == 102811) {
            viewHolder.setText(R.id.tv_bank_name, "中国光大银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_ceb);
            base.setBackgroundDrawable(getGradient(Color.BLUE));

        }
        if (item.getAccount_type() == 102901) {
            viewHolder.setText(R.id.tv_bank_type, "储蓄卡");
        } else {

        }
        viewHolder.setText(R.id.tv_bank_number, mContext.getString(R.string.bank_number).replace("####", item.getBank_account()));
        viewHolder.setOnClickListener(R.id.tv_delete, v -> {
            bankId = item.getId();
            confirmDialog.show();
        });
    }

    private GradientDrawable getGradient(int color) {
        int[] colors = {0xff28B190, 0xff22A8A2, 0xff1C9EB4};//分别为开始颜色，中间夜色，结束颜色
        if (color == Color.RED) {
            colors = new int[]{0xffE55D68, 0xffE65872, 0xffE7537D};
        } else if (color == Color.GREEN) {
            colors = new int[]{0xff28B190, 0xff22A8A2, 0xff1C9EB4};
        } else if (color == Color.BLUE) {
            colors = new int[]{0xff5183BC, 0xff2370B7, 0xff3F61A8};
        }
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gd.setCornerRadius(DensityUtil.dip2px(mContext, 10));
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return gd;
    }

    public interface bankOperaListener {
        void bankDelete(int cardId);
    }
}
