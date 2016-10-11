package com.ecmoho.mapper;

import com.ecmoho.common.persistent.BaseMapper;
import com.ecmoho.models.CookiesDTO;

import java.util.List;
import java.util.Map;

public interface CookiesMapper extends BaseMapper {
    
	List<CookiesDTO> selectAllCookies();
	
    List<CookiesDTO> selectCookiesBySBCodes(Map<String, String> map);
    
    List<CookiesDTO> selectCookies(Map<String, String> map);

    int deleteByPrimaryKey(Integer id);
    
    int deleteByShopCodeBusiness(Map<String, String> map);

    int insert(CookiesDTO record);

    int insertSelective(CookiesDTO record);

    CookiesDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CookiesDTO record);

    int updateByPrimaryKeyWithBLOBs(CookiesDTO record);

    int updateByPrimaryKey(CookiesDTO record);

    int updateBySBCode(CookiesDTO record);

    int updateStatusBySBCode(CookiesDTO record);
}