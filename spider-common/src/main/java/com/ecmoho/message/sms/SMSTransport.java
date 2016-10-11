package com.ecmoho.message.sms;

import com.ecmoho.message.MessageTransport;
import com.ecmoho.message.MessagingEngine;
import com.ecmoho.message.Msg;
import com.ecmoho.message.SMSException;
import com.wondertek.esmp.esms.empp.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SMSTransport implements MessageTransport {

	private final static Logger logger = Logger.getLogger(SMSTransport.class.getName());
    /**发送短信服务器地址*/
    private final static String host = "211.136.163.68";
    /**发送短信服务器端口*/
    private final static int port =9981 ;
    /**企业通web登录帐号*/
    private final static String accountId = "10657109030998";//10657109030998
    /**企业通web登录密码*/
    private final static String password = "Zrpei890";//boES71Ec
    /**企业通web登录名*/
    private final static String serviceId = "admin9";//2171097186  //4801KcqW

    private EmppApi emppApi = new EmppApi();
    private  RecvListener listener = new RecvListener(emppApi);

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
        // TODO Auto-generated method stub

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String sendOnceMsg(String mobile,String content) throws SMSException{
        if(StringUtils.isEmpty(mobile)){
            throw new SMSException("Mobile number is empty!");
        }
        if(StringUtils.isEmpty(content)){
            throw new SMSException("Content number is empty!");
        }
        if (!emppApi.isConnected()){
            //建立连接
            connectEmpp();
        }
        //发送短信
        if (emppApi.isSubmitable()) {
            EMPPSubmitSM msg = (EMPPSubmitSM) EMPPObject.createEMPP(EMPPData.EMPP_SUBMIT);
            List dstId = new ArrayList();
            dstId.add(mobile);
            msg.setDstTermId(dstId);
            msg.setSrcTermId(accountId);
            msg.setServiceId(serviceId);
            EMPPShortMsg msgContent = new EMPPShortMsg(EMPPShortMsg.EMPP_MSG_CONTENT_MAXLEN);
            try {
                msgContent.setMessage(content.getBytes("gbk"));
                msg.setShortMessage(msgContent);
                msg.assignSequenceNumber();
                EMPPSubmitSMResp essr = emppApi.submitMsg(msg);
                return EMACodeUnit.CODE_0.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "CODE_88888";
            }
        }
        return "CODE_77777";
    }

    /**
     * 和企信通服务器建立连接
     */
    private  void connectEmpp(){
        try {
            //建立同服务器的连接
            EMPPConnectResp response = emppApi.connect(host, port, accountId,password, listener);
            if (response == null) {
                logger.info("连接超时失败");
            }
            if (!emppApi.isConnected()) {
                logger.info("连接失败:响应包状态位=" + response.getStatus());
            }
        } catch (Exception e) {
            logger.info("发生异常，导致连接失败");
            e.printStackTrace();
        }
    }
}
