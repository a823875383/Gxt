package gxt.jsqix.com.mycommon.base.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.jsqix.utils.LogWriter;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class HttpPost extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;

    Context context;
    InterfaceHttpPost mListener;
    Map<String, Object> postMap;
//    private CustomeDialog customeDialog;

    /**
     * @param context
     * @param listener
     * @param params   参数
     */
    public HttpPost(Context context, Map<String, Object> params, InterfaceHttpPost listener) {
        this.postMap = params;
        String hmac = ApiClient.getSignAfter(params, ApiClient.ANDRID_SDK_KEY);
        postMap.put("hmac", hmac);
        this.mListener = listener;
        this.context = context;
    }

    /**
     * @param context
     * @param listener
     * @param unSignParams 参数
     * @param params       不参与签名的参数
     */
    public HttpPost(Context context, Map<String, Object> params, Map<String, Object> unSignParams, InterfaceHttpPost listener) {
        this.postMap = unSignParams;
        String hmac = ApiClient.getSignAfter(unSignParams, ApiClient.ANDRID_SDK_KEY);
        postMap.put("hmac", hmac);

        Iterator<Entry<String, Object>> strings = params.entrySet().iterator();
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            postMap.put(entry.getKey(), entry.getValue());
        }
        this.mListener = listener;
        this.context = context;
    }

    public abstract void onPreExecute(); /*{
        customeDialog = new CustomeDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
        customeDialog.setView(view);
        customeDialog.show();
    }*/

    public String doInBackground(String... urls) {
        return httpPost(urls);
    }

    public void onPostExecute(String response) {

//        if (customeDialog != null && customeDialog.isShowing()) {
//            customeDialog.dismiss();
//        }
        LogWriter.v(response);
        if (mListener != null) {
            mListener.postCallback(resultCode, response);
        }
    }

    @SuppressLint("NewApi")
    public String httpPost(String... urls) {
        try {
            for (String url : urls) {
                XutilsPost(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogWriter.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void XutilsPost(String url) throws Throwable {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60 * 1000);
        Iterator<Entry<String, Object>> strings = postMap.entrySet().iterator();
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List<?>) {
                List imgs = (List) value;
                for (Object img : imgs) {
                    File file = new File(img + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                }
            } else {
                if (key.contains("img")) {
                    File file = new File(value + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                } else {
                    params.addBodyParameter(key, URLEncoder.encode(value + ""));
                }
            }
        }
        response = x.http().postSync(params, String.class);
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public interface InterfaceHttpPost {
        void postCallback(int resultCode, String result);
    }
}
