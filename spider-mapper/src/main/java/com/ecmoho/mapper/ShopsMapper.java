package com.ecmoho.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ecmoho.common.persistent.BaseMapper;
import com.ecmoho.models.BusinessDTO;
import com.ecmoho.models.ShopsDTO;
/**
 * 
 * @author meidejing
 * 店铺管理接口
 */
@Component("shopsMapper")
public interface ShopsMapper extends BaseMapper {
    //查询单个店铺
	ShopsDTO selectShopsById(ShopsDTO shopsDTO);
    //查询全部店铺
    List<ShopsDTO> selectAllShops();
    //更新店铺信息
    //删除店铺（为假删除，设置deleFlag=0）
    int updateShopsById(ShopsDTO shopsDTO);
    //插入一个店铺
    int insertShops(ShopsDTO shopsDTO);
}