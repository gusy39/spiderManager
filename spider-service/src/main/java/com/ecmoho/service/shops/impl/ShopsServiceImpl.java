package com.ecmoho.service.shops.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecmoho.mapper.CookiesMapper;
import com.ecmoho.mapper.ShopsMapper;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.models.ShopsDTO;
import com.ecmoho.service.shops.ShopsService;
@Service("shopsServiceImpl")
public class ShopsServiceImpl implements ShopsService{

	@Autowired
    private ShopsMapper shopsMapper;
	@Autowired
	private CookiesMapper cookiesMapper;
	

	public List<ShopsDTO> selectAllShops() {
		return shopsMapper.selectAllShops();
	}
	@Transactional
	public int updateShopsById(Map<String, Object> parameter) {
		System.out.println(parameter);
		if(parameter==null){
			parameter=new HashMap<String,Object>();
		}
		String updateType=parameter.get("updateType").toString();
		String shopCode=parameter.get("shopCode").toString();
		
	
		ShopsDTO shopsDTO=new ShopsDTO();
		shopsDTO.setShopCode(shopCode);
		
	    Map<String,String> shopBusinessCodeMap=null;
		if("delete".equals(updateType)){
			shopBusinessCodeMap=new HashMap<String,String>();
			shopBusinessCodeMap.put("shopCode", shopCode);
			//删除cookie表中所有记录
			cookiesMapper.deleteByShopCodeBusiness(shopBusinessCodeMap);
			//删除店铺记录。置delFlag标志位为1
			shopsDTO.setDelFlag(1);
			shopsMapper.updateShopsById(shopsDTO);
		}else if("update".equals(updateType)){
			String shopName=parameter.get("shopName").toString();
			String userName=parameter.get("userName").toString();
			String pwd=parameter.get("pwd").toString();
			shopsDTO.setShopName(shopName);
			shopsDTO.setUserName(userName);
			shopsDTO.setPwd(pwd);
			//更新店铺为可用
			shopsDTO.setDelFlag(0);
			shopsMapper.updateShopsById(shopsDTO);
			//查询出表中该店铺原所有业务
			Map<String,String> cookieMap=new HashMap<String,String>();
			cookieMap.put("type", "2");
			cookieMap.put("SBCodesSql", "'"+shopCode+"'");
			List<CookiesDTO> cookieList= cookiesMapper.selectCookies(cookieMap);
		    List<String> existbusinessCodeList=new ArrayList<String>();
			String businessCodeStr=parameter.get("businessArr").toString();
			List<String> businessCodeList=new ArrayList<String>();
			if(!"".equals(businessCodeStr)){
				Collections.addAll(businessCodeList, businessCodeStr.split(","));
			}
			if(cookieList!=null&&cookieList.size()>0){
				//遍历cookie表中记录，如果提交列表中不存在，则删除
				for(CookiesDTO existCookiesDTO:cookieList){
					String existBusinessCode=existCookiesDTO.getBusinessCode();
					existbusinessCodeList.add(existBusinessCode);
				
					if(!businessCodeList.contains(existBusinessCode)){
						shopBusinessCodeMap=new HashMap<String,String>();
						shopBusinessCodeMap.put("shopCode", shopCode);
						shopBusinessCodeMap.put("businessCode", existBusinessCode);
						cookiesMapper.deleteByShopCodeBusiness(shopBusinessCodeMap);
					}
				}
			}
			if(businessCodeList!=null&&businessCodeList.size()>0){
				//遍历businessCode提交记录，如果表中没有该记录，则添加到cookie表中
				for(String businessCode:businessCodeList){
					if(!existbusinessCodeList.contains(businessCode)){
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
	@Transactional
	public int insertShops(Map<String, Object> parameter) {
		if(parameter==null){
			parameter=new HashMap<String,Object>();
		}
		String shopCode=parameter.get("shopCode").toString();
		String shopName=parameter.get("shopName").toString();
		String userName=parameter.get("userName").toString();
		String pwd=parameter.get("pwd").toString();
		String businessCodeStr=parameter.get("businessArr").toString();
		List<String> businessCodeList=new ArrayList<String>();
		if(!"".equals(businessCodeStr)){
			Collections.addAll(businessCodeList, businessCodeStr.split(","));
		}
		ShopsDTO shopsDTO=new ShopsDTO();
		shopsDTO.setShopCode(shopCode);
		shopsDTO.setShopName(shopName);
		shopsDTO.setUserName(userName);
		shopsDTO.setPwd(pwd);
		shopsDTO.setDelFlag(0);
		shopsDTO.setSort(1);
		shopsDTO.setStatus(1);
		shopsDTO.setDelFlag(0);
		shopsMapper.insertShops(shopsDTO);
		
		if(businessCodeList!=null&&businessCodeList.size()>0){
			//遍历businessCode提交记录，如果表中没有该记录，则添加到cookie表中
			for(String businessCode:businessCodeList){
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
