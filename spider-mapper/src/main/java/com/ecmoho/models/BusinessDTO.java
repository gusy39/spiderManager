package com.ecmoho.models;


import com.ecmoho.common.dto.BaseDTO;

import java.util.Date;
import java.util.List;
/**
 * 
 * @author meidejing
 * 业务操作pojo
 */
public class BusinessDTO extends BaseDTO {
    private Integer id;

    private String businessCode;

    private String describe;

    private String loginUrl;

    private String cookieUrl;

    private Date modified;

    private Date created;
    
    private int delFlag;
    
    private String seleniumSpiderBean;
    
    private List<ShopsDTO> shopsDTOList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl == null ? null : loginUrl.trim();
    }

    public String getCookieUrl() {
        return cookieUrl;
    }

    public void setCookieUrl(String cookieUrl) {
        this.cookieUrl = cookieUrl == null ? null : cookieUrl.trim();
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getSeleniumSpiderBean() {
		return seleniumSpiderBean;
	}

	public void setSeleniumSpiderBean(String seleniumSpiderBean) {
		this.seleniumSpiderBean = seleniumSpiderBean;
	}

	public List<ShopsDTO> getShopsDTOList() {
		return shopsDTOList;
	}

	public void setShopsDTOList(List<ShopsDTO> shopsDTOList) {
		this.shopsDTOList = shopsDTOList;
	}
    
}