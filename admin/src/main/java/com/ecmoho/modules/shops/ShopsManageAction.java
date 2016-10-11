package com.ecmoho.modules.shops;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.ecmoho.models.BusinessDTO;
import com.ecmoho.models.ShopsDTO;
import com.ecmoho.service.shops.BusinessService;
import com.ecmoho.service.shops.ShopsService;

@Component("ShopsManagePR")
public class ShopsManageAction{
	@Autowired
	private ShopsService shopsService;
	@Autowired
	private BusinessService businessService;
	@DataProvider
	public List<ShopsDTO> selectAllShops() {
		return shopsService.selectAllShops();
	};
	//查询全部业务
	@Expose
	public List<BusinessDTO> selectAllBusiness(){
	   	return businessService.selectAllBusiness();
	};
	
	//正常是执行更新
	//删除是更新delFlag标志位为1
	@DataResolver
    public int updateShopsById(Map<String, Object> parameter) {
    	return shopsService.updateShopsById(parameter);
    };
    //插入一个店铺,和对应这家店铺的选中的多个业务
    @DataResolver
    public int insertShops(Map<String, Object> parameter){
    	return shopsService.insertShops(parameter);
    };
}
