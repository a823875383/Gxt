package com.jsqix.gxt.app.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.SpecResult;
import com.jsqix.gxt.app.obj.StockResult;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

/**
 * Created by dongqing on 2016/10/14.
 */

public class GoodsDialogUtils implements HttpGet.InterfaceHttpGet {
    private CustomDialog confirmDialog;
    private TextView confirmTvMsg;
    private Button confirmBtLeft;

    private CustomDialog operateDialog;
    private RadioGroup operateRadio;
    private LinearLayout operateSpec;
    private EditText operateStock;
    private EditText operateMoq;
    private Button operateBt;

    private List<ArrayList<RadioButton>> checkBoxLists;//存放所有规格
    private String[] tags;//存放已选择的规格
    final static int GOODS_SPEC = 0x0010, GOODS_STOCK = 0x0011;
    private int goodsId, productId;

    private Context mContext;
    private MerchandiseOpListener opListener;

    public GoodsDialogUtils(Context mContext) {
        this.mContext = mContext;
        initDialog();
    }


    private void initDialog() {
        confirmDialog = new CustomDialog(mContext);
        View confirmView = LayoutInflater.from(mContext).inflate(R.layout.view_order_refund, null);
        confirmTvMsg = (TextView) confirmView.findViewById(R.id.tv_msg);
        confirmBtLeft = (Button) confirmView.findViewById(R.id.bt_confirm);
        Button confirmBtRight = (Button) confirmView.findViewById(R.id.bt_cancel);
        confirmTvMsg.setText("确定上架该商品？");
        confirmBtRight.setOnClickListener(v -> {
            confirmDialog.dismiss();
        });
        confirmBtLeft.setOnClickListener(new OpListener());
        confirmDialog.setView(confirmView);

        operateDialog = new CustomDialog(mContext);
        View operateView = LayoutInflater.from(mContext).inflate(R.layout.view_supplier_merchandise, null);
        ImageView operateClose = (ImageView) operateView.findViewById(R.id.iv_close);
        operateRadio = (RadioGroup) operateView.findViewById(R.id.radio_goods);
        operateSpec = (LinearLayout) operateView.findViewById(R.id.lin_spec);
        operateStock = (EditText) operateView.findViewById(R.id.et_stock);
        operateMoq = (EditText) operateView.findViewById(R.id.et_moq);
        operateBt = (Button) operateView.findViewById(R.id.bt_submit);
        operateClose.setOnClickListener(v -> {
            operateDialog.dismiss();
        });
        operateBt.setOnClickListener(new OpListener());
        operateDialog.setView(operateView);


    }

    public void setOpListener(MerchandiseOpListener opListener) {
        this.opListener = opListener;
    }

