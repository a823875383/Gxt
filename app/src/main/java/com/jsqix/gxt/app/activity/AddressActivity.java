package com.jsqix.gxt.app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.CityUtils;
import com.jsqix.gxt.app.utils.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;

@ContentView(R.layout.activity_address)
public class AddressActivity extends BaseToolActivity {
    @ViewInject(R.id.tv_area)
    private TextView area;
    private String title;
    private OptionsPickerView<String> areaPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(title);
        mRight.setText(getString(R.string.save));
        mRight.setTextColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void initView() {
        areaPicker = new OptionsPickerView<>(this);
        final ArrayList<String> pros = CityUtils.getInstance(this).getPro();
        final ArrayList<ArrayList<String>> cities = CityUtils.getInstance(this).getCity();
        final ArrayList<ArrayList<ArrayList<String>>> areas = CityUtils.getInstance(this).getArea();
        areaPicker.setPicker(pros, cities, areas, true);
        areaPicker.setCyclic(false);
        int option1 = pros.size() / 2;
        int option2 = cities.get(option1).size() / 2;
        int option3 = areas.get(option1).get(option2).size() / 2;
        areaPicker.setSelectOptions(option1, option2, option3);
        areaPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                area.setCompoundDrawables(null, null, null, drawable);
                area.setText(pros.get(options1) + "-" + cities.get(options1).get(option2) + "-" + areas.get(options1).get(option2).get(options3));
            }
        });
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        title = getIntent().getExtras().getString(Constant.TITLE, "");

    }

    @Event(R.id.tv_area)
    private void areaClick(View v) {
        areaPicker.show();
    }
}
