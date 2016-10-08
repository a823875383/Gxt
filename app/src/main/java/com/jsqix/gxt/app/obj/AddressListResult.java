package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 16/10/8.
 */

public class AddressListResult extends BaseBean {

    /**
     * id : 124792
     * user_id : 117893
     * province_id : 2
     * city_id : 52
     * county_id : 501
     * street_address : xx
     * whether_default : 1
     * phone : 13770301896
     * consignee : x
     * addtime : 2016-09-26 15:34:18
     * pro_name : 北京
     * city_name : 北京
     * contry_name : 西城区
     */

    private List<AddressObj> obj;

    public List<AddressObj> getObj() {
        return obj;
    }

    public void setObj(List<AddressObj> obj) {
        this.obj = obj;
    }


}
