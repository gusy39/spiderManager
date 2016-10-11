package com.ecmoho.jpush.client;

import com.ecmoho.jpush.api.JPushClient;
import com.ecmoho.jpush.api.common.resp.APIConnectionException;
import com.ecmoho.jpush.api.common.resp.APIRequestException;
import com.ecmoho.jpush.api.common.resp.DefaultResult;
import com.ecmoho.jpush.api.device.OnlineStatus;
import com.ecmoho.jpush.api.device.TagAliasResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DeviceExample extends JPushCommon {
	protected static final Logger LOG = LoggerFactory.getLogger(DeviceExample.class);

	private static JPushClient jpushClient = new JPushClient(masterSecret, appKey);

	public static void main(String[] args) {
//		testGetDeviceTagAlias();
//		testGetUserOnlineStatus();
	}
	
	public static void testGetDeviceTagAlias() {
		try {
			TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
			
			LOG.info(result.alias);
			LOG.info(result.tags.toString());
			
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
		}
	}

	public static void testGetUserOnlineStatus() {
		try {
			Map<String, OnlineStatus> result =  jpushClient.getUserOnlineStatus(REGISTRATION_ID1, REGISTRATION_ID2);

			LOG.info(result.get(REGISTRATION_ID1).toString());
			LOG.info(result.get(REGISTRATION_ID2).toString());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
	}

	public static void testBindMobile() {
		try {
			DefaultResult result =  jpushClient.bindMobile(REGISTRATION_ID1, "13000000000");
			LOG.info("Got result " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
	}
	
}


