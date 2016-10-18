package com.jsqix.gxt.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.GoodsDetailResult;
import com.jsqix.gxt.app.obj.SpecResult;
import com.jsqix.gxt.app.obj.StockResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.CustomDialog;

@ContentView(R.layout.activity_merchandise_info)
public class MerchandiseInfo extends BaseCompat implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.title_bar)
    private RelativeLayout titleBar;

    @ViewInject(R.id.fragment_merchandise)
    private LinearLayout merchandise;
    @ViewInject(R.id.convenientBanner)
    private ConvenientBanner<String> banner;
    @ViewInject(R.id.tv_goods_name)
    private TextView goodsName;
    @ViewInject(R.id.tv_goods_price)
    private TextView goodsPrice;
    @ViewInject(R.id.tv_goods_stock)
    private TextView goodsStock;
    @ViewInject(R.id.tv_goods_moq)
    private TextView goodsMOQ;
    @ViewInject(R.id.tv_spec)
    private TextView tvSpec;

    @ViewInject(R.id.rg_nav_content)
    private RadioGroup rg_nav_content;
    @ViewInject(R.id.iv_nav_indicator)
    private ImageView iv_nav_indicator;
    @ViewInject(R.id.fragment_mer_detail)
    private LinearLayout merDetail;
    @ViewInject(R.id.webView)
    private WebView webView;

    /**
     * 规格选择
     */
    private ImageView goods_image;
    private TextView goods_price;
    private TextView goods_stock;
    private TextView goods_moq;
    private TextView select_spec;
    private LinearLayout layout_spec;
    private ImageView iv_reduce;
    private TextView tv_num;
    private ImageView iv_add;
    private Button button_buy;
    private Button button_cart;


    @ViewInject(R.id.bt_buy)
    private Button buy;
    @ViewInject(R.id.bt_cart)
    private Button cart;

    private CustomDialog specDialog;

    private int productId, goodsId;
    private int currentIndicatorLeft = 0;
    private List<String> bannerData;//轮播图
    private String main_image;//主图
    private List<ArrayList<RadioButton>> checkBoxLists;//存放所有规格
    private String[] tags;//存放已选择的规格
    private List<SparseArray> classify;//存放所有分类与tags同大小

    final static int GOODS_INFO = 0x0001, GOODS_SPEC = 0x0010, GOODS_STOCK = 0x0011, ADD_CART = 0x0100;
    private GoodsDetailResult.ObjEntity defaultProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) titleBar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
        titleBar.setLayoutParams(lp);

    }

    @Override
    protected void initView() {
        initWebView();

        initSpecDialog();

        getProductDetail();

    }

    @Override
    protected void initVariable() {
        productId = getIntent().getExtras().getInt(Constant.ID);
    }

    private void initSpecDialog() {
        specDialog = new CustomDialog(this, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.view_merchandise_spec, null);

        goods_image = (ImageView) view.findViewById(R.id.iv_goods_img);
        goods_price = (TextView) view.findViewById(R.id.tv_goods_price);
        goods_stock = (TextView) view.findViewById(R.id.tv_goods_stock);
        goods_moq = (TextView) view.findViewById(R.id.tv_goods_moq);
        select_spec = (TextView) view.findViewById(R.id.tv_select_spec);
        layout_spec = (LinearLayout) view.findViewById(R.id.lin_spec);
        iv_reduce = (ImageView) view.findViewById(R.id.iv_reduce);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        iv_add = (ImageView) view.findViewById(R.id.iv_add);
        button_buy = (Button) view.findViewById(R.id.bt_buy);
        button_cart = (Button) view.findViewById(R.id.bt_cart);
        iv_reduce.setOnClickListener(v -> {
            int num = StringUtils.toInt(CommUtils.textToString(tv_num));
            if (num > StringUtils.toInt(CommUtils.textToString(goods_moq))) {
                num--;
                tv_num.setText("" + num);
            }
        });
        iv_add.setOnClickListener(v -> {
            int num = StringUtils.toInt(CommUtils.textToString(tv_num));
            if (num < StringUtils.toInt(CommUtils.textToString(goods_stock))) {
                num++;
                tv_num.setText("" + num);
            }
        });
        button_buy.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderSubmit.class);
            intent.putExtra(Constant.BUY_TYPE, 0);
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            map.put("cartNumber", CommUtils.textToString(tv_num));
            map.put("productId", productId);
            list.add(map);
            intent.putExtra(Constant.DATA, new Gson().toJson(list));
            startActivity(intent);
            specDialog.dismiss();
        });
        button_cart.setOnClickListener(v -> {
            addCart(productId + "", CommUtils.textToString(tv_num));
            specDialog.dismiss();
        });
        specDialog.setView(view, Gravity.BOTTOM);

    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);

        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);

        settings.setDefaultTextEncodingName("UTF-8");
        settings.setTextZoom(200);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(MerchandiseInfo.this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            x.image().bind(imageView, Constant.IMG_SERVICE + data);
        }
    }

    private void initBanner() {
        banner.setCanLoop(true);
        banner.setPointViewVisible(true);
//        banner.startTurning(2000);
        banner.stopTurning();
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerData);
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected boolean isShowNetOff() {
        return true;
    }

    @Override
    protected boolean isStatusWhite() {
        return true;
    }

    @Event(R.id.tv_left)
    private void backClick(View v) {
        finish();
    }

    @Event(R.id.bt_buy)
    private void buyClick(View v) {
        Intent intent = new Intent(this, OrderSubmit.class);
        intent.putExtra(Constant.BUY_TYPE, 0);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("cartNumber", defaultProduct.getMin_num());
        map.put("productId", productId);
        list.add(map);
        intent.putExtra(Constant.DATA, new Gson().toJson(list));
        startActivity(intent);
    }

    @Event(R.id.bt_cart)
    private void cartClick(View v) {
        addCart(productId + "", defaultProduct.getMin_num() + "");
    }


    @Event(value = R.id.rg_nav_content, type = RadioGroup.OnCheckedChangeListener.class)
    private void changeClick(RadioGroup group, @IdRes int checkedId) {
        int index = 0;
        merchandise.setVisibility(View.GONE);
        merDetail.setVisibility(View.GONE);
        switch (checkedId) {
            case R.id.rb_goods:
                index = 0;
                merchandise.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_detail:
                index = 1;
                merDetail.setVisibility(View.VISIBLE);
                break;
        }
        if (rg_nav_content.getChildAt(index) != null) {

            TranslateAnimation animation = new TranslateAnimation(
                    currentIndicatorLeft,
                    ((RadioButton) rg_nav_content
                            .getChildAt(index)).getLeft(),
                    0f, 0f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(100);
            animation.setFillAfter(true);

            // 执行位移动画
            iv_nav_indicator.startAnimation(animation);
            currentIndicatorLeft = ((RadioButton) rg_nav_content.getChildAt(index)).getLeft();
        }
    }

    @Event(R.id.rel_spec)
    private void specClick(View v) {
        if (layout_spec.getChildCount() > 0) {
            specDialog.show();
        } else {
            getGoodsStandard();
        }
    }

    /**
     * 获取商品详情
     */

    private void getProductDetail() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("productId", productId);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.GET_PRODUCT_DETAIL);
        get.setResultCode(GOODS_INFO);
    }

    /**
     * 查询规格
     */
    private void getGoodsStandard() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("goodsId", goodsId);
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
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
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("goodsId", goodsId);
        paras.put("timeStamp", System.currentTimeMillis());
        for (int i = 0; i < tags.length; i++) {
            String key = "tag" + (i + 1);
            String[] str = tags[i].split("-");
            String value = str[0].split(":")[1] + "-" + str[1].split(":")[1];
            paras.put(key, value);
        }
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.QUERY_PRODUCT_BY_STANDARD);
        get.setResultCode(GOODS_STOCK);

    }

    /**
     * 加入购物车
     *
     * @param productId
     * @param orderCounts
     */
    public void addCart(String productId, String orderCounts) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("productId", productId);
        paras.put("orderCounts", orderCounts);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {
                loadingUtils.show();
            }
        };
        get.execute(RequestIP.ADD_CART);
        get.setResultCode(ADD_CART);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case GOODS_INFO:
                InfoResult(result);
                break;
            case GOODS_SPEC:
                specResult(result);
                break;
            case GOODS_STOCK:
                stockResult(result);
                break;
            case ADD_CART:
                cartResult(result);
                break;
        }
        loadingUtils.dismiss();
    }

    private void cartResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(this, baseBean.getMsg());
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void stockResult(String result) {
        StockResult stockResult = new Gson().fromJson(result, StockResult.class);
        if (stockResult != null) {
            if (stockResult.getCode().equals("000")) {
                productId = stockResult.getObj().getPRODUCT_ID();
                goods_stock.setText(stockResult.getObj().getSTOCK() + "");
                goods_moq.setText(stockResult.getObj().getMIN_NUM() + "");
                tv_num.setText(CommUtils.textToString(goods_moq));
                getProductDetail();
            } else {
                Utils.makeToast(this, stockResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    /**
     * 商品详情
     *
     * @param result
     */
    private void InfoResult(String result) {
        GoodsDetailResult detailResult = new Gson().fromJson(result, GoodsDetailResult.class);
        if (detailResult != null) {
            if (detailResult.getCode().equals("000")) {
                defaultProduct = detailResult.getObj();
                goodsName.setText(defaultProduct.getProduct_name());
                goodsPrice.setText(getString(R.string.rmb) + detailResult.getObj().getWholesale_price() + "");
                goodsStock.setText(detailResult.getObj().getStock() + "");
                goodsMOQ.setText(detailResult.getObj().getMin_num() + "");
                StringBuffer spec = new StringBuffer(detailResult.getObj().getTag_detail_name1());
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name2())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name2());
                }
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name3())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name3());
                }
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name4())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name4());
                }
                if (!StringUtils.isEmpty(detailResult.getObj().getTag_detail_name5())) {
                    spec.append(" " + detailResult.getObj().getTag_detail_name5());
                }
                webView.loadData(detailResult.getObj().getGoods_detail(), "text/html; charset=UTF-8", null);
                tvSpec.setText("已选" + " \"" + spec.toString() + "\"");
                goodsId = detailResult.getObj().getGoods_id();

                main_image = detailResult.getObj().getGoods_image();
                bannerData = detailResult.getObj().getImage_list();
                bannerData.add(0, main_image);
                initBanner();
            } else {
                Utils.makeToast(this, detailResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
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
                x.image().bind(goods_image, Constant.IMG_SERVICE + main_image);
                goods_price.setText(CommUtils.textToString(goodsPrice));
                goods_stock.setText(CommUtils.textToString(goodsStock));
                goods_moq.setText(CommUtils.textToString(goodsMOQ));
                select_spec.setText(CommUtils.textToString(tvSpec));
                tv_num.setText(CommUtils.textToString(goods_moq));
                List<SpecResult.ObjBean> objBeanList = specResult.getObj();
                tags = new String[objBeanList.size()];
                checkBoxLists = new ArrayList<>();
                classify = new ArrayList<>();
                for (int i = 0; i < objBeanList.size(); i++) {
                    View specView = LayoutInflater.from(this).inflate(R.layout.layout_spec_info, null);
                    TextView propertyName = (TextView) specView
                            .findViewById(R.id.tv_property_name);
                    LinearLayout propertyLay = (LinearLayout) specView
                            .findViewById(R.id.lin_property);
                    SpecResult.ObjBean objBean = objBeanList.get(i);
                    SparseArray sparseArray = new SparseArray();
                    sparseArray.put(0, objBean.getName());
                    sparseArray.put(1, true);
                    classify.add(sparseArray);
                    List<SpecResult.ObjBean.ItemListBean> itemListBean = objBean.getItem_list();
                    int num = (int) (Math.ceil(itemListBean.size() / 4.0));
                    LinearLayout[] linearLayout = new LinearLayout[num];
                    for (int n = 0; n < num; n++) {
                        linearLayout[n] = new LinearLayout(this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearLayout[n].setLayoutParams(lp);
                        linearLayout[n].setOrientation(LinearLayout.HORIZONTAL);
                    }
                    ArrayList<RadioButton> boxs = new ArrayList<>();
                    for (int n = 0; n < num; n++) {
                        for (int j = 4 * n; j <= 4 * n + 3 && j < itemListBean.size(); j++) {
                            SpecResult.ObjBean.ItemListBean listBean = itemListBean.get(j);
                            View itemView = LayoutInflater.from(this).inflate(R.layout.layout_spec_item_info, null);
                            RadioButton checkBox = (RadioButton) itemView.findViewById(R.id.checkbox);
                            checkBox.setText(listBean.getName());
                            String tag = objBean.getName() + ":" + objBean.getId() + "-" + listBean.getName() + ":" + listBean.getId();
                            checkBox.setTag(tag);
                            if (setDefaultCheck(listBean.getId())) {
                                checkBox.setChecked(true);
                                tags[i] = checkBox.getTag() + "";
                            }
                            linearLayout[n].addView(itemView);
                            boxs.add(checkBox);
                        }
                        checkBoxLists.add(boxs);

                    }
                    for (int n = 0; n < num; n++) {
                        propertyLay.addView(linearLayout[n]);
                    }
                    propertyName.setText(objBean.getName());
                    layout_spec.addView(specView);

                }
                for (int i = 0; i < checkBoxLists.size(); i++) {
                    final ArrayList<RadioButton> boxs = checkBoxLists.get(i);
                    for (int j = 0; j < boxs.size(); j++) {
                        RadioButton box = boxs.get(j);
                        box.setOnClickListener(new myStandardClick(i, j));
                    }
                }
                specDialog.setParas(0.75f);
                specDialog.show();
            } else {
                Utils.makeToast(this, specResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    /**
     * 默认选择的规格
     *
     * @param id
     * @return
     */
    private boolean setDefaultCheck(int id) {
        if (defaultProduct.getTag_detail_no1() == id) {
            return true;
        } else if (defaultProduct.getTag_detail_no2() == id) {
            return true;
        } else if (defaultProduct.getTag_detail_no3() == id) {
            return true;
        } else if (defaultProduct.getTag_detail_no4() == id) {
            return true;
        } else if (defaultProduct.getTag_detail_no4() == id) {
            return true;
        }
        return false;
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
                classify.get(i).put(1, true);
            } else {
                tags[i] = "";
                classify.get(i).put(1, false);
            }
            if (notEmptySize(tags) == tags.length) {
                StringBuffer buffer = new StringBuffer("已选 \"");
                for (String tag : tags) {
                    buffer.append(tag.split("-")[1].split(":")[0] + " ");

                }
                buffer.deleteCharAt(buffer.length() - 1);
                buffer.append("\"");
                select_spec.setText(buffer.toString());
                queryProductByStandard();
            } else {
                StringBuffer buffer = new StringBuffer("请选择");
                for (SparseArray name : classify) {
                    if (Boolean.parseBoolean(name.get(1).toString()) == false) {
                        buffer.append(" " + name.get(0));
                    }
                }
                select_spec.setText(buffer.toString());
            }

        }

    }


    private int notEmptySize(String[] tags) {
        int num = 0;
        for (int i = 0; i < tags.length; i++) {
            if (!tags[i].equals("")) {
                num++;
            }
        }
        return num;

    }

}
