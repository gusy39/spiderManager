package com.ecmoho.message.smsfy;

import com.ecmoho.message.MessageTransport;
import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import com.shcm.bean.SendResultBean;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

public class SMSFYTransport implements MessageTransport {
    public static final Logger logger = Logger.getLogger(SMSFYTransport.class);

    private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
    private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";

    // 接口帐号
    private static final String account = "1001@501245020001";

    // 接口密钥
    private static final String authkey = "33DF4471AD31F811E12991BC7F65927F";

    // 通道组编号
    private static final int cgid = 52;

    // 默认使用的签名编号(未指定签名编号时传此值到服务器)
    private static final int csid = 0;

    static {
        // 发送参数
        OpenApi.initialzeAccount(sOpenUrl, account, authkey, cgid, csid);
        // 状态及回复参数
        DataApi.initialzeAccount(sDataUrl, account, authkey);
    }


    private String sendOnceMsg(String mobile, String content) throws SMSException {
        if (StringUtils.isEmpty(mobile)) {
            throw new SMSException("Mobile number is empty!");
        }
        if (StringUtils.isEmpty(content)) {
            throw new SMSException("Content number is empty!");
        }
        List<SendResultBean> listItem = OpenApi.sendOnce(mobile, content, 0, 0, null);
        if (listItem != null) {
            for (SendResultBean t : listItem) {
                if (t.getResult() < 1) {
                    logger.info("发送提交失败: " + t.getErrMsg());
                    return "CODE_88888";
                }
                logger.info("发送成功: 消息编号<" + t.getMsgId() + "> 总数<" + t.getTotal() + "> 余额<" + t.getRemain() + ">");
            }
        }else{
            return "CODE_88888";
        }
        return "CODE_77777";
    }

    @Override
    public void retry(Msg message) {

    }

    @Override
    public void retryAsync(Msg message) {

    }

    @Override
    public String send(Msg message, MessagingEngine.QueuePolicy queuePolicy) throws SMSException {
        if(message == null){
            throw new SMSException("Message content is null!");
        }
        return this.sendOnceMsg(message.getTo(),message.getContent());
    }

    @Override
    public void sendAsync(Msg message, MessagingEngine.QueuePolicy queuePolicy) {

    }
}
