package com.ecmoho.message;


import com.ecmoho.message.email.EmailEngine;
import com.ecmoho.message.email.EmailTransport;
import com.ecmoho.message.sms.SMSEngine;
import com.ecmoho.message.sms.SMSTransport;
import com.ecmoho.message.smsext.SMSEXTEngine;
import com.ecmoho.message.smsext.SMSEXTTransport;
import com.ecmoho.message.smsfy.SMSFYEngine;
import com.ecmoho.message.smsfy.SMSFYTransport;

import java.util.HashMap;
import java.util.Map;

public class MsgEngineFactory {

    private static Map<String, MessagingEngine> engine;

    /*
     * transport for sending message
     */
    public enum EngineType {
        EMAIL,
        SMS,
        SMSFY,
        EXT,
    }

    public  Map<String, MessagingEngine> getEngine() {
        return MsgEngineFactory.engine;
    }

    public void setEngine(Map<String, MessagingEngine> engine) {
        MsgEngineFactory.engine = engine;
    }

    public static MessagingEngine getMsgEngine(EngineType engineType) {
        if (MsgEngineFactory.engine == null || MsgEngineFactory.engine.size() == 0) {
            //设置默认的，也可以在配资里面定制参数
            engine = new HashMap<>();
            EmailEngine emailEngine = new EmailEngine();
            emailEngine.setEmailTransport(new EmailTransport());
            engine.put("email", emailEngine);

            SMSEngine smsEngine = new SMSEngine();
            smsEngine.setSmsTransport(new SMSTransport());
            engine.put("sms", smsEngine);

            SMSFYEngine smsfyEngine = new SMSFYEngine();
            smsfyEngine.setSmsfyTransport(new SMSFYTransport());
            engine.put("smsfy", smsfyEngine);

            SMSEXTEngine smsextEngine = new SMSEXTEngine();
            smsextEngine.setSmsextTransport(new SMSEXTTransport());
            engine.put("ext",smsextEngine);
        }

        return engine.get(engineType.toString().toLowerCase());
    }

    public static void main(String[] args) throws SMSException {
        // MsgEngineFactory.getMsgEngine(EngineType.SMS).sendMessage("18621989299","mytest");
        String result = MsgEngineFactory.getMsgEngine(EngineType.EXT).sendMessage("18217005270","您的验证码为1232");

    }
}
