package gxt.jsqix.com.mycommon.base.util;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by dongqing on 2016/10/18.
 */

public class LoadingUtils {
    private Context mContext;
    private KProgressHUD hub;

    public LoadingUtils(Context mContext) {
        this.mContext = mContext;
        initProgressHUD();
    }

    private void initProgressHUD() {
        hub = KProgressHUD.create(mContext);
        hub.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hub.setCancellable(false);
        hub.setAnimationSpeed(2);
        hub.setDimAmount(0.5f);
    }

    public void show() {
        if (hub != null && !hub.isShowing()) {
            hub.show();
        }
    }

    public void dismiss() {
        if (hub != null) {
            hub.dismiss();
        }
    }
}
