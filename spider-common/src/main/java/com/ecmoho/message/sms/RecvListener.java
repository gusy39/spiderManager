package com.ecmoho.message.sms;

import com.wondertek.esmp.esms.empp.*;

/**
 * Created by Administrator on 2014/7/31 0031.
 */
public class RecvListener implements EMPPRecvListener {

    //private static Logger logger = Logger.getLogger(RecvListener.class.getName());

    private static final long RECONNECT_TIME = 10 * 1000;

    private EmppApi emppApi = null;

    private int closedCount = 0;

    protected RecvListener(){}

    public RecvListener(EmppApi emppApi){
        this.emppApi = emppApi;
    }

    // 处理接收到的消息
    public void onMessage(EMPPObject message) {
        if(message instanceof EMPPUnAuthorization){
            EMPPUnAuthorization unAuth=(EMPPUnAuthorization)message;
            //logger.info("客户端无权执行此操作 commandId="+unAuth.getUnAuthCommandId());
            return;
        }
        if(message instanceof EMPPSubmitSMResp){
            EMPPSubmitSMResp resp=(EMPPSubmitSMResp)message;
            //logger.info("收到sumbitResp:");
            byte[] msgId=fiterBinaryZero(resp.getMsgId());
            //logger.info("msgId="+new BigInteger(msgId));
            //logger.info("result="+resp.getResult());
            return;
        }
        if(message instanceof EMPPDeliver){
            EMPPDeliver deliver = (EMPPDeliver)message;
            if(deliver.getRegister()== EMPPSubmitSM.EMPP_STATUSREPORT_TRUE){
                //收到状态报告
                EMPPDeliverReport report=deliver.getDeliverReport();
                //logger.info("收到状态报告:");
                byte[] msgId=fiterBinaryZero(report.getMsgId());
                //logger.info("msgId="+new BigInteger(msgId));
                //logger.info("status="+report.getStat());

            }else{
                //收到手机回复
                //logger.info("收到"+deliver.getSrcTermId()+"发送的短信");
                //logger.info("短信内容为："+deliver.getMsgContent());
            }
            return;
        }
        if(message instanceof EMPPSyncAddrBookResp){
            EMPPSyncAddrBookResp resp=(EMPPSyncAddrBookResp)message;
            if(resp.getResult()!= EMPPSyncAddrBookResp.RESULT_OK)
                //logger.info("同步通讯录失败")
            	;
            else{
                //logger.info("收到服务器发送的通讯录信息");
                //logger.info("通讯录类型为："+resp.getAddrBookType());
                //logger.info(resp.getAddrBook());
            }
        }
        if(message instanceof EMPPChangePassResp){
            EMPPChangePassResp resp=(EMPPChangePassResp)message;
            if(resp.getResult()== EMPPChangePassResp.RESULT_VALIDATE_ERROR)
                //logger.info("更改密码：验证失败");
            if(resp.getResult()== EMPPChangePassResp.RESULT_OK){
                //logger.info("更改密码成功,新密码为："+resp.getPassword());
                emppApi.setPassword(resp.getPassword());
            }
            return;

        }
        if(message instanceof EMPPReqNoticeResp){
            EMPPReqNoticeResp response=(EMPPReqNoticeResp)message;
            if(response.getResult()!= EMPPReqNoticeResp.RESULT_OK)
                //logger.info("查询运营商发布信息失败")
            	;
            else{
                //logger.info("收到运营商发布的信息");
                //logger.info(response.getNotice());
            }
            return;
        }
        if(message instanceof EMPPAnswer){
            //logger.info("收到企业疑问解答");
            EMPPAnswer answer=(EMPPAnswer)message;
            //logger.info(answer.getAnswer());
        }
        //logger.info(message);
    }
    //处理连接断掉事件
    public void OnClosed(Object object) {
        // 该连接是被服务器主动断掉，不需要重连
        if(object instanceof EMPPTerminate){
            //logger.info("收到服务器发送的Terminate消息，连接终止");
            return;
        }
        //这里注意要将emppApi做为参数传入构造函数
        RecvListener listener = new RecvListener(emppApi)
                ;
        //logger.info("连接断掉次数："+(++closedCount));
        for(int i = 1;!emppApi.isConnected();i++){
            try {
                //logger.info("重连次数:"+i);
                Thread.sleep(RECONNECT_TIME);
                emppApi.reConnect(listener);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //logger.info("重连成功");
    }

    //处理错误事件
    public void OnError(Exception e) {
        e.printStackTrace();
    }

    private static byte[] fiterBinaryZero(byte[] bytes) {
        byte[] returnBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            returnBytes[i] = bytes[i];
        }
        return returnBytes;
    }
}
