package com.ecmoho.common.constant;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/7/13.
 */
public class Constants {
    /**
     * 零 BigDecimal类型常量
     */
    public final static BigDecimal ZERO = new BigDecimal("0");

    public final static int BYTE_LENG_240 = 240;

    /**
     * 路径分隔符
     */
    public static final String SPT = "/";
    /**
     * 索引页
     */
    public static final String INDEX = "index";
    /**
     * 默认模板
     */
    public static final String DEFAULT = "default";

    /**
     * UTF-8编码
     */
    public static final String UTF8 = "UTF-8";
    /**
     * 提示信息
     */
    public static final String MESSAGE = "message";
    /**
     * cookie中的JSESSIONID名称 或者token
     */
    public static final String JSESSION_COOKIE = "t";
    /**
     * url中的jsessionid名称  或者token
     */
    public static final String JSESSION_URL = "t";

    public static final String JSESSION_HEAD = "t";
    /**
     * HTTP POST请求
     */
    public static final String POST = "POST";
    /**
     * HTTP GET请求
     */
    public static final String GET = "GET";


}

