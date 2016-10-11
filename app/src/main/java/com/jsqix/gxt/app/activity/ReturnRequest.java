package com.jsqix.gxt.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

import static com.jsqix.gxt.app.activity.OrderActivity.ORDER_OP;

@ContentView(R.layout.activity_return_request)
public class ReturnRequest extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.spinner)
    private Spinner spinner;
    @ViewInject(R.id.et_depict)
    private EditText depict;
    int type, detailId;

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
            orderOperate(type, detailId, (spinner.getSelectedItemPosition() + 1) + "", CommUtils.textToString(depict));
        }
    }

    /**
     * 订单操作
     *
     * @param type     操作事项 3：退款 4：退货
     * @param detailId 订单详情id
     * @param reason   退货原因
     * @param dec      退货理由
     */
    private void orderOperate(int type, int detailId, String reason, String dec) {
        Map<String, Object> unParas = new HashMap<>();

        if (detailId != -1) {
            unParas.put("detailId", detailId);
        }

        if (!StringUtils.isEmpty(reason)) {
            unParas.put("reason", reason);
        }
        if (!StringUtils.isEmpty(dec)) {
            unParas.put("dec", dec);
        }
        Map<String, Object> paras = new HashMap<>();
        paras.put("type", type);
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, unParas, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ORDER_OP);
        get.execute(RequestIP.UPT_BUYER_ORDER);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
            if (baseBean.getCode().equals("000")) {
                finish();
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }
}
