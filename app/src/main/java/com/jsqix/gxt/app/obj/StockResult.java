package com.jsqix.gxt.app.obj;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 16/9/30.
 */

public class StockResult extends BaseBean {

    /**
     * PRODUCT_ID : 124001
     * PRODUCT_NAME : 上好佳食品30g(S)
     * ID : 124001
     * MIN_NUM : 1
     * STOCK : 85
     * GOODS_ID : 47589
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int PRODUCT_ID;
        private String PRODUCT_NAME;
        private int ID;
        private int MIN_NUM;
        private int STOCK;
        private int GOODS_ID;

        public int getPRODUCT_ID() {
            return PRODUCT_ID;
        }

        public void setPRODUCT_ID(int PRODUCT_ID) {
            this.PRODUCT_ID = PRODUCT_ID;
        }

        public String getPRODUCT_NAME() {
            return PRODUCT_NAME;
        }

        public void setPRODUCT_NAME(String PRODUCT_NAME) {
            this.PRODUCT_NAME = PRODUCT_NAME;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getMIN_NUM() {
            return MIN_NUM;
        }

        public void setMIN_NUM(int MIN_NUM) {
            this.MIN_NUM = MIN_NUM;
        }

        public int getSTOCK() {
            return STOCK;
        }

        public void setSTOCK(int STOCK) {
            this.STOCK = STOCK;
        }

        public int getGOODS_ID() {
            return GOODS_ID;
        }

        public void setGOODS_ID(int GOODS_ID) {
            this.GOODS_ID = GOODS_ID;
        }
    }
}
