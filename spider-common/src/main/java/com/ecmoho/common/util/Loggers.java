/**
 * 
 */
package com.ecmoho.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuqs
 * @since 5:52:32 PM Jul 20, 2014
 */
public abstract class Loggers {

	public static final Logger seleniumSpiderLogger = LoggerFactory
			.getLogger("seleniumSpider");

	public static final Logger testTaskLogger = LoggerFactory
			.getLogger("testTask");

	public static final Logger redisLogger = LoggerFactory
			.getLogger("redis");

	/** 用于统计任务执行结果，加日志 */
	public enum StatsResult {

		SUCESS, /** 任务成功 */
		FAIL, /** 任务失败 */
		PARTIAL, /** 任务部分成功 */
		ASYNC /** 任务已提交异步处理 */
	}
}
