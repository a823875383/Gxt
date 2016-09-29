package com.jsqix.gxt.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.ClassifyResult;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dq on 2016/9/29.
 */

public class ClassifyLeftAdapter extends CommonAdapter<ClassifyResult.ObjBean> {
    private int selectPosition = 0;

    public ClassifyLeftAdapter(Context context, int layoutId, List<ClassifyResult.ObjBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ClassifyResult.ObjBean item, int position) {
        LinearLayout lay = viewHolder.getView(R.id.layout_classify);
        TextView tv = viewHolder.getView(R.id.tv_name);
        tv.setText(item.getName());
        if (selectPosition == position) {
            lay.setBackgroundDrawable(mContext.getResources().getDrawable(R.mipmap.bg_classify_left));
            tv.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            lay.setBackgroundColor(Color.TRANSPARENT);
            tv.setTextColor(mContext.getResources().getColor(R.color.font_black));
        }
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}
