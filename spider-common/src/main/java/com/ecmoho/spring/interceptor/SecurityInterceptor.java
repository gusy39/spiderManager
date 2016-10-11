package com.ecmoho.spring.interceptor;

import com.ecmoho.common.constant.Constants;
import com.ecmoho.common.util.RequestUtils;
import com.ecmoho.token.RedisSessionCache;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/6/25.
 */
public class SecurityInterceptor implements HandlerInterceptor {
    private final Logger logger = Logger.getLogger(getClass());
    public static final String CURRENT_SESSION = "_current_session";
    public static final String CURRENT_SESSION_ID = "_current_session_id";

    @Autowired
    private RedisSessionCache sessionCache;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        req.setAttribute("reqBegin", System.currentTimeMillis());

        String token = RequestUtils.getRequestedToken(req);
        if (StringUtils.isNotBlank(token)) {
            req.setAttribute(CURRENT_SESSION_ID, token);
        }

         return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object arg2, ModelAndView arg3) throws Exception {
        String token = (String) req.getAttribute(CURRENT_SESSION_ID);
        if (token != null) {
            if (arg3 == null) {
                arg3 = new ModelAndView();
            }
            arg3.addObject(Constants.JSESSION_URL, token);
            res.addCookie(createCookie(req, token));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3) throws Exception {
        Long reqBeginTime = (Long) req.getAttribute("reqBegin");
        logger.info((System.currentTimeMillis() - reqBeginTime) + "-" + req.getRequestURI());
    }

    private static int cookieMaxAge = 3600 * 24 * 7;

    private Cookie createCookie(HttpServletRequest request, String value) {
        Cookie cookie = new Cookie(Constants.JSESSION_COOKIE, value);
        cookie.setMaxAge(cookieMaxAge);
        String ctx = request.getContextPath();
        cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
        return cookie;
    }


}
