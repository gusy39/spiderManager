package com.ecmoho.common.dto;

import com.ecmoho.common.util.CommonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 所以的数据结构都从基类派生
 *
 * @author ningmd
 */
public abstract class BaseDTO extends AbstractDTO {

	private static final long serialVersionUID = -8126481845364459807L;

	@JsonIgnore
	private boolean loadBlobColumn=false;//如果有大数据列，是否加载大内容列

	public boolean isLoadBlobColumn() {
		return loadBlobColumn;
	}

	public void setLoadBlobColumn(boolean loadBlobColumn) {
		this.loadBlobColumn = loadBlobColumn;
	}

	public abstract Integer getId();

	//方便做时间段查询条件 扩张连个字段
	@JsonIgnore
	private String fieldBeginDate;
	@JsonIgnore
	private String fieldEndDate;

	@JsonIgnore
	private String field2BeginDate;
	@JsonIgnore
	private String field2EndDate;

	@JsonIgnore
	private String _sortCol;//排序列名
	@JsonIgnore
	private String _sortASC;//对应的方向 升序或降序 ASC or DESC

	@JsonIgnore
	private Long lastId;
	@JsonIgnore
	private Integer limit;

	@JsonIgnore
	private Map<String, Object> extCondition = new HashMap<>();

	public String getFieldBeginDate() {
		return fieldBeginDate;
	}

	public void setFieldBeginDate(String fieldBeginDate) {
		this.fieldBeginDate = fieldBeginDate;
	}

	public String getFieldEndDate() {
		return fieldEndDate;
	}

	public void setFieldEndDate(String fieldEndDate) {
		this.fieldEndDate = fieldEndDate;
	}

	public String getField2BeginDate() {
		return field2BeginDate;
	}

	public void setField2BeginDate(String field2BeginDate) {
		this.field2BeginDate = field2BeginDate;
	}

	public String getField2EndDate() {
		return field2EndDate;
	}

	public void setField2EndDate(String field2EndDate) {
		this.field2EndDate = field2EndDate;
	}

	@JsonIgnore
	public String getOrderCol() {
		if (StringUtils.isEmpty(_sortCol)) {
			return null;
		}
		String[] sortCols = StringUtils.split(_sortCol, ',');
		String[] sortASCs = StringUtils.split(StringUtils.lowerCase(_sortASC), ',');
		String orderCol = "";
		for (int i=0;i<sortCols.length;i++) {
			orderCol = orderCol + sortCols[i].replaceAll("[A-Z]", "_$0")+" ";
			if (sortASCs!=null && sortASCs[i] != null) {
				orderCol = orderCol + sortASCs[i];
			}
			orderCol = orderCol + " ,";
		}
		if (orderCol.length() > 0) {
			orderCol = orderCol.substring(0, orderCol.length() - 1);
		}
		if (StringUtils.isBlank(orderCol)) {
			return null;
		}
		return orderCol;
	}

	public String getSortCol() {
		return _sortCol;
	}

	/**
	 * 排序数据库字段 建议后台使用
	 * @param sortCol
	 */
	public void setSortCol(String sortCol) {
		if (sortCol == null) {
			return;
		}
		String[] sortCols = StringUtils.split(StringUtils.lowerCase(sortCol), ',');
		for (String orderByStr : sortCols) {
			if (!CommonUtils.isDateBaseColumn(orderByStr)) {
				throw new IllegalArgumentException("排序字段" + orderByStr + "不是合法值");
			}
		}
		this._sortCol = sortCol;
	}

	public String getSortASC() {
		return _sortASC;
	}

	/**
	 * 设置排序方式向.
	 *
	 * @param sortASC 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setSortASC(String sortASC) {
		this._sortASC = sortASC;
		if (StringUtils.isEmpty(sortASC)) {
			return;
		}
		//检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(sortASC), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(Pager.DESC, orderStr) && !StringUtils.equals(Pager.ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this._sortASC = StringUtils.lowerCase(sortASC);
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public Map<String, ?> getExtCondition() {
		return extCondition;
	}

	public void setExtCondition(Map<String, Object> extCondition) {
		this.extCondition = extCondition;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void addExtCondition(String name,Object value) {
		extCondition.put(name, value);
	}
	public void removeExtCondition(String name) {
		extCondition.remove(name);
	}

}
