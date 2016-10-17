package gxt.jsqix.com.mycommon.base.util;

import android.widget.TextView;

import com.jsqix.utils.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by dq on 2016/5/26.
 */
public class CommUtils extends StringUtils {
    protected static DecimalFormat df = new DecimalFormat("######0.00");

    public static String textToString(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static String toFormat(Object obj) {
        String format = df.format(toDouble(obj));
        if (format.length() == 3) {
            format = "0" + format;
        }
        return format;
    }

    public static <T> T Obj2Bean(Object object, Class<T> classOfT) {
        return (T) object;
    }
}
