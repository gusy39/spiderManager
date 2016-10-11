package com.ecmoho.service.autologin.impl;

import com.ecmoho.mapper.CookiesMapper;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.service.autologin.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookieServiceImpl implements CookieService {

    @Autowired
    private CookiesMapper cookiesMapper;
    
    public List<CookiesDTO> selectAllCookies(){
    	return cookiesMapper.selectAllCookies();
    };
    
    public int addCookie(String BusinessCode, String ShopCode, String cookie) {

        CookiesDTO cookiesDTO = new CookiesDTO();
        cookiesDTO.setBusinessCode(BusinessCode);
        cookiesDTO.setShopCode(ShopCode);
        cookiesDTO.setCookie(cookie);
        cookiesDTO.setStatus(1);
        return cookiesMapper.insert(cookiesDTO);
    }

    public int updateCookie(String BusinessCode, String ShopCode, String cookie) {

        CookiesDTO cookiesDTO = new CookiesDTO();
        cookiesDTO.setBusinessCode(BusinessCode);
        cookiesDTO.setShopCode(ShopCode);
        cookiesDTO.setCookie(cookie);
        return cookiesMapper.updateBySBCode(cookiesDTO);
    }

    public int updateCookieStatus(CookiesDTO dto) {
        return cookiesMapper.updateStatusBySBCode(dto);
    }

}
