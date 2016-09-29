package com.jsqix.gxt.app.adapter;

import android.content.Context;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.ClassifyResult;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dq on 2016/9/29.
 */

public class ClassifyRightAdapter extends CommonAdapter<ClassifyResult.ObjBean> {
    public ClassifyRightAdapter(Context context, int layoutId, List<ClassifyResult.ObjBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ClassifyResult.ObjBean item, int position) {
        viewHolder.setText(R.id.tv_goods_name, item.getName());
    }
}
