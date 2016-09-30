package gxt.jsqix.com.mycommon.base.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import gxt.jsqix.com.mycommon.R;

/**
 * Created by dongqing on 16/9/21.
 */
public class CustomDialog extends Dialog {
    private Context mContext;
    private WindowManager.LayoutParams lp;
    private View contentView;

    public CustomDialog(Context context) {
        super(context, R.style.BaseDialog);//默认主题
        this.mContext = context;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);//自定义主题
        this.mContext = context;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


    public void setView(View view) {
        setContentView(view);
        // 设置window属性
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.alpha = 1.0f; // 设置本身透明度
        lp.dimAmount = 0.65f; // 设置黑暗度
        getWindow().setAttributes(lp);
        setParas(0.8f, 0f);//默认宽度为0.8
        this.contentView = view;
    }

    public void setView(View view, int gravity) {
        setContentView(view);
        // 设置window属性
        lp = getWindow().getAttributes();
        lp.gravity = gravity;
        lp.alpha = 1.0f; // 设置本身透明度
        lp.dimAmount = 0.65f; // 设置黑暗度
        getWindow().setAttributes(lp);
        setParas(0.8f, 0f);//默认宽度为0.8
        this.contentView = view;
    }

    public void setParas(float width, float height) {
        WindowManager m = ((Activity) mContext).getWindowManager();
        // 设置window属性
        lp = getWindow().getAttributes();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        if (width > 0 && width <= 1) {
            lp.width = (int) (width * d.getWidth());
        } else {
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if (height > 0 && height <= 1)
            lp.height = (int) (height * d.getHeight());
        else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        getWindow().setAttributes(lp);
    }

    /**
     * 如果dialog高度大于大于给的值则设置否则为自适应
     * 宽度为最大
     *
     * @param height
     */
    public void setParas(float height) {
        WindowManager m = ((Activity) mContext).getWindowManager();
        // 设置window属性
        lp = getWindow().getAttributes();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        lp.width = d.getWidth();
        if (height > 0 && height <= 1)
            if (contentView.getMeasuredHeight() > (int) (height * d.getHeight())) {
                lp.height = (int) (height * d.getHeight());
            } else {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
        else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        getWindow().setAttributes(lp);
    }
}
