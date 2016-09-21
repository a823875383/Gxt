package gxt.jsqix.com.mycommon.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gxt.jsqix.com.mycommon.R;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 带title的activity
 */
public abstract class BaseToolActivity extends BaseCompat {

    protected RelativeLayout titleBar;
    protected TextView mBack; // 返回
    protected TextView mTitle; // 标题
    protected TextView mRight;//右菜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        ViewGroup vg = (ViewGroup) findViewById(R.id.linearlayout_base);
        getLayoutInflater().inflate(layoutResID, vg);
        init();
    }

    protected void init() {
        mBack = (TextView) findViewById(R.id.tv_left);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mRight = (TextView) findViewById(R.id.tv_right);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        if (isTransparent()) {
            //title margin透明通知栏高度
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) titleBar.getLayoutParams();
            lp.setMargins(0, StatusBarCompat.getStatusBarHeight(this), 0, 0);
            titleBar.setLayoutParams(lp);
        }
        mBack.setOnClickListener(this);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_left) {
            finish();
        }
    }

    @Override
    protected int getStatusColor() {
        return -1;
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }
}
