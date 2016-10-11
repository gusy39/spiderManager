package com.ecmoho.common.dto;

import com.ecmoho.common.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Collection;
import java.util.Date;

@XStreamAlias("result")
public class AjaxResult {

    @XStreamAlias("status")
    @JsonProperty("status")
    private Integer statusCode;

    @XStreamAlias("msg")
    @JsonProperty("msg")
    private Object messages;

    @XStreamAlias("type")
    @JsonProperty("type")
    private String type;

    @XStreamAlias("data")
    @JsonProperty("data")
    private Object content;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    public <T> T getContent() {
        return (T) content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getType() {
        return typeof(content);
    }

    public void setType(String type) {
        this.type = type;
    }

    public AjaxResult() {
    }

    public AjaxResult(Integer statusCode, Object messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public AjaxResult(Object content, Integer statusCode) {
        this.content = content;
        this.statusCode = statusCode;
    }

    public AjaxResult(Integer statusCode, Object messages, Object content) {
        this.statusCode = statusCode;
        this.messages = messages;
        this.content = content;
    }


    private static String typeof(Object param) {
        if (param==null ||param instanceof CharSequence) {
            return "string";
        } else if (param instanceof Integer) {
            return "string";
        } else if (param instanceof Double) {
            return "string";
        } else if (param instanceof Float) {
            return "string";
        } else if (param instanceof Number) {
            return "string";
        } else if (param instanceof Long) {
            return "string";
        } else if (param instanceof Boolean) {
            return "string";
        } else if (param instanceof Date) {
            return "string";
        } else if (param.getClass().isArray() || param instanceof Collection) {
            return "array";
        } else {
            return "object";
        }

    }

    public static void main(String[] args) {
        Object o[] = {11, 11};
        if (o instanceof String[]) {
            System.out.println("string");
        } else {
            System.out.println("not");
        }
        System.out.println(typeof(new Pager<ObjectUtils>()));

    }

}
