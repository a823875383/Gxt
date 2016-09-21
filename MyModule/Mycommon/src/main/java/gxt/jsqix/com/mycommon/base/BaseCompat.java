package gxt.jsqix.com.mycommon.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jsqix.utils.BaseActivity;
import com.jsqix.utils.NetworkTools;

import org.xutils.x;

import gxt.jsqix.com.mycommon.R;

/**
 * Created by dq on 2016/9/9.
 */
public abstract class BaseCompat extends BaseActivity implements View.OnClickListener {
    /**
     * 是否显示断网提示
     */
    protected boolean isShowNetOff = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        startView();
        if (isTransparent()) {
            //透明状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        if (getStatusColor() != -1 || getStatusColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getStatusColor());
            }
        }

    }

    private void startView() {
        if (isShowNetOff) {
            if (!NetworkTools.isNetworkAvailable(this)) {
                onNetErrorShowPage();
            } else {
                x.view().inject(this);
                initView();
            }
        } else {
            x.view().inject(this);
            initView();
        }
        initTitle();
    }

    protected void onNetErrorShowPage() {
        this.setContentView(R.layout.layout_no_network);
        final LinearLayout btnRetry = (LinearLayout) this.findViewById(R.id.layout_refresh);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View arg0) {
                startView();
            }
        });
    }

    abstract protected void initTitle();

    abstract protected void initView();

    abstract protected void initVariable();

    abstract protected boolean isTransparent();

    abstract protected int getStatusColor();

    @Override
    public void onClick(View var1) {

    }
}
