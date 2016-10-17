package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.BankListResult;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 2016/10/17.
 */

public class BankChooseAdapter extends CommonAdapter<BankListResult.ObjBean> {
    private int selectPosition = -1;
    private OnSelectListener onSelectListener;

    public BankChooseAdapter(Context context, int layoutId, List<BankListResult.ObjBean> datas) {
        super(context, layoutId, datas);
    }

    public void setOnSelectListner(OnSelectListener onSelectListner) {
        this.onSelectListener = onSelectListner;
    }

    @Override
    protected void convert(ViewHolder viewHolder, BankListResult.ObjBean item, int position) {
        LinearLayout base = viewHolder.getView(R.id.lin_base);
        if (item.getBank_no() == 102801) {
            viewHolder.setText(R.id.tv_bank_name, "中国建设银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_ccb);

        } else if (item.getBank_no() == 102802) {
            viewHolder.setText(R.id.tv_bank_name, "中国工商银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_icbc);

        } else if (item.getBank_no() == 102803) {
            viewHolder.setText(R.id.tv_bank_name, "中国农业银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_abc);

        } else if (item.getBank_no() == 102804) {
            viewHolder.setText(R.id.tv_bank_name, "中国交通银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_comm);

        } else if (item.getBank_no() == 102805) {
            viewHolder.setText(R.id.tv_bank_name, "中国银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_boc);

        } else if (item.getBank_no() == 102806) {
            viewHolder.setText(R.id.tv_bank_name, "中国招商银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_cmb);

        } else if (item.getBank_no() == 102807) {
            viewHolder.setText(R.id.tv_bank_name, "中国民生银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_cmbc);

        } else if (item.getBank_no() == 102808) {
            viewHolder.setText(R.id.tv_bank_name, "浦东发展银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_spdb);

        } else if (item.getBank_no() == 102809) {
            viewHolder.setText(R.id.tv_bank_name, "邮政储蓄银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_psbc);

        } else if (item.getBank_no() == 102810) {
            viewHolder.setText(R.id.tv_bank_name, "中国中信银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_citic);

        } else if (item.getBank_no() == 102811) {
            viewHolder.setText(R.id.tv_bank_name, "中国光大银行");
            viewHolder.setImageResource(R.id.iv_bank_icon, R.mipmap.bank_ceb);

        }
        if (item.getAccount_type() == 102901) {
            viewHolder.setText(R.id.tv_bank_type, "储蓄卡");
        } else {

        }
        if (selectPosition == position) {
            viewHolder.setVisible(R.id.iv_choose, true);
        } else {
            if (selectPosition == -1 && position == 0) {
                viewHolder.setVisible(R.id.iv_choose, true);
            } else {
                viewHolder.setVisible(R.id.iv_choose, false);
            }
        }
        viewHolder.setText(R.id.tv_bank_number, "尾号" + item.getBank_account());
        viewHolder.getConvertView().setOnClickListener(v -> {
            setSelectPosition(position);
            if (onSelectListener != null) {
                onSelectListener.onSelect(position);
            }
        });
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public interface OnSelectListener {
        void onSelect(int position);
    }
}
