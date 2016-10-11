package com.ecmoho.common.web;

import com.ecmoho.common.exception.ErrorCodeException;
import com.ecmoho.common.exception.NotLoginException;
import com.ecmoho.common.exception.ServiceException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/26.
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String viewName = determineViewName(ex, request);

        if (request.getRequestURL().indexOf(".htm")>-1) { // 如果不是异步请求
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            return getModelAndView(viewName, ex, request);
        } else {// JSON,xml格式返回
            Map<String, Object> map = new HashMap<String, Object>();
            logger.info(ExceptionUtils.getFullStackTrace(ex));
            if (ex instanceof ErrorCodeException) {
                map.put("content",BaseController.getAjaxException(((ErrorCodeException) ex).getFormatErrorDesc()));
            }else if (ex instanceof ServiceException) {
                map.put("content",BaseController.getAjaxException(ex.getMessage()));
            }else if (ex instanceof NotLoginException) {
                map.put("content",BaseController.getAjaxNotLogin(ex.getMessage()));
            }else{
                map.put("content",BaseController.getAjaxException("系统异常"));
            }
            return new ModelAndView(viewName, map);
        }
    }
}
