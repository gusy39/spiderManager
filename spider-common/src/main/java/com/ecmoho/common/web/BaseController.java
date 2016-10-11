package com.ecmoho.common.web;

import com.ecmoho.common.dto.AjaxResult;
import com.ecmoho.common.dto.Pager;
import com.ecmoho.common.dto.ResultStatus;
import com.ecmoho.common.web.esayui.GridList;
import com.ecmoho.common.web.esayui.GridPage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ningmd
 */
public abstract class BaseController {
    public final Logger logger = Logger.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        datetimeFormat.setLenient(false);

        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
    }

    public static AjaxResult getAjaxSuccess(Object data) {
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "success", data);
    }

    public static AjaxResult getAjaxSuccess() {
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "success", "");
    }

    public static AjaxResult getAjaxGridPage(Pager<?> pages) {
        GridPage gridPage = new GridPage(pages.getTotalCount(), pages.getResult());
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "success", gridPage);
    }

    public static AjaxResult getAjaxGridList(List<?> list) {
        GridList gridList = new GridList();
        gridList.setMsg("success");
        gridList.setObj(list);
        gridList.setSuccess(true);
        return new AjaxResult(ResultStatus.SUCCESS.getCode(), "success", gridList);
    }

    public static AjaxResult getAjaxNotLogin(Object data) {
        return new AjaxResult(ResultStatus.LOGIN.getCode(), "您还未登录", data);
    }

    public static AjaxResult getAjaxNotLocation(){
        return new AjaxResult(ResultStatus.LOCATION.getCode(),"未定位","");
    }
    public static AjaxResult getAjaxInvalid(BindingResult result, Object data) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), result, data);
    }

    public static AjaxResult getAjaxInvalid(BindingResult[] result, Object data) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), result, data);
    }

    public static AjaxResult getAjaxInvalid(Object data, List<ObjectError> result) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), result, data);
    }

    public static AjaxResult getAjaxInvalid(Object data, List<ObjectError>... result) {
        List<ObjectError> newRet = new ArrayList<>();
        if (result != null) {
            for (List<ObjectError> item : result) {
                newRet.addAll(item);
            }
        }
        return new AjaxResult(ResultStatus.INVALID.getCode(), newRet, data);
    }

    /**
     * 单项验证
     * @param message
     * @return
     */
    public static AjaxResult getAjaxInvalid(String message) {
        return new AjaxResult(ResultStatus.INVALID.getCode(), message);
    }
    public static AjaxResult getAjaxException(String message) {
        return new AjaxResult(ResultStatus.EXCEPTION.getCode(), message);
    }

    public static AjaxResult getAjaxError(String message) {
        return new AjaxResult(ResultStatus.ERROR.getCode(), message);
    }

    public static AjaxResult getAjaxRedirect(String redirectURL) {
        return new AjaxResult(ResultStatus.REDIRECT.getCode(), "redirectURL", redirectURL);
    }

}
