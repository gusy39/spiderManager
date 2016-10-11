package com.ecmoho.message.email;


import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;

public class EmailEngine extends MessagingEngine {
    private static final Logger logger = Logger.getLogger(EmailEngine.class);
    private EmailTransport emailTransport;

    public EmailTransport getEmailTransport() {
        return emailTransport;
    }

    public void setEmailTransport(EmailTransport emailTransport) {
        this.emailTransport = emailTransport;
    }

    public String sendMessage(Msg message) throws SMSException {
        try {
            return emailTransport.send(message, null);
        } catch (MessagingException e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new SMSException(e.getMessage());
        }
    }

    @Override
    public String sendMessage(String toEmail, String content) throws SMSException {
        try {
            Msg message = new Msg();
            message.setTo(toEmail);
            message.setContent(content);
            return emailTransport.send(message, null);
        } catch (MessagingException e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new SMSException(e.getMessage());
        }
    }

    @Override
    public String sendMessage(String toEmail, String subject, String content) throws SMSException {
        try {

            Msg message = new Msg();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setContent(content);
            return emailTransport.send(message, null);
        } catch (MessagingException e) {
            logger.info(ExceptionUtils.getFullStackTrace(e));
            throw new SMSException(e.getMessage());
        }
    }
}
