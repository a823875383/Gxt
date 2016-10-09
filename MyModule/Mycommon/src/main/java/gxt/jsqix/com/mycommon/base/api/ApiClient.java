package gxt.jsqix.com.mycommon.base.api;

import com.jsqix.utils.LogWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ApiClient {
    // 测试服务器
//    public static final String RequestIP = UAD.getRequestIp();

    public final static int P_ID = 102204;
    public final static int ORDER_TYPE = 1002;
    public final static int CHANNEL = 1003;
    public final static String UTF_8 = "UTF-8";
    public final static String ANDRID_SDK_KEY = "3c7210a86f1107b95459d2b4cb1ccf1d";
    public final static String SECRET_KEY = "3c7210a86f1107b95459d2b4cb1ccf1d";

    public static String makeGetMessage(String srcUrl, Map<String, Object> data)
            throws IOException {
        String url = null;
        if (data != null) {
            LogWriter.v("XXXXX", "--------------------------");
            LogWriter.i("签名前----", data.toString());
//            String data2 = getSignAfter(data, ApiClient.ANDRID_SDK_KEY);
//            LogWriter.i("签名后----", data2);
//            data.put("hmac", data2);
            String data2 = makePostData2(data);

            url = srcUrl + "?" + data2;
            LogWriter.i("发送的请求----", url);
        } else {
            url = srcUrl;
        }
        return url;
    }

    private static String makePostData2(Map<String, Object> data)
            throws UnsupportedEncodingException {

        StringBuilder postData = new StringBuilder();

        for (String name : data.keySet()) {
            postData.append(name);
            postData.append('=');
            if (data.get(name) == null) {
                postData.append("");
            } else {
                postData.append(URLEncoder.encode(data.get(name) + "", "utf-8"));
            }
            postData.append('&');
        }

        return postData.deleteCharAt(postData.lastIndexOf("&")).toString();
    }

    /**
     * map进行排序
     *
     * @param argMap
     * @return
     */
    public static String getSignAfter(Map<String, Object> argMap, String sin) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (Entry<String, Object> tMap : argMap.entrySet()) {
            treeMap.put(tMap.getKey(), tMap.getValue() + "");
        }
        StringBuffer ret = new StringBuffer();
        for (Entry<String, String> map : treeMap.entrySet()) {
            String key = map.getKey();
            String value = map.getValue();
            LogWriter.d(key + "=");
            if (value == null)
                value = "";
            LogWriter.d(value);
            if (key.contains("img"))
                continue;
            ret.append(key);
            ret.append("=");
            ret.append(value);
            ret.append("&");
        }
        if (ret.toString().endsWith("&")) {
            ret.delete(ret.toString().length() - 1, ret.toString().length());
        }
        String sign = getSign(ret.toString(), sin);
        return sign;
    }

    /**
     * 签名
     *
     * @param signAfter
     * @return
     */
    public static String getSign(String signAfter, String key) {
        LogWriter.v("加密前：", signAfter);
//        String sign = Md5.getMD5(signAfter + key,"utf-8");
        String sign = Md5.getMd5Hex(signAfter + key, "utf-8");
        LogWriter.v("加密后：", sign);
        return sign;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
