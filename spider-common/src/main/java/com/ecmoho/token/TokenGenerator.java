package com.ecmoho.token;

import com.ecmoho.common.util.MD5Util;

import java.util.Date;
import java.util.UUID;

/**
 * Created by: ningmeiduo
 * Modify by: user
 * Date: 2016/1/16.
 * email:382504345@qq.com
 * mobile:1861989299
 */
public class TokenGenerator {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * salt可能是用户Id+密码
     * @param salt
     * @return
     */
    public static String getMD5(String salt){
        return  MD5Util.MD5(salt+(new Date().getTime()));
    }
}
