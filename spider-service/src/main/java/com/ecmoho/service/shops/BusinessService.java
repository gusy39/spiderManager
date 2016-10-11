package com.ecmoho.service.shops;

import java.util.List;
import java.util.Map;

import com.ecmoho.models.BusinessDTO;

public interface BusinessService {
	//查询全部数据
    List<BusinessDTO> selectAllBusiness();
    //查询某一个店铺信息
    BusinessDTO selectBusinessById(BusinessDTO businessDTO);
    //删除业务（为假删除，设置deleFlag=0）
    int updateBusinessById(Map<String, Object> parameter);
    //插入一个业务记录
    int insertBusiness(Map<String, Object> parameter);
    
}
