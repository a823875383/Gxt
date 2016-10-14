package com.jsqix.gxt.app.obj;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dongqing on 2016/10/13.
 */

public class OrderObj implements Parcelable{
    private int buyer_id;
    private int seller_id;
    private String add_time;
    private String stype;
    private int id;
    private int order_status;
    private String order_time;
    private String pay_time;
    private String order_finish_time;
    private String delivery_time;
    private double order_totals;
    private String address;
    private String contacttel;
    private String contactname;
    private int detail_id;
    private int goods_id;
    private int product_id;
    private int order_id;
    private int cart_id;
    private String p_name;
    private String c_name;
    private String x_name;
    private List<OrderListBean> order_list;

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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getOrder_finish_time() {
        return order_finish_time;
    }

    public void setOrder_finish_time(String order_finish_time) {
        this.order_finish_time = order_finish_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public double getOrder_totals() {
        return order_totals;
    }

    public void setOrder_totals(double order_totals) {
        this.order_totals = order_totals;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
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

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getX_name() {
        return x_name;
    }

    public void setX_name(String x_name) {
        this.x_name = x_name;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class OrderListBean implements Parcelable {
        private int id;
        private int detail_id;
        private int goods_id;
        private int product_id;
        private int num;
        private double wholesale_price;
        private String tag_detail;
        private String product_name;
        private String goods_image;
        private int order_id;
        private int cart_id;
        private int min_num;
        private int stock;
        private int return_status;
        private String dec;
        private int day1;
        private int dec_status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getWholesale_price() {
            return wholesale_price;
        }

        public void setWholesale_price(double wholesale_price) {
            this.wholesale_price = wholesale_price;
        }

        public String getTag_detail() {
            return tag_detail;
        }

        public void setTag_detail(String tag_detail) {
            this.tag_detail = tag_detail;
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

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getMin_num() {
            return min_num;
        }

        public void setMin_num(int min_num) {
            this.min_num = min_num;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getReturn_status() {
            return return_status;
        }

        public void setReturn_status(int return_status) {
            this.return_status = return_status;
        }

        public String getDec() {
            return dec;
        }

        public void setDec(String dec) {
            this.dec = dec;
        }

        public void setDay1(int day1) {
            this.day1 = day1;
        }

        public int getDay1() {
            return day1;
        }

        public int getDec_status() {
            return dec_status;
        }

        public void setDec_status(int dec_status) {
            this.dec_status = dec_status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.detail_id);
            dest.writeInt(this.goods_id);
            dest.writeInt(this.product_id);
            dest.writeInt(this.num);
            dest.writeDouble(this.wholesale_price);
            dest.writeString(this.tag_detail);
            dest.writeString(this.product_name);
            dest.writeString(this.goods_image);
            dest.writeInt(this.order_id);
            dest.writeInt(this.cart_id);
            dest.writeInt(this.min_num);
            dest.writeInt(this.stock);
            dest.writeInt(this.return_status);
            dest.writeString(this.dec);
            dest.writeInt(this.day1);
            dest.writeInt(this.dec_status);
        }

        public OrderListBean() {
        }

        protected OrderListBean(Parcel in) {
            this.id = in.readInt();
            this.detail_id = in.readInt();
            this.goods_id = in.readInt();
            this.product_id = in.readInt();
            this.num = in.readInt();
            this.wholesale_price = in.readDouble();
            this.tag_detail = in.readString();
            this.product_name = in.readString();
            this.goods_image = in.readString();
            this.order_id = in.readInt();
            this.cart_id = in.readInt();
            this.min_num = in.readInt();
            this.stock = in.readInt();
            this.return_status = in.readInt();
            this.dec = in.readString();
            this.day1 = in.readInt();
            this.dec_status = in.readInt();
        }

        public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
            @Override
            public OrderListBean createFromParcel(Parcel source) {
                return new OrderListBean(source);
            }

            @Override
            public OrderListBean[] newArray(int size) {
                return new OrderListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.buyer_id);
        dest.writeInt(this.seller_id);
        dest.writeString(this.add_time);
        dest.writeString(this.stype);
        dest.writeInt(this.id);
        dest.writeInt(this.order_status);
        dest.writeString(this.order_time);
        dest.writeString(this.pay_time);
        dest.writeString(this.order_finish_time);
        dest.writeString(this.delivery_time);
        dest.writeDouble(this.order_totals);
        dest.writeString(this.address);
        dest.writeString(this.contacttel);
        dest.writeString(this.contactname);
        dest.writeInt(this.detail_id);
        dest.writeInt(this.goods_id);
        dest.writeInt(this.product_id);
        dest.writeInt(this.order_id);
        dest.writeInt(this.cart_id);
        dest.writeString(this.p_name);
        dest.writeString(this.c_name);
        dest.writeString(this.x_name);
        dest.writeTypedList(this.order_list);
    }

    public OrderObj() {
    }

    protected OrderObj(Parcel in) {
        this.buyer_id = in.readInt();
        this.seller_id = in.readInt();
        this.add_time = in.readString();
        this.stype = in.readString();
        this.id = in.readInt();
        this.order_status = in.readInt();
        this.order_time = in.readString();
        this.pay_time = in.readString();
        this.order_finish_time = in.readString();
        this.delivery_time = in.readString();
        this.order_totals = in.readDouble();
        this.address = in.readString();
        this.contacttel = in.readString();
        this.contactname = in.readString();
        this.detail_id = in.readInt();
        this.goods_id = in.readInt();
        this.product_id = in.readInt();
        this.order_id = in.readInt();
        this.cart_id = in.readInt();
        this.p_name = in.readString();
        this.c_name = in.readString();
        this.x_name = in.readString();
        this.order_list = in.createTypedArrayList(OrderListBean.CREATOR);
    }

    public static final Creator<OrderObj> CREATOR = new Creator<OrderObj>() {
        @Override
        public OrderObj createFromParcel(Parcel source) {
            return new OrderObj(source);
        }

        @Override
        public OrderObj[] newArray(int size) {
            return new OrderObj[size];
        }
    };
}
