package com.ecmoho.spring.interceptor;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2015/7/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPassport {
    /** 是否需要校验 */
    boolean isValidate() default true;

    /** htm,json,xml */
    ResTypeEnum resType() default ResTypeEnum.JSON;

    /** 店铺还未认证  */
    boolean isAddShop() default true;

    /** 店铺认证了还未开通服务 */
    boolean isOpenAuth() default true;

    /** 版本权限 */
    String versionAuth() default "";
}

