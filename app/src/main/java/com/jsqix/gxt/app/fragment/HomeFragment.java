package com.jsqix.gxt.app.fragment;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.MerchandiseInfo;
import com.jsqix.gxt.app.adapter.ClassifyLeftAdapter;
import com.jsqix.gxt.app.adapter.ClassifyRightAdapter;
import com.jsqix.gxt.app.adapter.HomeGoodsAdapter;
import com.jsqix.gxt.app.obj.ClassifyResult;
import com.jsqix.gxt.app.obj.HomeMerchandiseResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 首页
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<GridView>, HomeGoodsAdapter.CartListener {
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;
    @ViewInject(R.id.refreshGridView)
    PullToRefreshGridView refreshGridView;
    @ViewInject(R.id.gridView)
    private GridView gridView;
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.layout_classify)
    private LinearLayout layoutClassify;
    @ViewInject(R.id.tv_all)
    private TextView allClassify;
    @ViewInject(R.id.tv_name)
    private TextView classifyName;

    private int pageNum = 1;
    private boolean hasNext = true;
    private List<HomeMerchandiseResult.ObjBean.ItemListBean> merchandiseData = new ArrayList<>();
    private HomeGoodsAdapter goodsAdapter;
    private List<ClassifyResult.ObjBean> leftData = new ArrayList<>();
    private ClassifyLeftAdapter leftAdapter;
    private List<ClassifyResult.ObjBean> rightData = new ArrayList<>();
    private ClassifyRightAdapter rightAdapter;


    final static int DATA_LIST = 0x0001, CLASSIFY_LEFT = 0x0010, CLASSIFY_RIGHT = 0x0011, ADD_CART = 0x0100;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) title_bar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        title_bar.setLayoutParams(lp);

        refreshGridView.setHeaderLayout(new RefreshHeader(mContext));
        refreshGridView.setFooterLayout(new RefreshFooter(mContext));
        goodsAdapter = new HomeGoodsAdapter(mContext, R.layout.item_goods_purchase, merchandiseData);
        goodsAdapter.setCartListener(this);
        refreshGridView.setAdapter(goodsAdapter);
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshGridView.setOnRefreshListener(this);

        leftAdapter = new ClassifyLeftAdapter(mContext, R.layout.item_classify_left, leftData);
        listView.setAdapter(leftAdapter);
        rightAdapter = new ClassifyRightAdapter(mContext, R.layout.item_classify_right, rightData);
        gridView.setAdapter(rightAdapter);
        getGoodsData();
    }

    @Override
    protected void getArgument() {

    }

    int oneClassifyId = -1, twoClassifyId = -1;
    String productName = "";

    /**
     * 获取商品
     */
    private void getGoodsData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        paras.put("pageNum", pageNum);
        paras.put("pageSize", Constant.PAGE_SIZE);
        if (oneClassifyId != -1) {
            paras.put("oneClassifyId", oneClassifyId);
        }
        if (twoClassifyId != -1) {
            paras.put("twoClassifyId", twoClassifyId);
        }
        if (!StringUtils.isEmpty(productName)) {
            paras.put("productName", productName);
        }
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.GET_GOODS_LIST);
        get.setResultCode(DATA_LIST);
    }

    /**
     * 获取一级分类
     */
    private void getClassifyLeft() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.GET_GOODS_ONE_CLASSIFY);
        get.setResultCode(CLASSIFY_LEFT);
    }

    /**
     * 获取二级分类
     *
     * @param oneClassifyId
     */
    private void getClassifyRight(int oneClassifyId) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        paras.put("oneClassifyId", oneClassifyId);
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.GET_GOODS_TWO_CLASSIFY);
        get.setResultCode(CLASSIFY_RIGHT);
    }

    /**
     * 加入购物车
     *
     * @param productId
     * @param orderCounts
     */
    @Override
    public void addCart(String productId, String orderCounts) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        paras.put("productId", productId);
        paras.put("orderCounts", orderCounts);
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.ADD_CART);
        get.setResultCode(ADD_CART);
    }

    /**
     * 接口回调
     *
     * @param resultCode
     * @param result
     */
    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case DATA_LIST:
                dataResult(result);
                break;
            case CLASSIFY_LEFT:
                classifyLeft(result);
                break;
            case CLASSIFY_RIGHT:
                classifyRight(result);
                break;
            case ADD_CART:
                cartResult(result);
                break;
        }

    }

    private void cartResult(String result) {
        BaseBean baseBean = new Gson().fromJson(result, BaseBean.class);
        if (baseBean != null) {
            Utils.makeToast(mContext, baseBean.getMsg());
        } else {
            Utils.makeToast(mContext, mContext.getString(R.string.network_timeout));
        }
    }

    private void classifyRight(String result) {
        ClassifyResult classifyResult = new Gson().fromJson(result, ClassifyResult.class);
        if (classifyResult != null) {
            if (classifyResult.getCode().equals("000")) {
                rightData.clear();
                rightData.addAll(classifyResult.getObj());
                rightAdapter.notifyDataSetChanged();
            } else {
                Utils.makeToast(mContext, classifyResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
    }

    private void classifyLeft(String result) {
        ClassifyResult classifyResult = new Gson().fromJson(result, ClassifyResult.class);
        if (classifyResult != null) {
            if (classifyResult.getCode().equals("000")) {
                leftData.clear();
                leftData.addAll(classifyResult.getObj());
                leftAdapter.notifyDataSetChanged();
//                listView.performItemClick(listView.getChildAt(0), 0, listView.getItemIdAtPosition(0));
                oneClassifyId = leftData.get(leftAdapter.getSelectPosition()).getId();
                getClassifyRight(oneClassifyId);
            } else {
                Utils.makeToast(mContext, classifyResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
    }

    private void dataResult(String result) {
        HomeMerchandiseResult merchandiseResult = new Gson().fromJson(result, HomeMerchandiseResult.class);
        if (merchandiseResult != null) {
            if (merchandiseResult.getCode().equals("000")) {
                if (pageNum == 1) {
                    merchandiseData.clear();
                }
                hasNext = merchandiseResult.getObj().isHas_next_page();
                merchandiseData.addAll(merchandiseResult.getObj().getItem_list());
                goodsAdapter.notifyDataSetChanged();
            } else {
                Utils.makeToast(mContext, merchandiseResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
        new Handler().postDelayed(() -> {
            refreshGridView.onRefreshComplete();
        }, 500);
    }

    /**
     * 刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
        pageNum = 1;
        getGoodsData();
    }

    /**
     * 加载
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
        if (hasNext) {
            pageNum++;
            getGoodsData();
        } else {
            new Handler().postDelayed(() -> {
                refreshGridView.onRefreshComplete();
                Utils.makeToast(mContext, getString(R.string.no_more_data));
            }, 1000);
        }
    }

    /**
     * 点击分类
     *
     * @param v
     */
    @Event(R.id.tv_classify)
    private void classifyClick(View v) {
//        if(layoutClassify.getVisibility()==View.GONE){
        layoutClassify.setVisibility(View.VISIBLE);
        refreshGridView.setVisibility(View.GONE);
        getClassifyLeft();
//    }
//        else {

//        }
    }

    /**
     * 点击全部
     *
     * @param v
     */
    @Event(R.id.tv_all)
    private void allClick(View v) {
        classifyName.setText(getString(R.string.all));
        layoutClassify.setVisibility(View.GONE);
        refreshGridView.setVisibility(View.VISIBLE);
        oneClassifyId = -1;
        twoClassifyId = -1;
        pageNum = 1;
        getGoodsData();

    }

    /**
     * 点击一级分类
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Event(value = R.id.listView, type = AdapterView.OnItemClickListener.class)
    private void leftClick(AdapterView<?> adapterView, View view, int i, long l) {
        listView.setSelection(i);
        oneClassifyId = leftData.get(i).getId();
        leftAdapter.setSelectPosition(i);
        leftAdapter.notifyDataSetChanged();
        getClassifyRight(leftData.get(i).getId());
    }

    /**
     * 点击二级分类
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Event(value = R.id.gridView, type = AdapterView.OnItemClickListener.class)
    private void rightClick(AdapterView<?> adapterView, View view, int i, long l) {
        twoClassifyId = rightData.get(i).getId();
        classifyName.setText(rightData.get(i).getName());
        pageNum = 1;
        getGoodsData();
        layoutClassify.setVisibility(View.GONE);
        refreshGridView.setVisibility(View.VISIBLE);
    }

    /**
     * 点击商品
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Event(value = R.id.refreshGridView, type = AdapterView.OnItemClickListener.class)
    private void goodsClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(mContext, MerchandiseInfo.class);
        startActivity(intent);
    }


}
