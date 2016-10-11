package com.ecmoho.common.persistent;


import com.ecmoho.common.dto.BaseDTO;
import com.ecmoho.common.dto.Pager;
import com.ecmoho.common.exception.DaoException;
import com.ecmoho.common.exception.MapperNullException;
import com.ecmoho.common.util.ReflectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public abstract class BaseDaoImpl<T extends BaseDTO, PK extends java.io.Serializable> implements BaseDao<T, PK> {
    public Logger logger = Logger.getLogger(getClass());
    private BaseMapper baseMapper;

    public BaseMapper getBaseMapper() {
        if (baseMapper == null) {
            baseMapper = getSupperBaseMapper();
        }
        return baseMapper;
    }

    public void setBaseMapper(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    public abstract BaseMapper getSupperBaseMapper();

    @Override
    public int insert(T record) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        if (record == null) {
            throw new MapperNullException("insert record is null");
        }
        Method setCreateTime = ReflectionUtils.getDeclaredMethod(record, "setCreateTime", new Class[]{Date.class});
        if(setCreateTime!=null) {
            try {
                setCreateTime.invoke(record, new Date());
            }catch (Exception e) {
            }
        }
        Method setUpdateTime = ReflectionUtils.getDeclaredMethod(record, "setUpdateTime", new Class[]{Date.class});
        if(setUpdateTime!=null) {
            try {
                setUpdateTime.invoke(record, new Date());
            } catch (Exception e) {
            }
        }
        Object o = null;
        try {
            o = ReflectionUtils.invokeMethod(baseMapper, BaseMapper.DEFINE_INSERT_METHOD, new Object[]{record});
        } catch (Exception e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new DaoException("访问数据层错误");
        }
        return (Integer) o;
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        if (id == null) {
            throw new MapperNullException("deleteByPrimaryKey id is null");
        }
        Object o = null;
        try {
            o = ReflectionUtils.invokeMethod(baseMapper, BaseMapper.DEFINE_DELETE_BY_PRIMARY_KEY_METHOD, new Object[]{id});
        } catch (Exception e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new DaoException("访问数据层错误");
        }
        return (Integer) o;
    }

    @Override
    public int insertSelective(T record) {
        if (getBaseMapper()  == null) {
            throw new MapperNullException("baseMapper is null");
        }
        if (record == null) {
            throw new MapperNullException("insertSelective record is null");
        }
        Method setCreateTime = ReflectionUtils.getDeclaredMethod(record, "setCreateTime", new Class[]{Date.class});
        if(setCreateTime!=null) {
            try {
                setCreateTime.invoke(record, new Date());
            } catch (Exception e) {
            }
        }
        Method setUpdateTime = ReflectionUtils.getDeclaredMethod(record, "setUpdateTime", new Class[]{Date.class});
        if(setUpdateTime!=null) {
            try {
                setUpdateTime.invoke(record, new Date());
            } catch (Exception e) {
            }
        }
        Object o = null;
        try {
            o = ReflectionUtils.invokeMethod(baseMapper, BaseMapper.DEFINE_INSERT_SELECTIVE_METHOD, new Object[]{record});
        } catch (Exception e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new DaoException("访问数据层错误");
        }
        return (Integer) o;
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        if (id == null) {
            throw new MapperNullException("selectByPrimaryKey id is null");
        }
        Object o = null;
        try {
            o = ReflectionUtils.invokeMethod(baseMapper, BaseMapper.DEFINE_SELECT_BY_PRIMARY_KEY_METHOD, new Object[]{id});
        } catch (Exception e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new DaoException("访问数据层错误");
        }
        return (T) o;
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        if (record == null) {
            throw new MapperNullException("updateByPrimaryKeySelective record is null");
        }
        Method setUpdateTime = ReflectionUtils.getDeclaredMethod(record, "setUpdateTime", new Class[]{Date.class});
        if(setUpdateTime!=null) {
            try {
                setUpdateTime.invoke(record, new Date());
            } catch (Exception e) {
            }
        }
        Object o = null;
        try {
            o = ReflectionUtils.invokeMethod(baseMapper, BaseMapper.DEFINE_UPDATE_BY_PRIMARY_KEY_SELECTIVE_METHOD, new Object[]{record});
        } catch (Exception e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new DaoException("访问数据层错误");
        }
        return (Integer) o;
    }

    @Override
    public int updateByPrimaryKey(T record) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        if (record == null) {
            throw new MapperNullException("updateByPrimaryKey record is null");
        }
        Method setUpdateTime = ReflectionUtils.getDeclaredMethod(record, "setUpdateTime", new Class[]{Date.class});
        if(setUpdateTime!=null) {
            try {
                setUpdateTime.invoke(record, new Date());
            } catch (Exception e) {
            }
        }
        Object o = null;
        try {
            o = ReflectionUtils.invokeMethod(baseMapper, BaseMapper.DEFINE_UPDATE_BY_PRIMARY_KEY_METHOD, new Object[]{record});
        } catch (Exception e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new DaoException("访问数据层错误");
        }
        return (Integer) o;
    }

    @Override
    public Pager<T> queryBySelective(Pager<T> page, T condition) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        List<T> resultList = baseMapper.queryBySelective(page, condition);
        page.setResult(resultList);
        return page;
    }

    @Override
    public List<T> queryBySelective(T condition) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        return baseMapper.queryBySelective(condition);
    }

    @Override
    public Long getModelIdByLock(Long id) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        return baseMapper.getModelIdByLock(id);
    }

    @Override
    public T getModelByLock(Long id) {
        if (getBaseMapper() == null) {
            throw new MapperNullException("baseMapper is null");
        }
        return baseMapper.getModelByLock(id);
    }

    /**
     * 判断是否存在某条记录
     * tad
     * @param record
     * @return
     */
    @Override
    public boolean isExist(T record){
        boolean isExist=false;
        List<T> list=queryBySelective(record);
        if(list!=null&&list.size()>0){
            isExist=true;
        }
        return isExist;
    }

    /**
     * 判断是否仅存一条唯一数据
     * @param record
     * @return
     */
    @Override
    public boolean isExistSingle(T record){
        boolean isExist=false;
        List<T> list=queryBySelective(record);
        if(list!=null&&list.size()==1){
            isExist=true;
        }
        return isExist;
    }
}
