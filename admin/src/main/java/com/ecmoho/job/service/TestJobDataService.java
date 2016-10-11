package com.ecmoho.job.service;

import java.util.List;

import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.job.model.JobDefinition;
import com.bstek.bdf2.job.service.IJobDataService;

public class TestJobDataService implements IJobDataService {

	/**
	 * 默认分类Id（分公司Id）
	 */
	private String defaultCategoryId;

	/**
	 * 过滤Job（暂时不做过滤，返回所有job）
	 */
	public List<JobDefinition> filterJobs(List<JobDefinition> jobs) {
		return jobs;
	}

	/**
	 * 获取公司Id
	 */
	public String getCompanyId() {
		IUser user = ContextHolder.getLoginUser();
		return user == null ? this.defaultCategoryId : user.getCompanyId();
	}

	public void setDefaultCategoryId(String defaultCategoryId) {
		this.defaultCategoryId = defaultCategoryId;
	}
}