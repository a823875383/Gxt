package gxt.jsqix.com.mycommon.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jsqix.utils.BaseActivity;
import com.jsqix.utils.LogWriter;
import com.jsqix.utils.NetworkTools;
import com.jsqix.utils.Utils;

import org.xutils.x;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import gxt.jsqix.com.mycommon.R;
import gxt.jsqix.com.mycommon.base.util.LoadingUtils;

/**
 * Created by dq on 2016/9/9.
 */
public abstract class BaseCompat extends BaseActivity implements View.OnClickListener {
    public LoadingUtils loadingUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingUtils = new LoadingUtils(this);

        initVariable();
        startView();
        if (isTransparent()) {
            //透明状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            StatusBarMode(this, isStatusWhite());
        }
        if (getStatusColor() != -1 || getStatusColor() != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getStatusColor());
            }
        }
    }

    private void startView() {
        if (isShowNetOff()) {
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
        Utils.makeToast(this, getString(R.string.no_network));
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

    abstract protected boolean isShowNetOff();

    abstract protected boolean isStatusWhite();

    @Override
    public void onClick(View var1) {

    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     true 为黑色 false为白色
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarMode(Activity activity, boolean type) {
        LogWriter.e("设置状态栏字体" + type);
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity.getWindow(), type)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), type)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = activity.getWindow().getDecorView();
                int ui = decor.getSystemUiVisibility();
                if (type) {
                    ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
//                if (type) {
//                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                } else {
//                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                }
                decor.setSystemUiVisibility(ui);
                result = 3;
            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

}
