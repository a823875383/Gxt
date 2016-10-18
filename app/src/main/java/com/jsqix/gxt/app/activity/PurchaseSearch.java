package com.jsqix.gxt.app.activity;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * 商品采购搜索
 */
public class PurchaseSearch extends MerchandiseSearch {

    @Override
    void search(String key) {
        finish();
    }

    @Override
    protected void initVariable() {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
