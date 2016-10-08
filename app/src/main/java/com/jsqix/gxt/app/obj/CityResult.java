package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 16/10/8.
 */

public class CityResult extends BaseBean {
    private List<CityObj> obj;

    public List<CityObj> getObj() {
        return obj;
    }

    public void setObj(List<CityObj> obj) {
        this.obj = obj;
    }
}
