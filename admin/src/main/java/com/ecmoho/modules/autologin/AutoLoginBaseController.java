package com.ecmoho.modules.autologin;

import com.ecmoho.base.controller.SpiderBaseController;
import com.ecmoho.base.cookie.SpiderCookie;
import com.ecmoho.cache.RedisCacheProvider;
import com.ecmoho.service.autologin.AutoLoginService;
import com.ecmoho.service.autologin.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by 许巧生 on 2016/9/22.
 */
@Controller
public class AutoLoginBaseController extends SpiderBaseController {

    @Autowired
    private AutoLoginService autoLoginService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private RedisCacheProvider redisCacheProvider;
    /**
     * 自动登录
     *
     * @param SBCodes 店铺code_业务code，多个登录之间以逗号隔开
     * @param type    1、店铺code_业务code；2、店铺code;3、业务code
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/autoLogin.json")
    public Map<String, String> login(
            @RequestParam(value = "SBCodes", required = true) String SBCodes,
            @RequestParam(value = "type", required = true) Integer type
    ) {
        Map<String, String> map = autoLoginService.login(SBCodes, type);
        SpiderCookie.CookieHolder.init(cookieService, redisCacheProvider);
        return map;
    }
}
