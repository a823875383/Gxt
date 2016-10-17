package com.jsqix.gxt.app.obj;

import java.io.Serializable;
import java.util.List;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/17.
 */

public class BankListResult extends BaseBean{

    /**
     * id : 127248
     * user_id : 108724
     * account_type : 102901
     * bank_no : 102801
     * bank_account : 5678
     * user_name : 扣扣
     * phone : 18756235632
     * add_time : 2016-09-30 18:47:28
     */

    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable{
        private int id;
        private int user_id;
        private int account_type;
        private int bank_no;
        private String bank_account;
        private String user_name;
        private String phone;
        private String add_time;

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

        public int getAccount_type() {
            return account_type;
        }

        public void setAccount_type(int account_type) {
            this.account_type = account_type;
        }

        public int getBank_no() {
            return bank_no;
        }

        public void setBank_no(int bank_no) {
            this.bank_no = bank_no;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
