package com.ecmoho.models;

import com.ecmoho.common.dto.BaseDTO;

import java.util.List;

/**
 * 
 * @author meidejing
 * 店铺 pojo
 */
public class ShopsDTO extends BaseDTO {
    private Integer id;

    private String shopName;

    private String shopCode;

    private String cookie;

    private Integer sort;

    private Integer status;

    private String descript;

    private String userName;

    private String pwd;

    private String wlbRedUrl;
    
    private int delFlag;
    
    private List<BusinessDTO> BusinessDTOList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie == null ? null : cookie.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getWlbRedUrl() {
        return wlbRedUrl;
    }

    public void setWlbRedUrl(String wlbRedUrl) {
        this.wlbRedUrl = wlbRedUrl == null ? null : wlbRedUrl.trim();
    }

	public List<BusinessDTO> getBusinessDTOList() {
		return BusinessDTOList;
	}

	public void setBusinessDTOList(List<BusinessDTO> businessDTOList) {
		BusinessDTOList = businessDTOList;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
    
}