package com.ecmoho.common.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/28.
 */
public class HttpClientUtils {
    public static final Logger logger = Logger.getLogger(HttpClientUtils.class);

    private static DefaultHttpClient httpClient = null;

    /**
     * 适合多线程的HttpClient,用httpClient4.2.1实现
     *
     * @return DefaultHttpClient
     */
    public static synchronized DefaultHttpClient getHttpClient() {
        if (httpClient == null) {
            // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
            HttpProtocolParams.setUseExpectContinue(params, true);

            //设置连接超时时间
            int REQUEST_TIMEOUT = 30 * 1000;  //设置请求超时10秒钟
            int SO_TIMEOUT = 30 * 1000;       //设置等待数据超时时间10秒钟
            //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
            //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

            //设置访问协议
            SchemeRegistry schreg = new SchemeRegistry();
            schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

            //多连接的线程安全的管理器
            PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
            pccm.setDefaultMaxPerRoute(20); //每个主机的最大并行链接数
            pccm.setMaxTotal(100);          //客户端总并行链接最大数

            httpClient = new DefaultHttpClient(pccm, params);
        }
        return httpClient;
    }

    /**
     * 适合多线程的HttpClient,用httpClient4.2.1实现（需要鉴权的不redirect）
     *
     * @return DefaultHttpClient
     */
    public static synchronized DefaultHttpClient getHttpClient2() {
        if (httpClient == null) {
            // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
            HttpProtocolParams.setUseExpectContinue(params, true);

            //设置连接超时时间
            int REQUEST_TIMEOUT = 30 * 1000;  //设置请求超时10秒钟
            int SO_TIMEOUT = 30 * 1000;       //设置等待数据超时时间10秒钟
            //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
            //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

            //设置访问协议
            SchemeRegistry schreg = new SchemeRegistry();
            schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

            //多连接的线程安全的管理器
            PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
            pccm.setDefaultMaxPerRoute(20); //每个主机的最大并行链接数
            pccm.setMaxTotal(100);          //客户端总并行链接最大数

            httpClient = new DefaultHttpClient(pccm, params);
            if (httpClient instanceof DefaultHttpClient) {
                ((DefaultHttpClient) httpClient).setRedirectStrategy(new DefaultRedirectStrategy() {
                    @Override
                    public boolean isRedirected(HttpRequest request, HttpResponse response,
                                                HttpContext context) throws ProtocolException {
                        if (AuthUtils.isAuthResponse(response)) {
                            // 需要鉴权，redirect过去也没意义
                            return false;
                        }
                        return super.isRedirected(request, response, context);
                    }
                });
            }
        }
        return httpClient;
    }

