package com.jsqix.gxt.app.obj;

import java.io.Serializable;

/**
 * Created by dongqing on 16/10/8.
 */

public class AddressObj implements Serializable {
    private int id;
    private int user_id;
    private int province_id;
    private int city_id;
    private int county_id;
    private String street_address;
    private int whether_default;
    private String phone;
    private String consignee;
    private String addtime;
    private String pro_name;
    private String city_name;
    private String contry_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getCounty_id() {
        return county_id;
    }

    public void setCounty_id(int county_id) {
        this.county_id = county_id;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public int getWhether_default() {
        return whether_default;
    }

    public void setWhether_default(int whether_default) {
        this.whether_default = whether_default;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getContry_name() {
        return contry_name;
    }

    public void setContry_name(String contry_name) {
        this.contry_name = contry_name;
    }
}
