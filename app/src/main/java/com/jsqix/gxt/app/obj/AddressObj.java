package com.jsqix.gxt.app.obj;

/**
 * Created by dongqing on 16/9/30.
 */

public class AddressObj {

    /**
     * city_id : 24
     * parent_id : 1
     * city_name : 陕西
     * city_type : 1
     * city_area : 5
     */

    private int city_id;
    private int parent_id;
    private String city_name;
    private int city_type;
    private int city_area;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity_type() {
        return city_type;
    }

    public void setCity_type(int city_type) {
        this.city_type = city_type;
    }

    public int getCity_area() {
        return city_area;
    }

    public void setCity_area(int city_area) {
        this.city_area = city_area;
    }
}
