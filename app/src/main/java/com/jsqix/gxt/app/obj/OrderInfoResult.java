package com.jsqix.gxt.app.obj;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/12.
 */

public class OrderInfoResult extends BaseBean {

    /**
     * id : 127695
     * order_status : 100404
     * order_time : 2016-10-09 09:00:42
     * pay_time : 2016-10-09 09:00:47
     * order_totals : 875
     * address : 我问问
     * contacttel : 18865847415
     * contactname : 请求
     * delivery_time : 2016-10-09 09:02:30
     * detail_id : 0
     * goods_id : 0
     * product_id : 0
     * order_id : 0
     * cart_id : 0
     * order_list : [{"id":127695,"detail_id":127696,"goods_id":0,"product_id":0,"num":1,"wholesale_price":35,"tag_detail":"净含量:500g  ","product_name":"Danisa皇冠 丹麦 曲奇饼干 500g","goods_image":"zk/01/01/2016/06/30/8e2f796f3fc14948b5954ea69ae9ce46.jpg","order_id":0,"cart_id":0,"min_num":1,"stock":72,"return_status":100503},{"id":127695,"detail_id":127697,"goods_id":0,"product_id":0,"num":12,"wholesale_price":70,"tag_detail":"净含量:1000g  ","product_name":"Danisa皇冠 丹麦 曲奇饼干  1000g","goods_image":"zk/01/01/2016/06/30/8e2f796f3fc14948b5954ea69ae9ce46.jpg","order_id":0,"cart_id":0,"min_num":12,"stock":4,"return_status":100502}]
     * p_name : 北京
     * c_name : 北京
     * x_name : 西城区
     */

    private OrderObj obj;

    public OrderObj getObj() {
        return obj;
    }

    public void setObj(OrderObj obj) {
        this.obj = obj;
    }


}
