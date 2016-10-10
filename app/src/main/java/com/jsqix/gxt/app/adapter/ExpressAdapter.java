package com.jsqix.gxt.app.adapter;

import android.content.Context;

import com.jsqix.gxt.app.obj.ExpressResult;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 2016/10/10.
 */

public class ExpressAdapter extends CommonAdapter<ExpressResult.ObjBean> {
    public ExpressAdapter(Context context, int layoutId, List<ExpressResult.ObjBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ExpressResult.ObjBean item, int position) {
        viewHolder.setText(android.R.id.text1, item.getLABELNAME());
    }
}
