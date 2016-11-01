package com.jsqix.gxt.app.obj;

import gxt.jsqix.com.mycommon.base.bean.BaseBean;

/**
 * Created by dongqing on 2016/10/21.
 */

public class UserBalanceResult extends BaseBean {

    /**
     * id : 108725
     * user_id : 108724
     * acct_type : 100104
     * acc_balance : 98903378
     * account_status : 0
     * plat_id : 102204
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int id;
        private int user_id;
        private int acct_type;
        private double acc_balance;
        private int account_status;
        private int plat_id;

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

        public int getAcct_type() {
            return acct_type;
        }

        public void setAcct_type(int acct_type) {
            this.acct_type = acct_type;
        }

        public double getAcc_balance() {
            return acc_balance;
        }

        public void setAcc_balance(double acc_balance) {
            this.acc_balance = acc_balance;
        }

        public int getAccount_status() {
            return account_status;
        }

        public void setAccount_status(int account_status) {
            this.account_status = account_status;
        }

        public int getPlat_id() {
            return plat_id;
        }

        public void setPlat_id(int plat_id) {
            this.plat_id = plat_id;
        }
    }
}
