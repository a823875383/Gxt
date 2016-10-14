package com.jsqix.gxt.app.obj;

import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/13.
 */

public class MerchandiseManageResult extends BaseBean{

    /**
     * page_number : 1
     * page_size : 10
     * page_count : 1
     * total_count : 5
     * item_list : [{"goods_id":110357,"goods_name":"Danisa皇冠 丹麦 曲奇饼干","goods_image":"zk/01/01/2016/06/30/8e2f796f3fc14948b5954ea69ae9ce46.jpg","goods_status":0},{"goods_id":108746,"goods_name":"DOVE德芙 丝滑牛奶巧克力","goods_image":"zk/01/01/2016/06/24/11df77c220ff4728a0550b28b872ff0d.jpg","goods_status":0},{"goods_id":117365,"goods_name":"1212","goods_image":"zk/01/01/2016/08/23/0628799880ee4a3fa8ea25aa214e3ab2.jpg","goods_status":0},{"goods_id":111500,"goods_name":"高效肥","goods_image":"zk/01/01/2016/07/07/9f18b8930f044a87be75ec5897cc4cc8.jpg","goods_status":0,"sub_user_id":108724},{"goods_id":116764,"goods_name":"洁丽雅1浴巾 2毛巾 纯棉强吸水","goods_image":"zk/01/01/2016/08/22/73ad1eb0db0a4515b323edde8310cf24.jpg","goods_status":0}]
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
         * goods_id : 110357
         * goods_name : Danisa皇冠 丹麦 曲奇饼干
         * goods_image : zk/01/01/2016/06/30/8e2f796f3fc14948b5954ea69ae9ce46.jpg
         * goods_status : 0
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
            private int goods_status;

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
        }
    }
}
