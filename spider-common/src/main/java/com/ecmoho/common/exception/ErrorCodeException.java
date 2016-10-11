package com.ecmoho.common.exception;

import com.ecmoho.common.dto.BaseEnum;

/**
 * Created by Administrator on 2015/6/25.
 * 通过错误代码来标示异常类型
 * 通过这种方式message
 */
public class ErrorCodeException extends AppException {
    private BaseEnum baseEnum;

    public ErrorCodeException(BaseEnum baseEnum,String s) {
        super(s);
        this.baseEnum = baseEnum;
        if (s == null && baseEnum != null) {
            s = baseEnum.getDescription();
        }
    }
    public ErrorCodeException(BaseEnum baseEnum) {
        super(baseEnum.getDescription());
        this.baseEnum = baseEnum;
    }
    public ErrorCodeException(BaseEnum baseEnum,String s, Throwable throwable) {
        super(s, throwable);
        this.baseEnum = baseEnum;
        if (s == null && baseEnum != null) {
            s = baseEnum.getDescription();
        }
    }

    public ErrorCodeException(BaseEnum baseEnum,Throwable throwable) {
        super(throwable);
        this.baseEnum = baseEnum;
    }

    protected ErrorCodeException(BaseEnum baseEnum,String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
        this.baseEnum = baseEnum;
        if (s == null && baseEnum != null) {
            s = baseEnum.getDescription();
        }
    }

    public ErrorCodeException(BaseEnum baseEnum,String message, Throwable cause, Object... param) {
        super(message, cause, param);
        this.baseEnum = baseEnum;
        if (message == null && baseEnum != null) {
            message = baseEnum.getDescription();
        }
    }

    public ErrorCodeException(BaseEnum baseEnum,String message, Object... param) {
        super(message, param);
        this.baseEnum = baseEnum;
        if (message == null && baseEnum != null) {
            message = baseEnum.getDescription();
        }
    }

    public int getErrorCode() {
        if (baseEnum != null) {
            return baseEnum.getCode();
        }
        return 0;
    }
    public String getErrorDesc(){
        if (baseEnum != null) {
            return baseEnum.getDescription();
        }
        return "";
    }

    public String getFormatErrorDesc(){
        if (baseEnum != null) {
            return baseEnum.getCode()+":"+baseEnum.getDescription();
        }
        return "";
    }

}
