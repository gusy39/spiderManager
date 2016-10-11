package com.ecmoho.message;


import org.apache.log4j.Logger;

public abstract class MessagingEngine  {
    private static final Logger logger = Logger.getLogger(MessagingEngine.class);

    /**
     * Severity values for alert emails
     */
    public enum SEVERITY {
        ERROR,
        INFO,
        JOB,
        IMPORTANT,
        IMPORTANT_NEEDED_TAKE_ACTION,
        REPORTS,
    }

    /*
     * Define message queue processing policy
     */
    public enum QueuePolicy {
        ENQUEUE,            // Queue message
        IMMEDIATE,            // Don't queue, attempt to send right away
        IMMEDIATE_W_ENQUEUE // Queue message and attempt to send it right away
    }


    public abstract String sendMessage(Msg message) throws SMSException;

    public abstract String sendMessage(String mobile,String content) throws SMSException;

    public abstract String sendMessage(String toEmail,String subject,String content) throws SMSException;


}
