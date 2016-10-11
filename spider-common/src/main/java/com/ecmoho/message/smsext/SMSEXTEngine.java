package com.ecmoho.message.smsext;


import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import com.ecmoho.message.sms.SMSTransport;
import org.apache.log4j.Logger;

public class SMSEXTEngine extends MessagingEngine {
    public static final Logger logger = Logger.getLogger(SMSEXTEngine.class);

    public String open = "true";

    private SMSEXTTransport smsextTransport;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public SMSEXTTransport getSmsextTransport() {
        return smsextTransport;
    }

    public void setSmsextTransport(SMSEXTTransport smsextTransport) {
        this.smsextTransport = smsextTransport;
    }

    public String sendMessage(String mobile, String content) throws SMSException {
        Msg message = new Msg();
        message.setTo(mobile);
        message.setContent(content);
        logger.info("系统短信,mobile:" + mobile + " 内容:" + content);
        if ("true".equals(open)) {
            return smsextTransport.send(message, null);
        } else {
            return "";
        }

    }

    @Override
    public String sendMessage(Msg message) throws SMSException {
        if ("true".equals(open)) {
            return smsextTransport.send(message, null);
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
            return smsextTransport.send(message, null);
        } else {
            return "";
        }
    }
}
