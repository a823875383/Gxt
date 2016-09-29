package com.jsqix.gxt.app.obj;

import java.io.Serializable;

/**
 * Created by dongqing on 16/9/28.
 */

public class UserObj implements Serializable {
    private String id;
    private String login_name;
    private String login_pwd;
    private String pay_pwd;
    private int status;
    private int sex;
    private String phone;
    private int suppliter_status;
    private String nick_name;
    private int settle_type;
    private int yy_flag;
    private int plat_id;
    private int type;
    private int procure_status;
    private int seller_status;
    private String pay_key;
    private String sys_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }

    public String getPay_pwd() {
        return pay_pwd;
    }

    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSuppliter_status() {
        return suppliter_status;
    }

    public void setSuppliter_status(int suppliter_status) {
        this.suppliter_status = suppliter_status;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(int settle_type) {
        this.settle_type = settle_type;
    }

    public int getYy_flag() {
        return yy_flag;
    }

    public void setYy_flag(int yy_flag) {
        this.yy_flag = yy_flag;
    }

    public int getPlat_id() {
        return plat_id;
    }

    public void setPlat_id(int plat_id) {
        this.plat_id = plat_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProcure_status() {
        return procure_status;
    }

    public void setProcure_status(int procure_status) {
        this.procure_status = procure_status;
    }

    public int getSeller_status() {
        return seller_status;
    }

    public void setSeller_status(int seller_status) {
        this.seller_status = seller_status;
    }

    public String getPay_key() {
        return pay_key;
    }

    public void setPay_key(String pay_key) {
        this.pay_key = pay_key;
    }

    public String getSys_id() {
        return sys_id;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
    }
}
