package com.ecmoho.message.smsfy;


import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import org.apache.log4j.Logger;

public class SMSFYEngine extends MessagingEngine {
    public static final Logger logger = Logger.getLogger(SMSFYEngine.class);

    public String open = "false";

    private SMSFYTransport smsfyTransport;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public SMSFYTransport getSmsfyTransport() {
        return smsfyTransport;
    }

    public void setSmsfyTransport(SMSFYTransport smsfyTransport) {
        this.smsfyTransport = smsfyTransport;
    }

    public String sendMessage(String mobile, String content) throws SMSException {
        Msg message = new Msg();
        message.setTo(mobile);
        message.setContent(content);
        logger.info("系统短信,mobile:" + mobile + " 内容:" + content);
        if ("true".equals(open)) {
            return smsfyTransport.send(message, null);
        } else {
            return "";
        }

    }

    @Override
    public String sendMessage(Msg message) throws SMSException {
        if ("true".equals(open)) {
            return smsfyTransport.send(message, null);
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
            return smsfyTransport.send(message, null);
        } else {
            return "";
        }
    }
}