    private HttpClientUtils() {
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param reqURL        请求地址(含参数)
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendGetRequest(String reqURL, Map<String, String> header, String decodeCharset) {
        long responseLength = 0;       //响应长度
        String responseContent = null; //响应内容
        HttpClient httpClient = getHttpClient(); //创建默认的httpClient实例
        HttpGet httpGet = new HttpGet(reqURL);           //创建org.apache.http.client.methods.HttpGet
        if (header != null) {
            for (Map.Entry<String, String> item : header.entrySet()) {
                httpGet.addHeader(item.getKey(), item.getValue());
            }
        }
        long beginTime = System.currentTimeMillis();
        try {
            HttpResponse response = httpClient.execute(httpGet); //执行GET请求
            HttpEntity entity = response.getEntity();            //获取响应实体
            if (null != entity) {
                responseLength = entity.getContentLength();
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity); //Consume response content
            }
            logger.debug("请求地址: " + httpGet.getURI());
            logger.debug("响应状态: " + response.getStatusLine());
            logger.debug("响应长度: " + responseLength);
            logger.debug("响应内容: " + responseContent);
        } catch (ClientProtocolException e) {
            logger.info("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
        } catch (IOException e) {
            logger.info("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
        } finally {
            httpGet.releaseConnection();
            long endTime = System.currentTimeMillis();
            logger.info("请求用时:" + (endTime - beginTime));
        }
        return responseContent;
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param reqURL        请求地址(含参数)
     * @return 远程主机响应正文
     */
    public static HttpResponse sendGetRequest(String reqURL, Map<String, String> header) {
        HttpResponse response = null;
        HttpClient httpClient = getHttpClient2(); //创建默认的httpClient实例
        HttpGet httpGet = new HttpGet(reqURL);           //创建org.apache.http.client.methods.HttpGet
        if (header != null) {
            for (Map.Entry<String, String> item : header.entrySet()) {
                httpGet.addHeader(item.getKey(), item.getValue());
            }
        }
        long beginTime = System.currentTimeMillis();
        try {
            response = httpClient.execute(httpGet); //执行GET请求
            logger.debug("请求地址: " + httpGet.getURI());
            logger.debug("响应状态: " + response.getStatusLine());
        } catch (ClientProtocolException e) {
            logger.info("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
        } catch (IOException e) {
            logger.info("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
        } finally {
            httpGet.releaseConnection();
            long endTime = System.currentTimeMillis();
            logger.info("请求用时:" + (endTime - beginTime));
        }
        return response;
    }

    /**
     * 发送HTTP_POST请求
     *
     * @param reqURL    isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
     * @param sendData
     * @param isEncoder
     * @return
     */
    public static String sendPostRequest(String reqURL, String sendData, Map<String, String> header, boolean isEncoder) {
        return sendPostRequest(reqURL, sendData, header, isEncoder, null, null);
    }


    /**
     * 发送HTTP_POST请求
     *
     * @param reqURL        请求地址
     * @param sendData      请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,传入该参数中
     * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, String sendData, Map<String, String> header, boolean isEncoder, String encodeCharset, String decodeCharset) {
        String responseContent = null;
        DefaultHttpClient httpClient = getHttpClient();

        HttpPost httpPost = new HttpPost(reqURL);
        //httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        if (header != null) {
            for (Map.Entry<String, String> item : header.entrySet()) {
                httpPost.addHeader(item.getKey(), item.getValue());
            }
        }

        try {
            if (isEncoder) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for (String str : sendData.split("&")) {
                    formParams.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1)));
                }
                httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset == null ? "UTF-8" : encodeCharset)));
            } else {
                httpPost.setEntity(new StringEntity(sendData));
            }

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;
    }


    /**
     * 发送HTTP_POST请求
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset) {
        String responseContent = null;
        HttpClient httpClient = getHttpClient();

        HttpPost httpPost = new HttpPost(reqURL);

        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;
    }

    public static String sendPostRequestByFile(String reqURL,String filesName,InputStream files ,String encodeCharset, String decodeCharset) {
        String responseContent = null;
        HttpClient httpClient = getHttpClient();

        HttpPost httpPost = new HttpPost(reqURL);

        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
        try {
            MultipartEntity entityFile = new MultipartEntity();
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));
            if (files != null) {
                ContentBody fileBody = new InputStreamBody(files,filesName);
                entityFile.addPart("upfile",fileBody);
                httpPost.setEntity(entityFile);
            }

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            logger.info("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;
    }

    /**
     * 发送HTTP_POST请求
     * 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
     *
     * @param reqURL 请求地址
     * @param params 发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
     * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
     */
    public static String sendPostRequestByJava(String reqURL, Map<String, String> params) {
        StringBuilder sendData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sendData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sendData.length() > 0) {
            sendData.setLength(sendData.length() - 1); //删除最后一个&符号
        }
        return sendPostRequestByJava(reqURL, sendData.toString());
    }


    /**
     * 发送HTTP_POST请求
     *
     * @param reqURL   请求地址
     * @param sendData 发送到远程主机的正文数据
     * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
     */
    public static String sendPostRequestByJava(String reqURL, String sendData) {
        HttpURLConnection httpURLConnection = null;
        OutputStream out = null; //写
        InputStream in = null;   //读
        int httpStatusCode = 0;  //远程主机响应的HTTP状态码
        try {
            URL sendUrl = new URL(reqURL);
            httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);        //指示应用程序要将数据写入URL连接,其值默认为false
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(30000); //30秒连接超时
            httpURLConnection.setReadTimeout(30000);    //30秒读取超时

            out = httpURLConnection.getOutputStream();
            out.write(sendData.toString().getBytes());

            //清空缓冲区,发送数据
            out.flush();

            //获取HTTP状态码
            httpStatusCode = httpURLConnection.getResponseCode();

            //该方法只能获取到[HTTP/1.0 200 OK]中的[OK]
            //若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾
            //respData = httpURLConnection.getResponseMessage();

//          //处理返回结果
//          BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//          String row = null;
//          String respData = "";
//          if((row=br.readLine()) != null){ //readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止
//              respData = row;              //HTTP协议POST方式的最后一行数据为正文数据
//          }
//          br.close();

            in = httpURLConnection.getInputStream();
            byte[] byteDatas = new byte[in.available()];
            in.read(byteDatas);
            return new String(byteDatas) + "`" + httpStatusCode;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "Failed`" + httpStatusCode;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.info("关闭输出流时发生异常,堆栈信息如下", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.info("关闭输入流时发生异常,堆栈信息如下", e);
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public static void main(String[] args) {
       /* Map<String, String> header = new HashMap<>();
        header.put("os", "android");
        header.put("udid", "5589999");
        header.put("appkey", "appkey48574878EEEEWWWWDDDD");
        header.put("appversion", "1.0");
        header.put("osversion", "5.0.2");
        header.put("ver", "0.1");
        header.put("token", "030F5D2F065F98E8C7373A748F89F0D1");
//        String ajax = HttpClientUtils.sendPostRequest("http://127.0.0.1:2099/sqstcok/app/login.json?loginName=ningmd&loginPwd=123456", "",header,false);
//        System.out.println(ajax);
        String ajax2 = HttpClientUtils.sendPostRequest("http://127.0.0.1:2099/sqstcok/at/mblog.json", "", header, false);
        System.out.println(ajax2);*/

    }
}
