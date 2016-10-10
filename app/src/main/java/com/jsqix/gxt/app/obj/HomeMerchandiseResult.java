package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 16/9/28.
 */

public class HomeMerchandiseResult extends BaseBean {

    /**
     * page_number : 1
     * page_size : 10
     * page_count : 9
     * total_count : 89
     * item_list : [{"goods_id":20193,"goods_name":"酱油","goods_image":"zkmg/zkmgupload/01/01/2016/02/25/5ae0f75e22d045cbb6389ea964eb8b25.png","product_id":46964,"sales_price":25,"wholesale_price":20,"stock":0,"product_name":"海天味极鲜酱油1.9L","min_num":1,"sale_counts":79},{"goods_id":47589,"goods_name":"上好佳食品","goods_image":"zkmg/zkmgupload/01/01/2016/06/14/f4386e7d04194102bbecc7335c5caecf.jpg","product_id":47595,"sales_price":0.02,"wholesale_price":0.01,"stock":12,"product_name":"上好佳食品30g(XL)","min_num":1,"sale_counts":41},{"goods_id":110357,"goods_name":"Danisa皇冠 丹麦 曲奇饼干","goods_image":"zk/01/01/2016/06/30/8e2f796f3fc14948b5954ea69ae9ce46.jpg","product_id":110364,"sales_price":42,"wholesale_price":35,"stock":160,"product_name":"Danisa皇冠 丹麦 曲奇饼干 500g","min_num":1,"sale_counts":39},{"goods_id":108746,"goods_name":"DOVE德芙 丝滑牛奶巧克力","goods_image":"zk/01/01/2016/06/24/11df77c220ff4728a0550b28b872ff0d.jpg","product_id":108750,"sales_price":120,"wholesale_price":110,"stock":473,"product_name":"DOVE德芙 丝滑牛奶巧克力","min_num":2,"sale_counts":22},{"goods_id":21903,"goods_name":"多力葵花籽油 1.8L","goods_image":"zkmg/zkmgupload/01/01/2016/03/03/ff65ebe755b747ebb69b263cde438ac8.jpg","product_id":21908,"sales_price":29.9,"wholesale_price":2,"stock":4,"min_num":10,"sale_counts":18},{"goods_id":22034,"goods_name":"亲亲八宝粥","goods_image":"zkmg/zkmgupload/01/01/2016/03/03/d7c27a96edfe46c78da4832acc0c5257.jpg","product_id":22037,"sales_price":32,"wholesale_price":3,"stock":88,"product_name":"亲亲八宝粥","min_num":5,"sale_counts":18},{"goods_id":110357,"goods_name":"Danisa皇冠 丹麦 曲奇饼干","goods_image":"zk/01/01/2016/06/30/8e2f796f3fc14948b5954ea69ae9ce46.jpg","product_id":110362,"sales_price":84,"wholesale_price":70,"stock":88,"product_name":"Danisa皇冠 丹麦 曲奇饼干  1000g","min_num":1,"sale_counts":11},{"goods_id":105699,"goods_name":"三只松鼠大礼包","goods_image":"zk/01/01/2016/06/17/3185b228509849c8905df500d961158a.jpg","product_id":105702,"sales_price":20,"wholesale_price":15,"stock":86,"product_name":"三只松鼠大礼包","min_num":5,"sale_counts":10},{"goods_id":21858,"goods_name":"洽洽香瓜子","goods_image":"zkmg/zkmgupload/01/01/2016/06/13/3d78e7f3f3f74950afcc91dc3b0386e2.jpg","product_id":47101,"sales_price":2.5,"stock":80,"product_name":"洽洽香瓜子","min_num":10,"sale_counts":9},{"goods_id":105682,"goods_name":"小西瓜","goods_image":"zk/01/01/2016/06/17/1fdb9a2cf3334b8e8a697e42143b5825.jpg","product_id":105685,"sales_price":3,"wholesale_price":2,"stock":95,"product_name":"小西瓜","min_num":1000,"sale_counts":9}]
     * has_prev_page : false
     * has_next_page : true
     * is_first_page : true
     * is_last_page : false
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
         * goods_id : 20193
         * goods_name : 酱油
         * goods_image : zkmg/zkmgupload/01/01/2016/02/25/5ae0f75e22d045cbb6389ea964eb8b25.png
         * product_id : 46964
         * sales_price : 25
         * wholesale_price : 20
         * stock : 0
         * product_name : 海天味极鲜酱油1.9L
         * min_num : 1
         * sale_counts : 79
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
            private String goods_name;
            private String goods_image;
            private int product_id;
            private double sales_price;
            private double wholesale_price;
            private int stock;
            private String product_name;
            private int min_num;
            private int sale_counts;

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

            public int getSale_counts() {
                return sale_counts;
            }

            public void setSale_counts(int sale_counts) {
                this.sale_counts = sale_counts;
            }
        }
    }
}
