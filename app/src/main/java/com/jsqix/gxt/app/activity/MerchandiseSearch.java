package com.jsqix.gxt.app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsqix.gxt.app.R;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseCompat;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * Created by dongqing on 16/9/20.
 * 搜索
 */
@ContentView(R.layout.activity_merchandise_search)
public abstract class MerchandiseSearch extends BaseCompat {
    @ViewInject(R.id.listView)
    public ListView listView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(R.id.tv_left)
    private void backClick(View v) {
        finish();
    }

    @Event(R.id.tv_right)
    private void searchClick(View v) {
        if (mRight.getText().toString().equals(getString(R.string.search))) {
            //搜索
            String key = searchEdit.getText().toString().trim();
            if (StringUtils.isEmpty(key)) {
                Utils.makeToast(this, getString(R.string.goods_search));
            } else {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_search);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mRight.setCompoundDrawables(drawable, null, null, null);
                mRight.setText("");

                mTitle.setText(key);
                mTitle.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.GONE);
                searchEdit.setText("");
                search(key);
            }
        } else {
            mRight.setCompoundDrawables(null, null, null, null);
            mRight.setText(getString(R.string.search));
            mTitle.setVisibility(View.GONE);
            searchLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void initView() {
        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) titleBar.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
        titleBar.setLayoutParams(lp);
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
}

