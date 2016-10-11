package com.ecmoho.common.persistent;

import com.ecmoho.common.dto.Pager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ningmd
 */
public interface BaseMapper {

    /** 子类需要定义函数 */
    final String DEFINE_INSERT_METHOD="insert";
    final String DEFINE_INSERT_SELECTIVE_METHOD="insertSelective";
    final String DEFINE_DELETE_BY_PRIMARY_KEY_METHOD="deleteByPrimaryKey";
    final String DEFINE_SELECT_BY_PRIMARY_KEY_METHOD="selectByPrimaryKey";
    final String DEFINE_UPDATE_BY_PRIMARY_KEY_SELECTIVE_METHOD="updateByPrimaryKeySelective";
    final String DEFINE_UPDATE_BY_PRIMARY_KEY_METHOD="updateByPrimaryKey";

    <T> List<T> queryBySelective(T param);

    <T> List<T> queryBySelective(Pager<T> pager, T param);

    Long getModelIdByLock(@Param(value="id") Long id);

    <T> T getModelByLock(@Param(value="id") Long id);

}
