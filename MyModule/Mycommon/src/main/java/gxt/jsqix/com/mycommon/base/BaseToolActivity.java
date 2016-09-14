package gxt.jsqix.com.mycommon.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gxt.jsqix.com.mycommon.R;

/**
 * 带title的activity
 * 根布局必须是LinearLayout
 */
public class BaseToolActivity extends BaseCompat {

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
        initView();
    }

    @Override
    protected void initView() {
        mBack = (TextView) findViewById(R.id.tv_left);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mRight = (TextView) findViewById(R.id.tv_right);
        mBack.setOnClickListener(this);
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
