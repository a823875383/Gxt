package gxt.jsqix.com.mycommon.base.api;

import android.content.Context;
import android.os.AsyncTask;

import com.jsqix.utils.LogWriter;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Iterator;
import java.util.Map;

public abstract class HttpGet extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;
    InterfaceHttpGet mListener;
    Context context;
    Map<String, Object> postMap;

    // Constructor
    public HttpGet(Context context, Map<String, Object> params,
                   InterfaceHttpGet listener) {
        this.mListener = listener;
        this.postMap = params;
        this.context = context;
    }

    /**
     * @param context
     * @param unSignParams 参数
     * @param params       不参与签名的参数
     * @param listener
     */
    // Constructor
    public HttpGet(Context context, Map<String, Object> params, Map<String, Object> unSignParams,
                   InterfaceHttpGet listener) {
        this.postMap = unSignParams;
        this.mListener = listener;
        this.context = context;
        String hmac = ApiClient.getSignAfter(unSignParams, ApiClient.ANDRID_SDK_KEY);
        postMap.put("hmac", hmac);

        Iterator<Map.Entry<String, Object>> strings = params.entrySet().iterator();
        while (strings.hasNext()) {
            Map.Entry<String, Object> entry = strings.next();
            postMap.put(entry.getKey(), entry.getValue());
        }

    }

    public abstract void onPreExecute();

    public void onPostExecute(String response) {
        if (mListener != null)
            mListener.getCallback(resultCode, response);
    }

    public String doInBackground(String... urls) {
        return getJSON(urls);
    }

    public String getJSON(String... urls) {
        try {
            for (String url : urls) {
                String getUrl = ApiClient.makeGetMessage(url, postMap);
                XutilsRequst(getUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogWriter.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void XutilsRequst(String url) throws Throwable {
        LogWriter.i(url);
        RequestParams params = new RequestParams(url);
        response = x.http().getSync(params, String.class);
    }

    public void setResultCode(int code) {
        this.resultCode = code;
    }

    public interface InterfaceHttpGet {
        void getCallback(int resultCode, String result);
    }
}
