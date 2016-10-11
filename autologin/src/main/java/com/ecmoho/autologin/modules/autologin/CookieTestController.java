package com.ecmoho.autologin.modules.autologin;

import com.ecmoho.autologin.base.cookie.SpiderCookie;
import com.ecmoho.cache.RedisCacheProvider;
import com.ecmoho.common.util.HttpClientUtils;
import com.ecmoho.common.util.Loggers;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.service.autologin.CookieService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by 许巧生 on 2016/10/8.
 */
@Controller
public class CookieTestController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private RedisCacheProvider redisCacheProvider;

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/cookieTest.json")
    public String cookieTest() {

        //初始化该商铺的cookie
        SpiderCookie.CookieHolder.init(cookieService, redisCacheProvider);
        List<CookiesDTO> list = cookieService.selectAllCookies();

        for (final CookiesDTO dto : list) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("cookie", dto.getCookie());
            String url = "";
            if (dto.getCookieUrl().indexOf("####") == -1) {
                url = dto.getCookieUrl();
            } else {
                url = dto.getCookieUrl().split("####")[0];
            }
            HttpResponse result = HttpClientUtils.sendGetRequest(url, map);

            if (result.getStatusLine().getStatusCode() == 200) {
                dto.setFailTime(null);
                dto.setLastTime(new Date());
                dto.setStatus(1);
                cookieService.updateCookieStatus(dto);//更新cookie状态为成功
                Loggers.testTaskLogger.info("cookie of the shop is ok by bean{}", dto.toString());
            } else {
                dto.setFailTime(new Date());
                dto.setLastTime(new Date());
                dto.setStatus(0);
                cookieService.updateCookieStatus(dto);//更新cookie状态为失败
                Loggers.testTaskLogger.error("cookie of the shop is fail by bean{}", dto.toString());
            }
            try {
                Thread.currentThread().sleep(1000 + new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Loggers.testTaskLogger.info("============================================");
        return "";
    }
}
