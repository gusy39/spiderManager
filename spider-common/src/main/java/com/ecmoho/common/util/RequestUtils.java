package com.ecmoho.common.util;

import com.ecmoho.common.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RequestUtils {
    public final static Logger logger = Logger.getLogger(RequestUtils.class);

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        if (request == null) {
            return map;
        }
        Map<String, String[]> map2 = request.getParameterMap();
        for (Map.Entry<String, String[]> item : map2.entrySet()) {
            map.put(item.getKey(), StringUtils.join(item.getValue()));
        }
        return map;
    }

    /**
     * Returns the IP address from the request
     *
     * @return IP address
     */
    public static String getIPAddress(HttpServletRequest request) {
        String remoteAddr = null;
        // try from true client IP (akamai header)
        remoteAddr = request.getHeader("XOLCIPV");
        if (remoteAddr != null && remoteAddr.length() > 0) return cleanIPAddress(remoteAddr);

        // try getting from clientIP (webcache header)
        remoteAddr = request.getHeader("CLIENTIP");

        if (remoteAddr != null && remoteAddr.length() > 0) return cleanIPAddress(remoteAddr);

        // try getting from X-Real-IP (nginx header)
        remoteAddr = request.getHeader("X-Forwarded-For");

        if (remoteAddr != null && remoteAddr.length() > 0) return cleanIPAddress(remoteAddr);

        remoteAddr = request.getHeader("X-Real-IP");

        if (remoteAddr != null && remoteAddr.length() > 0) return cleanIPAddress(remoteAddr);

        // ok.. try from request header
        remoteAddr = request.getHeader("REMOTE_ADDR");

        if (remoteAddr != null && remoteAddr.length() > 0) return cleanIPAddress(remoteAddr);

        // finally try the java call which is pretty much equvalent to the above
        // call.
        return cleanIPAddress(request.getRemoteAddr());
    }

    public static String cleanIPAddress(String ipAddress) {
        if (ipAddress == null) return null;
        String ip = ipAddress.replace("c ", "");
        ip = ipAddress.replace("a ", "");
        return ip;
    }

    /**
     * 获取QueryString的参数，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的，
     * 那么将通过HttpServletRequest#getParameter获取。
     *
     * @param request web请求
     * @param name    参数名称
     * @return
     */
    public static String getQueryParam(HttpServletRequest request, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (request.getMethod().equalsIgnoreCase(Constants.POST)) {
            return request.getParameter(name);
        }
        String s = request.getQueryString();
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            s = URLDecoder.decode(s, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.error("encoding " + Constants.UTF8 + " not support?", e);
        }
        String[] values = parseQueryString(s).get(name);
        if (values != null && values.length > 0) {
            return values[values.length - 1];
        } else {
            return null;
        }
    }

    public static Map<String, Object> getQueryParams(HttpServletRequest request) {
        Map<String, String[]> map;
        if (request.getMethod().equalsIgnoreCase(Constants.POST)) {
            map = request.getParameterMap();
        } else {
            String s = request.getQueryString();
            if (StringUtils.isBlank(s)) {
                return new HashMap<String, Object>();
            }
            try {
                s = URLDecoder.decode(s, Constants.UTF8);
            } catch (UnsupportedEncodingException e) {
                logger.error("encoding " + Constants.UTF8 + " not support?", e);
            }
            map = parseQueryString(s);
        }

        Map<String, Object> params = new HashMap<String, Object>(map.size());
        int len;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            len = entry.getValue().length;
            if (len == 1) {
                params.put(entry.getKey(), entry.getValue()[0]);
            } else if (len > 1) {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        return params;
    }

    /**
     * Parses a query string passed from the client to the server and builds a
     * <code>HashTable</code> object with key-value pairs. The query string
     * should be in the form of a string packaged by the GET or POST method,
     * that is, it should have key-value pairs in the form <i>key=value</i>,
     * with each pair separated from the next by a &amp; character.
     * <p/>
     * <p/>
     * A key can appear more than once in the query string with different
     * values. However, the key appears only once in the hashtable, with its
     * value being an array of strings containing the multiple values sent by
     * the query string.
     * <p/>
     * <p/>
     * The keys and values in the hashtable are stored in their decoded form, so
     * any + characters are converted to spaces, and characters sent in
     * hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
     *
     * @param s a string containing the query to be parsed
     * @return a <code>HashTable</code> object built from the parsed key-value
     * pairs
     * @throws IllegalArgumentException if the query string is invalid
     */
    public static Map<String, String[]> parseQueryString(String s) {
        String valArray[] = null;
        if (s == null) {
            throw new IllegalArgumentException();
        }
        Map<String, String[]> ht = new HashMap<String, String[]>();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = (String) st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = pair.substring(0, pos);
            String val = pair.substring(pos + 1, pair.length());
            if (ht.containsKey(key)) {
                String oldVals[] = (String[]) ht.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = val;
            } else {
                valArray = new String[1];
                valArray[0] = val;
            }
            ht.put(key, valArray);
        }
        return ht;
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix) {
        return getRequestMap(request, prefix, false);
    }

    public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix) {
        return getRequestMap(request, prefix, true);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        String name, key, value;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (name.startsWith(prefix)) {
                key = nameWithPrefix ? name : name.substring(prefix.length());
                value = StringUtils.join(request.getParameterValues(name), ',');
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 获取访问者IP
     * <p/>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p/>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获得当的访问路径
     * <p/>
     * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
     *
     * @param request
     * @return
     */
    public static String getLocation(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        StringBuffer buff = request.getRequestURL();
        String uri = request.getRequestURI();
        String origUri = helper.getOriginatingRequestUri(request);
        buff.replace(buff.length() - uri.length(), buff.length(), origUri);
        String queryString = helper.getOriginatingQueryString(request);
        if (queryString != null) {
            buff.append("?").append(queryString);
        }
        return buff.toString();
    }

    /**
     * 获得请求的session id，但是HttpServletRequest#getRequestedSessionId()方法有一些问题。
     * 当存在部署路径的时候，会获取到根路径下的jsessionid。
     *
     * @param request
     * @return
     * @see HttpServletRequest#getRequestedSessionId()
     */
    public static String getRequestedSessionId(HttpServletRequest request) {
        String sid = request.getRequestedSessionId();
        String ctx = request.getContextPath();
        // 如果session id是从url中获取，或者部署路径为空，那么是在正确的。
        if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(ctx)) {
            return sid;
        } else {
            // 手动从cookie获取
            Cookie cookie = CookieUtils.getCookie(request, Constants.JSESSION_COOKIE);
            if (cookie != null) {
                return cookie.getValue();
            } else {
                return request.getSession().getId();
            }
        }
    }

    /**
     * 自定义回话token机制 系统httpSession无效
     * @param request
     * @return
     */
    public static String getRequestedToken(HttpServletRequest request) {
        if(!StringUtil.isEmpty(request.getParameter(Constants.JSESSION_COOKIE))) {
            return request.getParameter(Constants.JSESSION_COOKIE);
        }
        if(!StringUtil.isEmpty(request.getHeader(Constants.JSESSION_HEAD))){
            return request.getHeader(Constants.JSESSION_HEAD);
        }
        Cookie cookie = CookieUtils.getCookie(request, Constants.JSESSION_COOKIE);
        if (cookie != null && !StringUtil.isEmpty(cookie.getValue())) {
            return cookie.getValue();
        }
        return null;
    }


    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        int port = request.getServerPort();
        String portStr = "";
        if (port != 80) {
            portStr = ":" + port;
        }
        String basePath = request.getScheme() + "://" + request.getServerName() + portStr + path;
        return basePath;
    }
}
