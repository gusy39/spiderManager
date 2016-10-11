package com.ecmoho.message.sms;

/**
 * 短信错误码
 * Created by tad.
 */
public enum EMACodeUnit {

    CODE_0("0","正确"),
    CODE_1("1","消息结构错"),
    CODE_2("2","命令字错"),
    CODE_3("3","消息序号重复"),
    CODE_4("4","消息长度错"),
    CODE_5("5","资费代码错"),
    CODE_6("6","超过最大信息长"),
    CODE_7("7","业务代码错"),
    CODE_8("8","流量控制错"),
    CODE_9("9","短信网关不负责服务此计费号码"),
    CODE_10("10","Src_Id错误"),
    CODE_11("11","Msg_src错误"),
    CODE_12("12","Fee_terminal_Id错误"),
    CODE_13("13","Dest_terminal_Id错误"),
    CODE_14("14","双重认证错"),
    CODE_10001("10001","收信方号码为黑名单用户"),
    CODE_10002("10002","短信内容中包含敏感字，该短信被拒发"),
    CODE_10003("10003","提供给该试用企业的短信发送量已用完，系统停止对其的短信发送服务"),
    CODE_10004("10004","超过允许发给该用户的最大发信频率"),
    CODE_10005("10005","服务忙，请稍后再发短信"),
    CODE_10006("10006","向网关发送短信失败"),
    CODE_10007("10007","超过允许发给该用户的最大发信频率"),
    CODE_77777("77777","无法连接短信服务器"),
    CODE_88888("88888","运行异常"),
    CODE_other("99999","其他错误");

    private EMACodeUnit(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    /**
     * 状态码
     */
    private String code;
    /**
     * 信息
     */
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static void main(String[] args) {
        System.out.println(EMACodeUnit.valueOf("fsdfsd").getMsg());
    }
}
