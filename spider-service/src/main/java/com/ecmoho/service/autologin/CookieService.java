package com.ecmoho.service.autologin;

//import CookiesDTO;

import com.ecmoho.models.CookiesDTO;

import java.util.List;


/**
 * Created by 许巧生 on 2016/9/22.
 */
public interface CookieService {
	List<CookiesDTO> selectAllCookies();
	
    int addCookie(String BusinessCode, String ShopCode, String cookie);

    int updateCookie(String BusinessCode, String ShopCode, String cookie);

    int updateCookieStatus(CookiesDTO dto);
}
