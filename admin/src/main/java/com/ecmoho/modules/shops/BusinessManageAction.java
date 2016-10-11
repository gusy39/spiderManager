package com.ecmoho.modules.shops;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.ecmoho.models.BusinessDTO;
import com.ecmoho.models.ShopsDTO;
import com.ecmoho.service.shops.BusinessService;
import com.ecmoho.service.shops.ShopsService;
@Component("BusinessManagePR")
public class BusinessManageAction {
	@Autowired
	private ShopsService shopsService;
	@Autowired
	private BusinessService businessService;
	
	 //查询全部业务数据
	@DataProvider
	public List<BusinessDTO> selectAllBusiness(){
	   	return businessService.selectAllBusiness();
	};
	//查询全部店铺
	@Expose
	public List<ShopsDTO> selectAllShops(){
	   	return shopsService.selectAllShops();
	};
	@DataResolver
    public int updateBusinessById(Map<String, Object> parameter){
    	return businessService.updateBusinessById(parameter);
    };
    @DataResolver
    public int insertBusiness(Map<String, Object> parameter){
    	return businessService.insertBusiness(parameter);
    };
}
