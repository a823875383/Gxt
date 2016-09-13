package gxt.jsqix.com.mycommon.base;

import com.jsqix.utils.FrameApplication;

import org.xutils.x;

/**
 * Created by dq on 2016/9/9.
 */
public class BaseApplication extends FrameApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
