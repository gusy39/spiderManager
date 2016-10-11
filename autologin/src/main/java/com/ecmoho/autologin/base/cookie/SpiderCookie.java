package com.ecmoho.autologin.base.cookie;

import com.ecmoho.cache.CacheToPutExecption;
import com.ecmoho.cache.RedisCacheProvider;
import com.ecmoho.common.util.Loggers;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.service.autologin.CookieService;

import java.io.Serializable;
import java.util.*;

/**
 * Created by 许巧生 on 2016/6/6.
 */
public class SpiderCookie implements Serializable {

    private static final long serialVersionUID = 1802998747586288766L;

    private String shopName;
    private String shopCode;
    private String bsnsCode;
    private String cookie;

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getBsnsCode() {
        return bsnsCode;
    }

    public void setBsnsCode(String bsnsCode) {
        this.bsnsCode = bsnsCode;
    }

    public SpiderCookie(String shopName, String shopCode, String bsnsCode, String cookie) {
        this.shopName = shopName;
        this.shopCode = shopCode;
        this.bsnsCode = bsnsCode;
        this.cookie = cookie;
    }

    /**
     * cookie初始化
     */
    public static class CookieHolder {

        public static void init(CookieService cookieService, RedisCacheProvider redisCacheProvider) {
            final List<CookiesDTO> cookieList = cookieService.selectAllCookies();

            for (CookiesDTO dto : cookieList) {
                SpiderCookie cookie = new SpiderCookie(dto.getShopName(), dto.getShopCode(),
                        dto.getBusinessCode(), dto.getCookie());
                try {
                    redisCacheProvider.put(dto.getShopCode() + "_" + dto.getBusinessCode(), cookie);
                } catch (CacheToPutExecption e) {
                    Loggers.redisLogger.info(dto.getShopCode() + "_" + dto.getBusinessCode() + "init failed.");
                }
            }
        }
    }
}