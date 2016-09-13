package com.jsqix.gxt.app.adapter;

import android.content.Context;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 16/9/13.
 */
public class OrderAdapter extends CommonAdapter {

    public OrderAdapter(Context context, final int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

    }
}
