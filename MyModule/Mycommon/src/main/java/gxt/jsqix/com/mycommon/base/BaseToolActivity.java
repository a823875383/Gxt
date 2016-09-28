package gxt.jsqix.com.mycommon.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gxt.jsqix.com.mycommon.R;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 带title的activity
 */
public abstract class BaseToolActivity extends BaseCompat {

    protected LinearLayout titleBase;
    private RelativeLayout titleBar;
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
        titleBase = (LinearLayout) findViewById(R.id.linearlayout_base);
        getLayoutInflater().inflate(layoutResID, titleBase);
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

    @Override
    protected boolean isShowNetOff() {
        return true;
    }

    @Override
    protected boolean isStatusWhite() {
        Drawable background = titleBase.getBackground();
        ColorDrawable colorDrawable = (ColorDrawable) background;
        int color = colorDrawable.getColor();
        if (color == Color.WHITE) {
            //标题栏为白色，状态栏改为黑色字体
            return true;
        } else {
            return false;
        }
    }
}
