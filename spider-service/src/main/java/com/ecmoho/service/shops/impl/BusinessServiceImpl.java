package com.ecmoho.service.shops.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecmoho.mapper.BusinessMapper;
import com.ecmoho.mapper.CookiesMapper;
import com.ecmoho.models.BusinessDTO;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.models.ShopsDTO;
import com.ecmoho.service.shops.BusinessService;
@Service
public class BusinessServiceImpl implements BusinessService{
    @Autowired
	private BusinessMapper businessMapper;
	@Autowired
	private CookiesMapper cookiesMapper;
	//查询全部数据
	public List<BusinessDTO> selectAllBusiness() {
		return businessMapper.selectAllBusiness();
	}
	//查询某一个店铺信息
	public BusinessDTO selectBusinessById(BusinessDTO businessDTO) {
		return businessMapper.selectBusinessById(businessDTO);
	}
	//更新业务信息
	@Transactional
	public int updateBusinessById(Map<String, Object> parameter) {
		System.out.println(parameter);
		if(parameter==null){
			parameter=new HashMap<String,Object>();
		}
		String updateType=parameter.get("updateType")==null?"":parameter.get("updateType").toString();
		String businessCode=parameter.get("businessCode")==null?"":parameter.get("businessCode").toString();
	
		
		BusinessDTO businessDTO=new BusinessDTO();
		businessDTO.setBusinessCode(businessCode);
	    
		
	    Map<String,String> shopBusinessCodeMap=null;
		if("delete".equals(updateType)){
			shopBusinessCodeMap=new HashMap<String,String>();
			shopBusinessCodeMap.put("businessCode", businessCode);
			//删除cookie表中所有记录
			cookiesMapper.deleteByShopCodeBusiness(shopBusinessCodeMap);
			//删除店铺记录。置delFlag标志位为1
			businessDTO.setDelFlag(1);
			businessMapper.updateBusinessById(businessDTO);
		}else if("update".equals(updateType)){
			
			String describe=parameter.get("describe")==null?"":parameter.get("describe").toString();
			String loginUrl=parameter.get("loginUrl")==null?"":parameter.get("loginUrl").toString();
			String cookieUrl=parameter.get("cookieUrl")==null?"":parameter.get("cookieUrl").toString();
			String seleniumSpiderBean=parameter.get("seleniumSpiderBean")==null?"":parameter.get("seleniumSpiderBean").toString();
			businessDTO.setDescribe(describe);
		    businessDTO.setLoginUrl(loginUrl);
		    businessDTO.setCookieUrl(cookieUrl);
		    businessDTO.setSeleniumSpiderBean(seleniumSpiderBean);
		    businessDTO.setModified(new Date());
			//更新店铺为可用
			businessDTO.setDelFlag(0);
			businessMapper.updateBusinessById(businessDTO);
			//查询出表中该店铺原所有业务
			Map<String,String> cookieMap=new HashMap<String,String>();
			cookieMap.put("type", "3");
			cookieMap.put("SBCodesSql", "'"+businessCode+"'");
			List<CookiesDTO> cookieList= cookiesMapper.selectCookies(cookieMap);
		    List<String> existshopCodeList=new ArrayList<String>();
			String shopCodeStr=parameter.get("shopArr").toString();
			List<String> shopCodeList=new ArrayList<String>();
			if(!"".equals(shopCodeStr)){
				Collections.addAll(shopCodeList, shopCodeStr.split(","));
			}
			if(cookieList!=null&&cookieList.size()>0){
				//遍历cookie表中记录，如果提交列表中不存在，则删除
				for(CookiesDTO existCookiesDTO:cookieList){
					String existShopCode=existCookiesDTO.getShopCode();
					existshopCodeList.add(existShopCode);
				
					if(!shopCodeList.contains(existShopCode)){
						shopBusinessCodeMap=new HashMap<String,String>();
						shopBusinessCodeMap.put("businessCode", businessCode);
						shopBusinessCodeMap.put("shopCode", existShopCode);
						cookiesMapper.deleteByShopCodeBusiness(shopBusinessCodeMap);
					}
				}
			}
			if(shopCodeList!=null&&shopCodeList.size()>0){
				//遍历shopCode提交记录，如果表中没有该记录，则添加到cookie表中
				for(String shopCode:shopCodeList){
					if(!existshopCodeList.contains(shopCode)){
						CookiesDTO cookiesDTO=new CookiesDTO();
						cookiesDTO.setShopCode(shopCode);
						cookiesDTO.setBusinessCode(businessCode);
						cookiesDTO.setStatus(0);
						cookiesMapper.insertSelective(cookiesDTO);
					}
				}
			}
		}
		return 1;
	}
	//插入一条业务信息
	@Transactional
	public int insertBusiness(Map<String, Object> parameter){
		System.out.println(parameter);
		if(parameter==null){
			parameter=new HashMap<String,Object>();
		}
		String businessCode=parameter.get("businessCode").toString();
		String describe=parameter.get("describe")==null?"":parameter.get("describe").toString();
		String loginUrl=parameter.get("loginUrl")==null?"":parameter.get("loginUrl").toString();
		String cookieUrl=parameter.get("cookieUrl")==null?"":parameter.get("cookieUrl").toString();
		String seleniumSpiderBean=parameter.get("seleniumSpiderBean")==null?"":parameter.get("seleniumSpiderBean").toString();
		String shopCodeStr=parameter.get("shopArr")==null?"":parameter.get("shopArr").toString();
		BusinessDTO businessDTO=new BusinessDTO();
		businessDTO.setBusinessCode(businessCode);
	    businessDTO.setDescribe(describe);
	    businessDTO.setLoginUrl(loginUrl);
	    businessDTO.setCookieUrl(cookieUrl);
	    businessDTO.setSeleniumSpiderBean(seleniumSpiderBean);
	    businessDTO.setCreated(new Date());
	    businessMapper.insertBusiness(businessDTO);
		
		List<String> shopCodeList=new ArrayList<String>();
		if(!"".equals(shopCodeStr)){
			Collections.addAll(shopCodeList, shopCodeStr.split(","));
		}
		if(shopCodeList!=null&&shopCodeList.size()>0){
			//遍历shopCode提交记录，如果表中没有该记录，则添加到cookie表中
			for(String shopCode:shopCodeList){
				CookiesDTO cookiesDTO=new CookiesDTO();
				cookiesDTO.setShopCode(shopCode);
				cookiesDTO.setBusinessCode(businessCode);
				cookiesDTO.setStatus(0);
				cookiesMapper.insertSelective(cookiesDTO);
			}
		}
		return 1;
	}

}
