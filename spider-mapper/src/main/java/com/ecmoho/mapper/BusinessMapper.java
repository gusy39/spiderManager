package com.ecmoho.mapper;

import java.util.List;


import com.ecmoho.common.persistent.BaseMapper;
import com.ecmoho.models.BusinessDTO;
/**
 * 
 * @author meidejing
 * 业务信息管理接口
 */
public interface BusinessMapper extends BaseMapper {
    //查询单个数据
    BusinessDTO selectBusinessById(BusinessDTO businessDTO);
    //查询全部数据
    List<BusinessDTO> selectAllBusiness();
    //更新业务信息
    //删除业务（为假删除，设置deleFlag=0）
    int updateBusinessById(BusinessDTO businessDTO);
    //插入一个业务记录
    int insertBusiness(BusinessDTO businessDTO);
}