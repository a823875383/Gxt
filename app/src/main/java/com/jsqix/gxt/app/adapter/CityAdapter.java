package com.jsqix.gxt.app.adapter;

import android.content.Context;

import com.jsqix.gxt.app.obj.CityObj;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by dongqing on 16/10/8.
 */

public class CityAdapter extends CommonAdapter<CityObj> {
    public CityAdapter(Context context, int layoutId, List<CityObj> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CityObj item, int position) {
        viewHolder.setText(android.R.id.text1, item.getCity_name());

    }
}
