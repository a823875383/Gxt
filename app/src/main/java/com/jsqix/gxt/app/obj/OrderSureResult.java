package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 16/10/8.
 */

public class OrderSureResult extends BaseBean {

    /**
     * productlist : [{"tag_no1":108742,"details1":"净含量","tag_detail_no1":108743,"tag_detail_name1":"1000g","goods_id":108746,"goods_name":"DOVE德芙 丝滑牛奶巧克力","goods_image":"zk/01/01/2016/06/24/11df77c220ff4728a0550b28b872ff0d.jpg","goods_status":1,"freight":0,"freight_flag":1,"product_id":108750,"sales_price":120,"wholesale_price":110,"stock":380,"product_name":"DOVE德芙 丝滑牛奶巧克力","min_num":2,"cart_number":2}]
     * address : {"id":127583,"user_id":117893,"province_id":3,"city_id":3401,"county_id":3405,"street_address":"hdhiiqdhfhshi","whether_default":0,"phone":"15312345678","consignee":"hefei"}
     * buy_type : 0
     * counts : 2
     * total_amt : 220.0
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 127583
         * user_id : 117893
         * province_id : 3
         * city_id : 3401
         * county_id : 3405
         * street_address : hdhiiqdhfhshi
         * whether_default : 0
         * phone : 15312345678
         * consignee : hefei
         */

        private AddressObj address;
        private int buy_type;
        private int counts;
        private double total_amt;
        /**
         * tag_no1 : 108742
         * details1 : 净含量
         * tag_detail_no1 : 108743
         * tag_detail_name1 : 1000g
         * goods_id : 108746
         * goods_name : DOVE德芙 丝滑牛奶巧克力
         * goods_image : zk/01/01/2016/06/24/11df77c220ff4728a0550b28b872ff0d.jpg
         * goods_status : 1
         * freight : 0
         * freight_flag : 1
         * product_id : 108750
         * sales_price : 120
         * wholesale_price : 110
         * stock : 380
         * product_name : DOVE德芙 丝滑牛奶巧克力
         * min_num : 2
         * cart_number : 2
         */

        private List<ProductlistBean> productlist;

        public AddressObj getAddress() {
            return address;
        }

        public void setAddress(AddressObj address) {
            this.address = address;
        }

        public int getBuy_type() {
            return buy_type;
        }

        public void setBuy_type(int buy_type) {
            this.buy_type = buy_type;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public double getTotal_amt() {
            return total_amt;
        }

        public void setTotal_amt(double total_amt) {
            this.total_amt = total_amt;
        }

        public List<ProductlistBean> getProductlist() {
            return productlist;
        }

        public void setProductlist(List<ProductlistBean> productlist) {
            this.productlist = productlist;
        }


        public static class ProductlistBean {
            private int tag_no1;
            private String details1;
            private int tag_detail_no1;
            private String tag_detail_name1;
            private int goods_id;
            private String goods_name;
            private String goods_image;
            private int goods_status;
            private int freight;
            private int freight_flag;
            private int product_id;
            private double sales_price;
            private double wholesale_price;
            private int stock;
            private String product_name;
            private int min_num;
            private int cart_number;

            public int getTag_no1() {
                return tag_no1;
            }

            public void setTag_no1(int tag_no1) {
                this.tag_no1 = tag_no1;
            }

            public String getDetails1() {
                return details1;
            }

            public void setDetails1(String details1) {
                this.details1 = details1;
            }

            public int getTag_detail_no1() {
                return tag_detail_no1;
            }

            public void setTag_detail_no1(int tag_detail_no1) {
                this.tag_detail_no1 = tag_detail_no1;
            }

            public String getTag_detail_name1() {
                return tag_detail_name1;
            }

            public void setTag_detail_name1(String tag_detail_name1) {
                this.tag_detail_name1 = tag_detail_name1;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public int getGoods_status() {
                return goods_status;
            }

            public void setGoods_status(int goods_status) {
                this.goods_status = goods_status;
            }

            public int getFreight() {
                return freight;
            }

            public void setFreight(int freight) {
                this.freight = freight;
            }

            public int getFreight_flag() {
                return freight_flag;
            }

            public void setFreight_flag(int freight_flag) {
                this.freight_flag = freight_flag;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public double getSales_price() {
                return sales_price;
            }

            public void setSales_price(double sales_price) {
                this.sales_price = sales_price;
            }

            public double getWholesale_price() {
                return wholesale_price;
            }

            public void setWholesale_price(double wholesale_price) {
                this.wholesale_price = wholesale_price;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public int getMin_num() {
                return min_num;
            }

            public void setMin_num(int min_num) {
                this.min_num = min_num;
            }

            public int getCart_number() {
                return cart_number;
            }

            public void setCart_number(int cart_number) {
                this.cart_number = cart_number;
            }
        }
    }
}
