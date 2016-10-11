package com.ecmoho.common.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <strong>Title : MapUtils<br></strong>
 * <strong>Description : </strong>@类注释说明写在此处@<br> 
 * <strong>Create on : 2011-11-2<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointellects.com<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改人		修改日期		修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class MapUtils {
	@SuppressWarnings("all")
	public static void putIfNull(Map map,Object key,Object defaultValue) {
		if(key == null)
			throw new IllegalArgumentException("key must be not null");
		if(map == null)
			throw new IllegalArgumentException("map must be not null");
		if(map.get(key) == null) {
			map.put(key, defaultValue);
		}
	}

	public static void populate(Object target,Map<String,Object> parameter){
		if(target==null || parameter==null ||parameter.size()==0){
				return;
		}else{
			try {
				populate(target, parameter,true);
			} catch (Exception e) {
			}
		}
	}

	public static void populate(Object target,Map<String,Object> parameter,boolean filterBlank){
		if(target==null || parameter==null ||parameter.size()==0){
			return;
		}else{
			try {
				Map<String,Object> filterMap = new HashMap<>() ;
 				if(filterBlank) {
					Object value;
					for (Map.Entry<String, Object> entry : parameter.entrySet()) {
						value = entry.getValue();
						if(value==null){
							continue;
						}else if (value instanceof String && ((String) value).length() == 0) {
							continue;
						}else if("fieldBeginDate".equals(entry.getKey()) && entry.getValue() instanceof Date){
							filterMap.put(entry.getKey(), DateUtil.date2String((Date)entry.getValue(),DateUtil.PATTERN_DATE));
							continue;
						}else if("fieldEndDate".equals(entry.getKey()) && entry.getValue() instanceof Date){
							filterMap.put(entry.getKey(), DateUtil.date2String((Date)entry.getValue(),DateUtil.PATTERN_DATE)+" 23:59:59");
							continue;
						}else if("field2BeginDate".equals(entry.getKey()) && entry.getValue() instanceof Date){
							filterMap.put(entry.getKey(), DateUtil.date2String((Date)entry.getValue(),DateUtil.PATTERN_DATE));
							continue;
						}else if("field2EndDate".equals(entry.getKey()) && entry.getValue() instanceof Date){
							filterMap.put(entry.getKey(), DateUtil.date2String((Date)entry.getValue(),DateUtil.PATTERN_DATE)+" 23:59:59");
							continue;
						}
						filterMap.put(entry.getKey(), entry.getValue());
					}
				}
				BeanUtils.populate(target, filterMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> T populate(Class target,Map<String,Object> parameter){
		if(target==null || parameter==null ||parameter.size()==0){
			return null;
		}else{
			try {
				Object o = target.newInstance();
				BeanUtils.populate(o, parameter);
				return null;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
}
