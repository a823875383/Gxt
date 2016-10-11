package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/9.
 */

public class OrderSubmitResult extends BaseBean{

    /**
     * id : 127716
     * buyer_id : 117893
     * order_status : 100401
     * order_ip : 192.168.1.136
     * order_totals : 220
     * freight : 0
     * detail_id : 0
     * goods_id : 0
     * product_id : 0
     * order_id : 0
     * cart_id : 0
     * order_list : [{"detail_id":0,"goods_id":108746,"product_id":108750,"num":2,"sales_price":120,"tag_detail":"净含量:1000g  ","goods_name":"DOVE德芙 丝滑牛奶巧克力","product_name":"DOVE德芙 丝滑牛奶巧克力","goods_image":"zk/01/01/2016/06/24/11df77c220ff4728a0550b28b872ff0d.jpg","order_id":127716,"add_time":"2016-10-09 10:16:38","cart_id":0}]
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
        private int order_status;
        private String order_ip;
        private double order_totals;
        private int freight;
        private int detail_id;
        private int goods_id;
        private int product_id;
        private int order_id;
        private int cart_id;
        /**
         * detail_id : 0
         * goods_id : 108746
         * product_id : 108750
         * num : 2
         * sales_price : 120
         * tag_detail : 净含量:1000g
         * goods_name : DOVE德芙 丝滑牛奶巧克力
         * product_name : DOVE德芙 丝滑牛奶巧克力
         * goods_image : zk/01/01/2016/06/24/11df77c220ff4728a0550b28b872ff0d.jpg
         * order_id : 127716
         * add_time : 2016-10-09 10:16:38
         * cart_id : 0
         */

        private List<OrderListBean> order_list;

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

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getOrder_ip() {
            return order_ip;
        }

        public void setOrder_ip(String order_ip) {
            this.order_ip = order_ip;
        }

        public double getOrder_totals() {
            return order_totals;
        }

        public void setOrder_totals(double order_totals) {
            this.order_totals = order_totals;
        }

        public int getFreight() {
            return freight;
        }

        public void setFreight(int freight) {
            this.freight = freight;
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

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public List<OrderListBean> getOrder_list() {
            return order_list;
        }

        public void setOrder_list(List<OrderListBean> order_list) {
            this.order_list = order_list;
        }

        public static class OrderListBean {
            private int detail_id;
            private int goods_id;
            private int product_id;
            private int num;
            private double sales_price;
            private String tag_detail;
            private String goods_name;
            private String product_name;
            private String goods_image;
            private int order_id;
            private String add_time;
            private int cart_id;

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

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public double getSales_price() {
                return sales_price;
            }

            public void setSales_price(double sales_price) {
                this.sales_price = sales_price;
            }

            public String getTag_detail() {
                return tag_detail;
            }

            public void setTag_detail(String tag_detail) {
                this.tag_detail = tag_detail;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
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
}
