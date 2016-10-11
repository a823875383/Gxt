package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/10.
 */

public class OrderMoneyResult extends BaseBean{

    /**
     * id : 1118940
     * buyer_id : 108724
     * seller_id : 19753
     * order_status : 100401
     * order_finish_time : 2016-09-02 13:49:38
     * order_totals : 2210
     * paid_amt : 1
     * detail_id : 0
     * goods_id : 0
     * product_id : 0
     * order_id : 0
     * add_time : 2016-07-12 08:47:45
     * cart_id : 0
     */

    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int id;
        private int buyer_id;
        private int seller_id;
        private int order_status;
        private String order_finish_time;
        private double order_totals;
        private double paid_amt;
        private int detail_id;
        private int goods_id;
        private int product_id;
        private int order_id;
        private String add_time;
        private int cart_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getOrder_finish_time() {
            return order_finish_time;
        }

        public void setOrder_finish_time(String order_finish_time) {
            this.order_finish_time = order_finish_time;
        }

        public double getOrder_totals() {
            return order_totals;
        }

        public void setOrder_totals(double order_totals) {
            this.order_totals = order_totals;
        }

        public double getPaid_amt() {
            return paid_amt;
        }

        public void setPaid_amt(double paid_amt) {
            this.paid_amt = paid_amt;
        }

        public int getDetail_id() {
            return detail_id;
        }

        public void setDetail_id(int detail_id) {
            this.detail_id = detail_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }
    }
}
