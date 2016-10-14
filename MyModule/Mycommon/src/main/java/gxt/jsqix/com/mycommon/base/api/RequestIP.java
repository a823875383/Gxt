package gxt.jsqix.com.mycommon.base.api;

/**
 * Created by dongqing on 16/9/26.
 */

public class RequestIP {
    private static final String IP = "http://192.168.1.223:8086/";

    public final static String LOGIN = IP + "user/login";

    public final static String GET_GOODS_LIST = IP + "buyGoods/getGoodsList";
    public final static String GET_GOODS_ONE_CLASSIFY = IP + "buyGoods/getGoodsOneClassify";
    public final static String GET_GOODS_TWO_CLASSIFY = IP + "buyGoods/getGoodsTwoClassify";
    public final static String GET_PRODUCT_DETAIL = IP + "buyGoods/getProductDetail";
    public final static String SURE_ORDER = IP + "buyGoods/sureOrder";
    public final static String ADD_ORDER = IP + "buyGoods/addOrder";
    public final static String GET_USER_BALANCE = IP + "buyGoods/getUserBalance";
    public final static String BALANCE_PAY = IP + "buyGoods/balancePay";
    public final static String ADD_CART = IP + "buyGoods/addCart";

    public final static String LIST_GOODS_STANDARD = IP + "goods/listGoodsStandard";
    public final static String QUERY_PRODUCT_BY_STANDARD = IP + "goods/queryProductByStandard";
    public final static String GOODS_CART = IP + "goods/goodsCart";
    public final static String DELETE_CART = IP + "goods/delete";
    public final static String PAGE_GOODS = IP + "goods/pageGoods";
    public final static String UPDATE_GOODS_STATUS = IP + "goods/updateGoodsStatus";
    public final static String UPDATE_PRODUCT_MINNUM_AND_STOCK = IP + "goods/updateProductMinNumAndStock";

    public final static String PRO_LIST = IP + "city/proList";
    public final static String ADDRESS_LIST = IP + "city/addressList";
    public final static String QUERY_ADDRESS_BY_ID = IP + "city/queryAddressById";
    public final static String ADD_ADDRESS = IP + "city/addAddress";
    public final static String UPDATE_ADDRESS = IP + "city/updateAddress";
    public final static String UPDATE_DEFAULT = IP + "city/updateDefult";
    public final static String DELETE_ADDRESS = IP + "city/deleteAddress";

    public final static String ORDER_BUYER_LIST = IP + "order/orderBuyerList";
    public final static String ORDER_SELLER_LIST = IP + "order/orderSellerList";
    public final static String QUERY_EXPRESS = IP + "order/queryExpress";
    public final static String QUERY_ORDER = IP + "order/queryOrder";
    public final static String UPT_BUYER_ORDER = IP + "order/uptBuyerOrder";
    public final static String UPT_SELLER_ORDER = IP + "order/uptOrder";
    public final static String MY_ORDER_DETAIL = IP + "order/myOrderDetail";

}
