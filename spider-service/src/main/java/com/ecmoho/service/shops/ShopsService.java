package com.ecmoho.service.shops;

import java.util.List;
import java.util.Map;

import com.ecmoho.models.ShopsDTO;

public interface ShopsService {
	 
    //查询全部店铺
    List<ShopsDTO>selectAllShops();
    //更新店铺信息
    //删除店铺（为假删除，设置deleFlag=0）
    int updateShopsById(Map<String, Object> parameter) ;
    //插入一个店铺
    int insertShops(Map<String, Object> parameter);
}
