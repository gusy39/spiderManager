package com.ecmoho.common.dto;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;

import javax.servlet.http.HttpServletRequest;

public class PagerHelper {
	public final static int DEFAULT_PAGE_NO = 1;
	
	public final static int DEFAULT_PAGE_SIZE = 20;
	
	/**
	 * mybatis分页参数
	 * @return
	 */
	public static RowBounds getRowBounds(Pager<?> page){
		RowBounds rowBounds = new RowBounds(page.getOffset(),page.getPageSize());
		return rowBounds;
	}
	
	public static <T> Pager<T> createPage(int pageNo,int pageSize){
		Pager<T> page = new Pager<T>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}
	
	public static <T> Pager<T> createDefaultPage(){
		Pager<T> page = new Pager<T>();
		page.setPageNo(PagerHelper.DEFAULT_PAGE_NO);
		page.setPageSize(PagerHelper.DEFAULT_PAGE_SIZE);
		return page;
	}

	private final static String count[] = {"count","totalCount", "total"};
	private final static String pageNo[] = {"pageNo", "page","pageNow"};
	private final static String pageSize[] = {"pageSize","rows", "pageNow"};
	private final static String order[] = {"order"};
	private final static String orderBy[] = {"sort","orderBy"};

	public static <T> Pager<T> createPage(HttpServletRequest request){
		Pager<T> page = new Pager<T>();
		page.setPageNo(PagerHelper.DEFAULT_PAGE_NO);
		page.setPageSize(PagerHelper.DEFAULT_PAGE_SIZE);
		for (int i =0;i< count.length;i++) {
			String countStr = request.getParameter(count[i]);
			if (countStr != null && NumberUtils.isNumber(countStr)) {
				page.setTotalCount(Integer.valueOf(countStr));
				break;
			}
		}

		for (int i =0;i< pageNo.length;i++) {
			String pageNoStr = request.getParameter(pageNo[i]);
			if (pageNoStr != null && NumberUtils.isNumber(pageNoStr)) {
				page.setPageNo(Integer.valueOf(pageNoStr));
				break;
			}
		}
		for (int i =0;i< pageSize.length;i++) {
			String pageSizeStr = request.getParameter(pageSize[i]);
			if (pageSizeStr != null && NumberUtils.isNumber(pageSizeStr)) {
				page.setPageSize(Integer.valueOf(pageSizeStr));
				break;
			}
		}
		for (int i =0;i< order.length;i++) {
			String orderStr = request.getParameter(order[i]);
			if (orderStr != null ) {
				page.setOrder(orderStr);
				break;
			}
		}
		for (int i =0;i< orderBy.length;i++) {
			String orderByStr = request.getParameter(orderBy[i]);
			if (orderByStr != null ) {
				page.setOrderBy(orderByStr);
				break;
			}
		}
		return page;
	}
}
