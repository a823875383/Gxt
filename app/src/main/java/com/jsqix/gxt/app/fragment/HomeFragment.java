package com.jsqix.gxt.app.fragment;


import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.adapter.HomeGoodsAdapter;
import com.jsqix.gxt.app.obj.ClassifyResult;
import com.jsqix.gxt.app.obj.HomeMerchandiseResult;
import com.jsqix.gxt.app.utils.Constant;
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
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * 首页
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements HttpGet.InterfaceHttpGet, PullToRefreshBase.OnRefreshListener2<GridView> {
    @ViewInject(R.id.gridView)
    PullToRefreshGridView gridView;
    @ViewInject(R.id.title_bar)
    private RelativeLayout title_bar;

    private int pageNum = 1;
    private boolean hasNext = true;
    private List<HomeMerchandiseResult.ObjBean.ItemListBean> data = new ArrayList<>();
    private HomeGoodsAdapter adapter;


    final static int DATA_LIST = 0x0001, CLASSIFY_LEFT = 0x0010, CLASSIFY_RIGHT = 0x0011;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) title_bar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext), 0, 0);
        title_bar.setLayoutParams(lp);

        gridView.setHeaderLayout(new RefreshHeader(mContext));
        gridView.setFooterLayout(new RefreshFooter(mContext));
        adapter = new HomeGoodsAdapter(mContext, R.layout.item_goods_purchase, data);
        gridView.setAdapter(adapter);
        gridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView.setOnRefreshListener(this);

        getGoodsData();
    }

    private void getGoodsData() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        paras.put("pageNum", pageNum);
        paras.put("pageSize", Constant.PAGESIZE);
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.execute(RequestIP.GET_GOODS_LIST);
        get.setResultCode(DATA_LIST);
    }

    @Override
    protected void getArgument() {

    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case DATA_LIST:
                dataResult(result);
                break;
            case CLASSIFY_LEFT:
                ClassifyResult classifyResult = new Gson().fromJson(result, ClassifyResult.class);
                if (classifyResult != null) {
                } else {
                    if (classifyResult.getCode().equals("000")) {

                    } else {
                        Utils.makeToast(mContext, classifyResult.getMsg());
                    }
                    Utils.makeToast(mContext, getString(R.string.network_timeout));
                }
                break;
            case CLASSIFY_RIGHT:
                break;
        }

    }

    private void dataResult(String result) {
        HomeMerchandiseResult merchandiseResult = new Gson().fromJson(result, HomeMerchandiseResult.class);
        if (merchandiseResult != null) {
            if (merchandiseResult.getCode().equals("000")) {
                if (pageNum == 1) {
                    data.clear();
                }
                hasNext = merchandiseResult.getObj().isHas_next_page();
                data.addAll(merchandiseResult.getObj().getItem_list());
                adapter.notifyDataSetChanged();
            } else {
                Utils.makeToast(mContext, merchandiseResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
        gridView.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
        pageNum = 1;
        getGoodsData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
        if (hasNext) {
            pageNum++;
            getGoodsData();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gridView.onRefreshComplete();
                    Utils.makeToast(mContext, getString(R.string.no_more_data));
                }
            }, 1000);
        }
    }

    @Event(R.id.tv_classify)
    private void classifyClick(View v) {
        getClassifyLeft();
    }

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
}