    /**
     * 查询规格
     */
    public void getGoodsStandard() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", ((BaseCompat) mContext).aCache.getAsString(Constant.U_ID));
        paras.put("goodsId", goodsId);
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.LIST_GOODS_STANDARD);
        get.setResultCode(GOODS_SPEC);
    }

    /**
     * 根据规格查询商品信息
     */
    private void queryProductByStandard() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", ((BaseCompat) mContext).aCache.getAsString(Constant.U_ID));
        paras.put("goodsId", goodsId);
        paras.put("timeStamp", System.currentTimeMillis());
        for (int i = 0; i < tags.length; i++) {
            String key = "tag" + (i + 1);
            paras.put(key, tags[i]);
        }
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.QUERY_PRODUCT_BY_STANDARD);
        get.setResultCode(GOODS_STOCK);

    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case GOODS_SPEC:
                specResult(result);
                break;
            case GOODS_STOCK:
                stockResult(result);
                break;
        }
    }

    /**
     * 规格
     *
     * @param result
     */
    private void specResult(String result) {
        SpecResult specResult = new Gson().fromJson(result, SpecResult.class);
        if (specResult != null) {
            if (specResult.getCode().equals("000")) {
                List<SpecResult.ObjBean> objBeanList = specResult.getObj();
                tags = new String[objBeanList.size()];
                operateSpec.removeAllViewsInLayout();
                checkBoxLists = new ArrayList<>();
                for (int i = 0; i < objBeanList.size(); i++) {
                    View specView = LayoutInflater.from(mContext).inflate(R.layout.layout_spec, null);
                    TextView propertyName = (TextView) specView
                            .findViewById(R.id.tv_property_name);
                    LinearLayout propertyLay = (LinearLayout) specView
                            .findViewById(R.id.lin_property);
                    SpecResult.ObjBean objBean = objBeanList.get(i);
                    SparseArray sparseArray = new SparseArray();
                    sparseArray.put(0, objBean.getName());
                    sparseArray.put(1, true);
                    List<SpecResult.ObjBean.ItemListBean> itemListBean = objBean.getItem_list();
                    int num = (int) (Math.ceil(itemListBean.size() / 2.0));
                    LinearLayout[] linearLayout = new LinearLayout[num];
                    for (int n = 0; n < num; n++) {
                        linearLayout[n] = new LinearLayout(mContext);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearLayout[n].setLayoutParams(lp);
                        linearLayout[n].setOrientation(LinearLayout.HORIZONTAL);
                    }
                    ArrayList<RadioButton> radios = new ArrayList<RadioButton>();
                    for (int n = 0; n < num; n++) {
                        for (int j = 4 * n; j <= 4 * n + 3 && j < itemListBean.size(); j++) {
                            SpecResult.ObjBean.ItemListBean listBean = itemListBean.get(j);
                            View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_spec_item, null);
                            RadioButton radio = (RadioButton) itemView.findViewById(R.id.radio);
                            radio.setText(listBean.getName());
                            String tag = objBean.getId() + "-" + listBean.getId();
                            radio.setTag(tag);
                            linearLayout[n].addView(itemView);
                            radios.add(radio);
                        }
                        checkBoxLists.add(radios);

                    }
                    for (int n = 0; n < num; n++) {
                        propertyLay.addView(linearLayout[n]);
                    }
                    propertyName.setText(objBean.getName());
                    operateSpec.addView(specView);

                }
                for (int i = 0; i < checkBoxLists.size(); i++) {
                    final ArrayList<RadioButton> radios = checkBoxLists.get(i);
                    for (int j = 0; j < radios.size(); j++) {
                        RadioButton radio = radios.get(j);
                        radio.setOnClickListener(new myStandardClick(i, j));
                    }
                }
                operateDialog.show();
            } else {
                Utils.makeToast(mContext, specResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, mContext.getString(R.string.network_timeout));
        }
    }

    /**
     * 库存
     *
     * @param result
     */
    private void stockResult(String result) {
        StockResult stockResult = new Gson().fromJson(result, StockResult.class);
        if (stockResult != null) {
            if (stockResult.getCode().equals("000")) {
                productId = stockResult.getObj().getPRODUCT_ID();
                operateStock.setText(stockResult.getObj().getSTOCK() + "");
                operateMoq.setText(stockResult.getObj().getMIN_NUM() + "");
            } else {
                Utils.makeToast(mContext, stockResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, mContext.getString(R.string.network_timeout));
        }
    }

    /**
     * 规格点击事件
     */
    class myStandardClick implements View.OnClickListener {
        private int i, j;

        public myStandardClick(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void onClick(View v) {
            if (checkBoxLists.get(i).get(j).isChecked()) {
                for (int z = 0; z < checkBoxLists.get(i).size(); z++) {
                    if (z == j) {
                        continue;
                    }
                    checkBoxLists.get(i).get(z).setChecked(false);
                }
                String str = checkBoxLists.get(i).get(j).getTag() + "";
                tags[i] = str;

            } else {
                tags[i] = "";
            }
            if (notEmptySize(tags) == tags.length) {
                queryProductByStandard();
            }

        }

    }

    private int notEmptySize(String[] tags) {
        int num = 0;
        if (tags == null)
            return num;
        for (int i = 0; i < tags.length; i++) {
            if (tags[i]!=null&&!tags[i].equals("")) {
                num++;
            }
        }
        return num;

    }

    class OpListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_confirm:
                    if (opListener != null) {
                        opListener.updateStatus(goodsId, 1);
                    }
                    break;
                case R.id.bt_submit:
                    if (operateRadio.getCheckedRadioButtonId() == R.id.radio_1) {
                        if (opListener != null) {
                            opListener.updateStatus(goodsId, 0);
                        }
                    } else {
                        if (notEmptySize(tags) == 0) {
                            operateDialog.dismiss();
                        } else if (notEmptySize(tags) == tags.length) {
                            if (StringUtils.isEmpty(CommUtils.textToString(operateStock))) {
                                Utils.makeToast(mContext, "请输入库存量");
                            } else if (StringUtils.isEmpty(CommUtils.textToString(operateMoq))) {
                                Utils.makeToast(mContext, "请输入起订量");
                            } else {
                                if (opListener != null) {
                                    opListener.updateGoods(productId, StringUtils.toInt(CommUtils.textToString(operateStock)), StringUtils.toInt(CommUtils.textToString(operateMoq)));
                                }
                            }
                        } else {
                            Utils.makeToast(mContext, "请选择商品规格");
                        }
                    }
                    break;
            }
        }
    }

    public void dismissDialog() {
        confirmDialog.dismiss();
        operateDialog.dismiss();
    }

    public CustomDialog getConfirmDialog() {
        return confirmDialog;
    }

    public TextView getConfirmTvMsg() {
        return confirmTvMsg;
    }

    public Button getConfirmBtLeft() {
        return confirmBtLeft;
    }

    public void setConfirmBtLeft(Button confirmBtLeft) {
        this.confirmBtLeft = confirmBtLeft;
    }

    public CustomDialog getOperateDialog() {
        return operateDialog;
    }

    public void setOperateDialog(CustomDialog operateDialog) {
        this.operateDialog = operateDialog;
    }

    public RadioGroup getOperateRadio() {
        return operateRadio;
    }

    public void setOperateRadio(RadioGroup operateRadio) {
        this.operateRadio = operateRadio;
    }

    public LinearLayout getOperateSpec() {
        return operateSpec;
    }

    public void setOperateSpec(LinearLayout operateSpec) {
        this.operateSpec = operateSpec;
    }

    public EditText getOperateStock() {
        return operateStock;
    }

    public void setOperateStock(EditText operateStock) {
        this.operateStock = operateStock;
    }

    public EditText getOperateMoq() {
        return operateMoq;
    }

    public void setOperateMoq(EditText operateMoq) {
        this.operateMoq = operateMoq;
    }

    public Button getOperateBt() {
        return operateBt;
    }

    public void setOperateBt(Button operateBt) {
        this.operateBt = operateBt;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public interface MerchandiseOpListener {
        void updateStatus(int goodsId, int status);

        void updateGoods(int productId, int stock, int moq);

    }
}
