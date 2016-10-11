package com.ecmoho.common.service;

import com.ecmoho.common.dto.BaseDTO;
import com.ecmoho.common.dto.Pager;
import com.ecmoho.common.exception.ServiceException;

import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 *
 */
public interface BaseService<T extends BaseDTO, PK extends java.io.Serializable> {

    Pager<T> queryBySelective(Pager<T> page, T condition) throws ServiceException;

    List<T> queryBySelective(T condition) throws ServiceException;

    /**
     * 查询前面第几条数据
     * @param limit
     * @param condition
     * @return
     * @throws ServiceException
     */
    List<T> queryBySelectiveByLimit(int limit,T condition) throws ServiceException;

    /**
     * 查询唯一的记录，不存在或存在多条返回第一条
     * @param condition
     * @return
     * @throws ServiceException
     */
    T getUniqueBySelective(T condition) throws ServiceException;

    int deleteByPrimaryKey(PK id) throws ServiceException;

    int insert(T record) throws ServiceException;

    int insertSelective(T record) throws ServiceException;

    T selectByPrimaryKey(PK id) throws ServiceException;

    int updateByPrimaryKeySelective(T record) throws ServiceException;

    int updateByPrimaryKey(T record) throws ServiceException;

    Long getModelIdByLock(Long id);

    T getModelByLock(Long id);

    boolean isExist(T record);

    boolean isExistSingle(T record);
}
