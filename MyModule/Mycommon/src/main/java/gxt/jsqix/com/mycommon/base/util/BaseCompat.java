package gxt.jsqix.com.mycommon.base.util;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.jsqix.utils.BaseActivity;

import org.xutils.x;

/**
 * Created by dq on 2016/9/9.
 */
public abstract class BaseCompat extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        if (isTransparent()) {
            //透明状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        if (getStatusColor() != -1||getStatusColor()!=0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getStatusColor());
            }
        }

    }

    abstract protected void initView();

    abstract protected boolean isTransparent();

    abstract protected int getStatusColor();

    @Override
    public void onClick(View var1) {

    }
}
