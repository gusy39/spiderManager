package com.ecmoho.autologin.base.controller;

import com.ecmoho.common.web.BaseController;
import com.ecmoho.token.RedisSessionCache;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by: ningmeiduo
 * Modify by: user
 * Date: 2016/1/18.
 * email:382504345@qq.com
 * mobile:1861989299
 */
public abstract class SpiderBaseController extends BaseController {

    @Autowired
    public RedisSessionCache sessionCache;



}
