package com.ecmoho.job.modules;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ecmoho.common.util.HttpClientUtils;

/**
 * 单次对账接口
 * @author 许巧生
 *
 */
public class ReconciliationJob implements Job  {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(HttpClientUtils.sendPostRequestByJava(
				"http://192.168.2.150/erp/Public/index.php/checking/Dochecking", "") + "单次");
		
	}

}
