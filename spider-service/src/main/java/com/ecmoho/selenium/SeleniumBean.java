package com.ecmoho.selenium;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by 许巧生 on 2016/9/27.
 */
public class SeleniumBean implements Serializable {

    private static final long serialVersionUID = 1802998747586288777L;
    /**
     * 登录URL
     */
    private String login_url;
    /**
     * 获取Cookie的URL
     */
    private String red_url;
    /**
     * 登录名称
     */
    private String login_name;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 店铺Code
     */
    private String shopCode;
    /**
     * 业务Code
     */
    private String bnssCode;
    /**
     * 其他参数
     */
    private Map<String, Object> map;

    public SeleniumBean(){}

    public SeleniumBean(String login_url, String red_url, String login_name, String password,
                        String shopCode, String bnssCode, Map<String, Object> map) {
        this.login_url = login_url;
        this.red_url = red_url;
        this.login_name = login_name;
        this.password = password;
        this.shopCode = shopCode;
        this.bnssCode = bnssCode;
        this.map = map;
    }

    public String getLogin_url() {
        return login_url;
    }

    public void setLogin_url(String login_url) {
        this.login_url = login_url;
    }

    public String getRed_url() {
        return red_url;
    }

    public void setRed_url(String red_url) {
        this.red_url = red_url;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getBnssCode() {
        return bnssCode;
    }

    public void setBnssCode(String bnssCode) {
        this.bnssCode = bnssCode;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
