package gxt.jsqix.com.mycommon.base.bean;

import java.io.Serializable;

/**
 * Created by dongqing on 16/9/28.
 */

public class BaseBean implements Serializable{

    /**
     * code : 000
     * msg : 登录成功
     */

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
