package gxt.jsqix.com.mycommon.base.api;

/**
 * Created by dongqing on 16/9/26.
 */

public class RequestIP {
    private static final String IP = "http://192.168.1.245:8080/";

    public final static String LOGIN = IP + "user/login";
    public final static String GET_GOODS_LIST = IP + "buyGoods/getGoodsList";
    public final static String GET_GOODS_ONE_CLASSIFY = IP + "buyGoods/getGoodsOneClassify";
    public final static String GET_GOODS_TWO_CLASSIFY = IP + "buyGoods/getGoodsTwoClassify";
    public final static String GET_PRODUCT_DETAIL = IP + "buyGoods/getProductDetail";

}
