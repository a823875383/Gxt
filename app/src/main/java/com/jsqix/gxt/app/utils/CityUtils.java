package com.jsqix.gxt.app.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author dq
 */
public class CityUtils {
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;
    private Context context;
    static CityUtils utils;

    public CityUtils() {
    }

    public CityUtils(Context context) {
        this.context = context;
        initJsonData();
    }

    public static CityUtils getInstance(Context context) {
        if (utils == null) {
            utils = new CityUtils(context);
        }
        return utils;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPro() {
        ArrayList<String> pros = new ArrayList<String>();
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字
                pros.add(province);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pros;
    }

    public ArrayList<ArrayList<String>> getCity() {
        ArrayList<ArrayList<String>> cities = new ArrayList<ArrayList<String>>();
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                ArrayList<String> pro = new ArrayList<String>();
                JSONArray jsonCs;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字
                    pro.add(city);
                }
                cities.add(pro);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cities;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getArea() {
        ArrayList<ArrayList<ArrayList<String>>> areas = new ArrayList<ArrayList<ArrayList<String>>>();
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                ArrayList<ArrayList<String>> pro = new ArrayList<ArrayList<String>>();
                JSONArray jsonCs;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);// 每个市的json对象
                    ArrayList<String> city = new ArrayList<String>();
                    JSONArray jsonAs;
                    try {
                        jsonAs = jsonCity.getJSONArray("a");
                    } catch (Exception e2) {
                        continue;
                    }
                    for (int k = 0; k < jsonAs.length(); k++) {
                        JSONObject jsonArea = jsonAs.getJSONObject(k);
                        String area = jsonArea.getString("s");//区名字
                        city.add(area);
                    }
                    pro.add(city);
                }
                areas.add(pro);
            }
        } catch (JSONException e) {

        }
        return areas;
    }

    public void destory() {
        utils = null;
        mJsonObj = null;
    }
}
