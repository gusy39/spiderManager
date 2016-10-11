package com.ecmoho.message.smsext;

import com.ecmoho.common.util.StringUtil;
import com.ecmoho.message.MessageTransport;
import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class SMSEXTTransport implements MessageTransport {
	private final static Logger logger = Logger.getLogger(SMSEXTTransport.class.getName());

    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmssS");

    private final static NumberFormat numberFormat = new DecimalFormat("00");

    private static int seq = 0;

    private static final int MAX = 9999;
    /**发送短信服务器地址*/
    private final static String host = "https://smsapi.ums86.com:9600/sms/Api/Send.do";
    /**一信通web登录帐号*/
    private final static String SpCode = "201451";//10657109030998
    /**一信通web登录名*/
    private final static String LoginName = "bd_wx";//boES71Ec
    /**一信通web登录密码*/
    private final static String Password = "Bdoulife#129exin";//2171097186  //4801KcqW


    @Override
    public void retry(Msg message) {
        //TODO
    }

    @Override
    public void retryAsync(Msg message) {
        // TODO Auto-generated method stub

    }

    @Override
    public String send(Msg message, MessagingEngine.QueuePolicy queuePolicy)
            throws SMSException {
        if(message == null){
            throw new SMSException("Message content is null!");
        }
        return this.sendOnceMsg(message.getTo(),message.getContent());
    }

    @Override
    public void sendAsync(Msg message, MessagingEngine.QueuePolicy queuePolicy) {

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String sendOnceMsg(String mobile,String content){
        String info = null;
        try{
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod("https://smsapi.ums86.com:9600/sms/Api/Send.do");//
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
            post.addParameter("SpCode", SpCode);
            post.addParameter("LoginName", LoginName);
            post.addParameter("Password", Password);
            post.addParameter("MessageContent", content);
            post.addParameter("UserNumber", mobile);
            post.addParameter("SerialNumber", SerialNumber());
            post.addParameter("ScheduleTime", String.valueOf(new Date().getTime()));
            post.addParameter("ExtendAccessNum", "");
            post.addParameter("f", "1");

            return "";
//            httpclient.executeMethod(post);
//            info = new String(post.getResponseBody(),"gbk");
//            return info;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String SerialNumber(){
        Calendar rightNow = Calendar.getInstance();

        StringBuffer sb = new StringBuffer();

        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

        numberFormat.format(seq, sb, HELPER_POSITION);

        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }
        return sb.toString();
    }

}
