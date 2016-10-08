package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.widget.CheckBox;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.AddressObj;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 16/10/8.
 */

public class AddressChooseAdapter extends CommonAdapter<AddressObj> {
    private int selectPosition = -1;
    //    SparseArray<Boolean> sparseArray = new SparseArray<>();
    private OnSelectListner onSelectListner;

    public AddressChooseAdapter(Context context, int layoutId, List<AddressObj> datas) {
        super(context, layoutId, datas);
    }

    public void setOnSelectListner(OnSelectListner onSelectListner) {
        this.onSelectListner = onSelectListner;
    }

    @Override
    protected void convert(ViewHolder viewHolder, AddressObj item, int position) {
        int isDefault = item.getWhether_default();
        viewHolder.setText(R.id.tv_name, item.getConsignee());
        viewHolder.setText(R.id.tv_phone, item.getPhone());
        viewHolder.setText(R.id.tv_address, item.getPro_name() + item.getCity_name() + item.getContry_name() + item.getStreet_address());
        if (isDefault == 0) {
            viewHolder.setVisible(R.id.iv_default, true);
        } else {
            viewHolder.setVisible(R.id.iv_default, false);
        }
        CheckBox choose = viewHolder.getView(R.id.cb_choose);
        if (selectPosition == position) {
            choose.setChecked(true);
        } else {
            if (selectPosition == -1 && isDefault == 0) {
                choose.setChecked(true);
            } else {
                choose.setChecked(false);
            }
        }
//        sparseArray.put(position, choose.isChecked());
        choose.setOnClickListener(v -> {
            setSelectPosition(position);
            if (onSelectListner != null) {
                onSelectListner.onSelect(position);
            }
        });
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
//        sparseArray.put(selectPosition, true);
//        for (int i = 0; i < sparseArray.size(); i++) {
//            if (i == selectPosition)
//                continue;
//            sparseArray.put(i, false);
//        }
        notifyDataSetChanged();
    }

    public interface OnSelectListner {
        void onSelect(int position);
    }
}
