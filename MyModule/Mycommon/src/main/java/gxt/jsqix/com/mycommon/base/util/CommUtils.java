package gxt.jsqix.com.mycommon.base.util;

import android.widget.TextView;

import com.jsqix.utils.StringUtils;

/**
 * Created by dq on 2016/5/26.
 */
public class CommUtils extends StringUtils {


    public static String textToString(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static <T> T Obj2Bean(Object object, Class<T> classOfT) {
        return (T) object;
    }
}
