package com.ecmoho.service.autologin.impl;

import com.ecmoho.common.util.Loggers;
import com.ecmoho.mapper.CookiesMapper;
import com.ecmoho.mapper.ShopsMapper;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.models.ShopsDTO;
import com.ecmoho.selenium.SeleniumBean;
import com.ecmoho.selenium.SeleniumSpider;
import com.ecmoho.selenium.WLBSeleniumSpider;
import com.ecmoho.service.autologin.AutoLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 许巧生 on 2016/9/22.
 */
@Service
public class AutoLoginServiceImpl implements AutoLoginService {

    @Autowired
    private CookiesMapper cookiesMapper;

    /**
     * 登录
     *
     * @param SBCodes
     * @param type
     * @return
     */
    public Map<String, String> login(String SBCodes, Integer type) {
        String SBCodesSql = "";
        for (String SBCode : SBCodes.split(",")) {
            SBCodesSql += "'" + SBCode + "',";
        }

        Map<String, String> map = new HashMap<>();
        map.put("SBCodesSql", SBCodesSql.substring(0, SBCodesSql.length() - 1));
        map.put("type", type.toString());
        List<CookiesDTO> account = cookiesMapper.selectCookiesBySBCodes(map);

        //循环登录
        Map<String, String> returnMap = new HashMap<>();
        for (CookiesDTO dto : account) {
            SeleniumBean bean = new SeleniumBean(dto.getLoginUrl(), dto.getCookieUrl(), dto.getUserName(),
                    dto.getPwd(), dto.getShopCode(), dto.getBusinessCode(), null);

            SeleniumSpider seleniumSpider = null;
            try {
                seleniumSpider = createSeleniumSpider(dto.getSeleniumSpiderBean());
            } catch (Exception e) {
                Loggers.seleniumSpiderLogger.error("SeleniumSpider instantiation failed, {},{}",
                        bean.toString(), e.getMessage());
                returnMap.put(dto.getShopCode() + "_" + dto.getBusinessCode(), "0");
                continue;
            }

            try {
                String refferCookie = seleniumSpider.getCookie(bean);
                if(StringUtils.isEmpty(refferCookie)){
                    returnMap.put(dto.getShopCode() + "_" + dto.getBusinessCode(), "1");
                    continue;
                }
                dto.setCookie(refferCookie);
                dto.setStatus(1);
                cookiesMapper.updateBySBCode(dto);
                returnMap.put(dto.getShopCode() + "_" + dto.getBusinessCode(), "1");
            } catch (Exception e) {
                Loggers.seleniumSpiderLogger.error("Failed to get cookie, {},{}",
                        bean.toString(), e.getMessage());
                returnMap.put(dto.getShopCode() + "_" + dto.getBusinessCode(), "0");
            }
        }
        return returnMap;
    }

    /**
     * 创建 各业务对应的bean
     *
     * @param seleniumSpiderBean
     * @return
     * @throws Exception
     */
    public SeleniumSpider createSeleniumSpider(String seleniumSpiderBean) throws Exception {
        Class classType = Class.forName("com.ecmoho.selenium." + seleniumSpiderBean);
        return (SeleniumSpider) classType.newInstance();
    }
}
