package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/10.
 */

public class ExpressResult extends BaseBean {

    /**
     * LABELNAME : 顺丰速运
     * LABELNO : 101701
     */

    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private String LABELNAME;
        private int LABELNO;

        public String getLABELNAME() {
            return LABELNAME;
        }

        public void setLABELNAME(String LABELNAME) {
            this.LABELNAME = LABELNAME;
        }

        public int getLABELNO() {
            return LABELNO;
        }

        public void setLABELNO(int LABELNO) {
            this.LABELNO = LABELNO;
        }
    }
}
