package com.ecmoho.common.dto;

/**
 * Created by Administrator on 2015/6/26.
 */

public enum ResultStatus {
    SUCCESS(0,"成功"),
    INVALID(100,"验证失败"),
    LOGIN(200,"未登录"),
    EXCEPTION(300,"系统异常"),
    ERROR(400,"系统错误"),
    REDIRECT(500,"重定向"),
    NOT_ADD_SHOP(600,"未认领店铺"),
    NOT_APPROVE(610,"认证中"),
    NOT_OPEN_AUTH(620,"未开通任何服务"),
    NOT_OPEN_THIS(999,"未开此项服务"),
    LOCATION(700,"未定位"),
    ;

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ResultStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
