package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.CheckBox;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.AddressActivity;
import com.jsqix.gxt.app.obj.AddressObj;
import com.jsqix.gxt.app.utils.Constant;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 16/10/8.
 */

public class AddressManageAdapter extends CommonAdapter<AddressObj> {
    private UpdateDefaultListner updateDefaultListner;

    public AddressManageAdapter(Context context, int layoutId, List<AddressObj> datas) {
        super(context, layoutId, datas);
    }

    public void setUpdateDefaultListner(UpdateDefaultListner updateDefaultListner) {
        this.updateDefaultListner = updateDefaultListner;
    }

    @Override
    protected void convert(ViewHolder viewHolder, AddressObj item, int position) {
        int isDefault = item.getWhether_default();
        viewHolder.setText(R.id.tv_name, item.getConsignee());
        viewHolder.setText(R.id.tv_phone, item.getPhone());
        viewHolder.setText(R.id.tv_address, item.getPro_name() + item.getCity_name() + item.getContry_name() + item.getStreet_address());
        if (isDefault == 0) {
            viewHolder.setVisible(R.id.iv_default, true);
            viewHolder.setChecked(R.id.cb_choose, true);
        } else {
            viewHolder.setVisible(R.id.iv_default, false);
            viewHolder.setChecked(R.id.cb_choose, false);
        }
        viewHolder.setOnClickListener(R.id.iv_edit, v -> {
            Intent intent = new Intent(mContext, AddressActivity.class);
            intent.putExtra(Constant.TITLE, mContext.getString(R.string.title_edit_address));
            intent.putExtra(Constant.ID, item.getId());
            mContext.startActivity(intent);
        });
        CheckBox choose = viewHolder.getView(R.id.cb_choose);
//        choose.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (updateDefaultListner != null) {
//                updateDefaultListner.updateDefaultListner(item.getId(), isChecked ? 1 : 0);
//            }
//        });
        viewHolder.setOnClickListener(R.id.cb_choose, v -> {
            if (updateDefaultListner != null) {
                updateDefaultListner.updateDefault(item.getId(), choose.isChecked() ? 0 : 1);//0是默认
            }
        });
    }

    public interface UpdateDefaultListner {
        void updateDefault(int addressId, int whetherDefault);
    }
}
