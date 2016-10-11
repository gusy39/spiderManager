package com.ecmoho.message.sms;


import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import org.apache.log4j.Logger;

public class SMSEngine extends MessagingEngine {
    public static final Logger logger = Logger.getLogger(SMSEngine.class);

    public String open = "false";

    private SMSTransport smsTransport;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public SMSTransport getSmsTransport() {
        return smsTransport;
    }

    public void setSmsTransport(SMSTransport smsTransport) {
        this.smsTransport = smsTransport;
    }

    public String sendMessage(String mobile, String content) throws SMSException {
        Msg message = new Msg();
        message.setTo(mobile);
        message.setContent(content);
        logger.info("系统短信,mobile:" + mobile + " 内容:" + content);
        if ("true".equals(open)) {
            return smsTransport.send(message, null);
        } else {
            return "";
        }

    }

    @Override
    public String sendMessage(Msg message) throws SMSException {
        if ("true".equals(open)) {
            return smsTransport.send(message, null);
        } else {
            return "";
        }
    }

    @Override
    public String sendMessage(String mobile, String subject, String content) throws SMSException {
        Msg message = new Msg();
        message.setTo(mobile);
        message.setContent(content);
        logger.info("系统短信,mobile:" + mobile + " 内容:" + content);
        if ("true".equals(open)) {
            return smsTransport.send(message, null);
        } else {
            return "";
        }
    }
}
