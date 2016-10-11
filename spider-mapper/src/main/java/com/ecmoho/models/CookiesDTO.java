package com.ecmoho.models;


import com.ecmoho.common.dto.BaseDTO;

import java.util.Date;

/**
 * 
 * @author meidejing
 * cookie pojo 
 */
public class CookiesDTO extends BaseDTO {
    private Integer id;

    private String shopCode;

    private String businessCode;

    private Integer status;

    private String cookie;

    private Date failTime;

    private Date lastTime;

    private BusinessDTO businessDTO;
    
    private ShopsDTO shopsDTO;

    //////////////////////////////////////////////////////////

    private String userName;

    private String pwd;

    private String loginUrl;

    private String cookieUrl;

    private String seleniumSpiderBean;
    
    private String shopName;
    
    private String describe;

    public String getSeleniumSpiderBean() {
        return seleniumSpiderBean;
    }

    public void setSeleniumSpiderBean(String seleniumSpiderBean) {
        this.seleniumSpiderBean = seleniumSpiderBean;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getCookieUrl() {
        return cookieUrl;
    }

    public void setCookieUrl(String cookieUrl) {
        this.cookieUrl = cookieUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie == null ? null : cookie.trim();
    }

	public BusinessDTO getBusinessDTO() {
		return businessDTO;
	}

	public void setBusinessDTO(BusinessDTO businessDTO) {
		this.businessDTO = businessDTO;
	}

	public ShopsDTO getShopsDTO() {
		return shopsDTO;
	}

	public void setShopsDTO(ShopsDTO shopsDTO) {
		this.shopsDTO = shopsDTO;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        if(1 == status){
            this.failTime = null;
        }
        return "CookiesDTO{" +
                "shopCode='" + shopCode + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", failTime=" + failTime +
                ", lastTime=" + lastTime +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}