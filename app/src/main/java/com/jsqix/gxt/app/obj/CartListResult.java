package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/11.
 */

public class CartListResult extends BaseBean{
    /**
     * page_number : 1
     * page_size : 100
     * page_count : 1
     * total_count : 1
     * item_list : [{"goods_id":47589,"user_id":108724,"goods_image":"zkmg/zkmgupload/01/01/2016/06/14/f4386e7d04194102bbecc7335c5caecf.jpg","psite":102401,"add_time":"2016-10-11 10:30:23","product_id":47593,"wholesale_price":0.01,"stock":48,"product_name":"上好佳食品20g(S)","min_num":1,"cart_number":2}]
     * has_prev_page : false
     * has_next_page : false
     * is_first_page : true
     * is_last_page : true
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int page_number;
        private int page_size;
        private int page_count;
        private int total_count;
        private boolean has_prev_page;
        private boolean has_next_page;
        private boolean is_first_page;
        private boolean is_last_page;
        /**
         * goods_id : 47589
         * user_id : 108724
         * goods_image : zkmg/zkmgupload/01/01/2016/06/14/f4386e7d04194102bbecc7335c5caecf.jpg
         * psite : 102401
         * add_time : 2016-10-11 10:30:23
         * product_id : 47593
         * wholesale_price : 0.01
         * stock : 48
         * product_name : 上好佳食品20g(S)
         * min_num : 1
         * cart_number : 2
         */

        private List<ItemListBean> item_list;

        public int getPage_number() {
            return page_number;
        }

        public void setPage_number(int page_number) {
            this.page_number = page_number;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public boolean isHas_prev_page() {
            return has_prev_page;
        }

        public void setHas_prev_page(boolean has_prev_page) {
            this.has_prev_page = has_prev_page;
        }

        public boolean isHas_next_page() {
            return has_next_page;
        }

        public void setHas_next_page(boolean has_next_page) {
            this.has_next_page = has_next_page;
        }

        public boolean isIs_first_page() {
            return is_first_page;
        }

        public void setIs_first_page(boolean is_first_page) {
            this.is_first_page = is_first_page;
        }

        public boolean isIs_last_page() {
            return is_last_page;
        }

        public void setIs_last_page(boolean is_last_page) {
            this.is_last_page = is_last_page;
        }

        public List<ItemListBean> getItem_list() {
            return item_list;
        }

        public void setItem_list(List<ItemListBean> item_list) {
            this.item_list = item_list;
        }

        public static class ItemListBean {
            private int goods_id;
            private int user_id;
            private String goods_image;
            private int psite;
            private String add_time;
            private int product_id;
            private double wholesale_price;
            private int stock;
            private String product_name;
            private int min_num;
            private int cart_number;

            private boolean isSelect;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public int getPsite() {
                return psite;
            }

            public void setPsite(int psite) {
                this.psite = psite;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
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

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public boolean isSelect() {
                return isSelect;
            }
        }
    }
}
