package com.jsqix.gxt.app.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.utils.GoodsOpUtils;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;
import gxt.jsqix.com.mycommon.base.view.RefreshFooter;
import gxt.jsqix.com.mycommon.base.view.RefreshHeader;

/**
 * Created by dongqing on 16/9/20.
 * 搜索
 */
@ContentView(R.layout.activity_merchandise_search)
public abstract class MerchandiseSearch extends BaseCompat implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @ViewInject(R.id.refreshListView)
    protected PullToRefreshListView refreshListView;
    @ViewInject(R.id.title_bar)
    private RelativeLayout titleBar;
    @ViewInject(R.id.tv_left)
    private TextView mBack;
    @ViewInject(R.id.tv_title)
    private TextView mTitle;
    @ViewInject(R.id.lin_search)
    private LinearLayout searchLayout;
    @ViewInject(R.id.et_search)
    private EditText searchEdit;
    @ViewInject(R.id.tv_right)
    private TextView mRight;
    @ViewInject(R.id.empty_view)
    protected LinearLayout emptyView;
    @ViewInject(R.id.tv_empty)
    private TextView emptyMsg;


    protected GoodsOpUtils opUtils;
    protected String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(R.id.tv_left)
    private void backClick(View v) {
        finish();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
    }

    @Event(R.id.tv_right)
    private void searchClick(View v) {
        if (mRight.getText().toString().equals(getString(R.string.search))) {
            //搜索
            key = searchEdit.getText().toString().trim();
            if (StringUtils.isEmpty(key)) {
                Utils.makeToast(this, getString(R.string.goods_search));
            } else {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_search_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mRight.setCompoundDrawables(drawable, null, null, null);
                mRight.setText("");

                mTitle.setText(key);
                mTitle.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.GONE);
                searchEdit.setText("");
                search(key);
                refreshListView.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
            }
        } else {
            mRight.setCompoundDrawables(null, null, null, null);
            mRight.setText(getString(R.string.search));
            mTitle.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
            refreshListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchEdit, InputMethodManager.SHOW_FORCED);
        }
    }


    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) titleBar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
        titleBar.setLayoutParams(lp);

        refreshListView.setHeaderLayout(new RefreshHeader(this));
        refreshListView.setFooterLayout(new RefreshFooter(this));

        emptyMsg.setText("对不起，您搜索的商品不存在！");
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    abstract void search(String key);

    @Override
    protected boolean isShowNetOff() {
        return false;
    }

    @Override
    protected boolean isStatusWhite() {
        return true;
    }
}

