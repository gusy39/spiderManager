package com.ecmoho.autologin.base.test;

import com.ecmoho.autologin.base.cookie.SpiderCookie;
import com.ecmoho.cache.RedisCacheProvider;
import com.ecmoho.common.util.HttpClientUtils;
import com.ecmoho.common.util.Loggers;
import com.ecmoho.models.CookiesDTO;
import com.ecmoho.service.autologin.CookieService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 许巧生 on 2016/7/14.
 */
public class TestTask {

    @Autowired
    private CookieService cookieService;

    private RedisCacheProvider redisCacheProvider;

    private int IntervalTime;

    private ScheduledExecutorService executor;

    public void init() {
        executor = Executors.newScheduledThreadPool(1);
        this.start();
    }

    public void start() {

        executor.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {

                //初始化该商铺的cookie
                SpiderCookie.CookieHolder.init(cookieService, redisCacheProvider);
                List<CookiesDTO> list = cookieService.selectAllCookies();

                for (final CookiesDTO dto : list) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("cookie", dto.getCookie());
                    String url = "";
                    if (dto.getCookieUrl().indexOf("####") == -1) {
                        url = dto.getCookieUrl();
                    } else {
                        url = dto.getCookieUrl().split("####")[0];
                    }
                    HttpResponse result = HttpClientUtils.sendGetRequest(url, map);

                    if (result.getStatusLine().getStatusCode() == 200) {
                        dto.setFailTime(null);
                        dto.setLastTime(new Date());
                        dto.setStatus(1);
                        cookieService.updateCookieStatus(dto);
                        Loggers.testTaskLogger.info("cookie of the shop is ok by bean{}", dto.toString());
                    } else {
                        dto.setFailTime(new Date());
                        dto.setLastTime(new Date());
                        dto.setStatus(0);
                        cookieService.updateCookieStatus(dto);
                        Loggers.testTaskLogger.error("cookie of the shop is fail by bean{}", dto.toString());
                    }
                    try {
                        Thread.currentThread().sleep(1000 + new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Loggers.testTaskLogger.info("============================================");
            }
        }, 0, IntervalTime, TimeUnit.SECONDS);
    }

    public void destroy() {
        if (Loggers.testTaskLogger.isInfoEnabled()) {
            Loggers.testTaskLogger.info("start to destroy " + this.getClass().getSimpleName());
        }

        this.executor.shutdown();

        if (Loggers.testTaskLogger.isInfoEnabled()) {
            Loggers.testTaskLogger.info("success to destroy " + this.getClass().getSimpleName());
        }
    }

    public int getIntervalTime() {
        return IntervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        IntervalTime = intervalTime;
    }

    public CookieService getCookieService() {
        return cookieService;
    }

    public void setCookieService(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    public RedisCacheProvider getRedisCacheProvider() {
        return redisCacheProvider;
    }

    public void setRedisCacheProvider(RedisCacheProvider redisCacheProvider) {
        this.redisCacheProvider = redisCacheProvider;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ScheduledExecutorService executor) {
        this.executor = executor;
    }
}
