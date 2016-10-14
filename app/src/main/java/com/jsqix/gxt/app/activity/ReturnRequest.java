package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.gxt.app.utils.OrderOpUtils;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

@ContentView(R.layout.activity_return_request)
public class ReturnRequest extends BaseToolActivity implements OrderOpUtils.DataResultListener {
    @ViewInject(R.id.spinner)
    private Spinner spinner;
    @ViewInject(R.id.et_depict)
    private EditText depict;
    int type, detailId;

    private OrderOpUtils opUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.return_request));
    }

    @Override
    protected void initView() {
        opUtils = new OrderOpUtils(this);
        opUtils.setResultListener(this);
    }

    @Override
    protected void initVariable() {
        super.initVariable();
        type = getIntent().getIntExtra(Constant.ORDER_TYPE, 3);
        detailId = getIntent().getIntExtra(Constant.DATA, 0);
    }

    @Event(R.id.bt_submit)
    private void returnClick(View v) {
        if (StringUtils.isEmpty(CommUtils.textToString(depict))) {
            Utils.makeToast(this, "请填写退货理由");
        } else {
            opUtils.orderBuyerOperate(type, -1, detailId, -1, "", (spinner.getSelectedItemPosition() + 1) + "", CommUtils.textToString(depict));
        }
    }

    @Override
    public void refreshData() {
        finish();
    }
}
