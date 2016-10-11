/**
 *
 */
package com.ecmoho.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.ibatis.annotations.CacheNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class AuthUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AuthUtils.class);

    /**
     * 判断是否是需要认证的响应
     *
     * @param response
     * @return
     */
    public static boolean isAuthResponse(HttpResponse response) {
        if (response == null) {
            return false;
        }

        Properties props = new Properties();
        String loginUrls = null;
        try {
            InputStream in = new FileInputStream(System.getProperty("web.root") + "/WEB-INF/dorado-home/configure.properties");
            props.load(in);
            loginUrls = props.getProperty("authUtils.loginUrls");
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        if (StringUtils.isNoneBlank(loginUrls)) {

            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_MOVED_TEMPORARILY:
                case HttpStatus.SC_MOVED_PERMANENTLY:
                case HttpStatus.SC_TEMPORARY_REDIRECT:
                case HttpStatus.SC_SEE_OTHER:
                    Header locationHeader = response.getFirstHeader("location");

                    for (String loginUrl : loginUrls.split("\\$\\$\\$\\$")) {
                        if (StringUtils.contains(locationHeader.getValue(), loginUrl)) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }
}
