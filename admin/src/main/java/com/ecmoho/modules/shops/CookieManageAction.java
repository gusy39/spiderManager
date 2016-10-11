package com.ecmoho.modules.shops;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.ecmoho.cache.RedisCacheProvider;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.service.autologin.CookieService;


@Component("CookieManagePR")
public class CookieManageAction {

	@Autowired
	private CookieService cookieService;
	private RedisCacheProvider redisCacheProvider;
		
	@Value("${seleniumRemoteUrl}")
	private String seleniumRemoteUrl;
	@DataProvider
	public List<CookiesDTO> selectAllCookies(){
		return cookieService.selectAllCookies();
	};
	
	@Transactional
    @DataResolver
    public void updateCookie(List<Map<String,Object>> paramterList){
    	System.out.println(paramterList);
    	if(paramterList!=null&&paramterList.size()>0){
    		for(Map<String,Object> paramter:paramterList){
    			String businessCode=paramter.get("businessCode")==null?"":paramter.get("businessCode").toString();
    			String shopCode=paramter.get("shopCode")==null?"":paramter.get("shopCode").toString();
    			String cookie=paramter.get("cookie")==null?"":paramter.get("cookie").toString();
    			cookieService.updateCookie(businessCode, shopCode, cookie);
    		}
    	}
    };
    @DataResolver
    public void updateCookieSelenium(Map<String,Object> paramter){
    	
 		
		String businessCode=paramter.get("businessCode")==null?"":paramter.get("businessCode").toString();
		String shopCode=paramter.get("shopCode")==null?"":paramter.get("shopCode").toString();
		String targetUrl=seleniumRemoteUrl+"?SBCodes="+shopCode+"_"+businessCode+"&type=1";
		String result=UrlUtil.getUrlString("seleniumRemoteUrl", "get");
		System.out.println(targetUrl);
		
		/**
		 * if(success){
		 *     init到redis里
		 *     SpiderCookie.CookieHolder.init(cookieService, redisCacheProvider);
		 * }else{
		 *     返回错误信息
		 * }
		 * 
		 * 
		 * 
		 */
		//return result;
  
    };
}
