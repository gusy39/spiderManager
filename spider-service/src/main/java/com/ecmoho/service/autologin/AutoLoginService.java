package com.ecmoho.service.autologin;

import java.util.Map;

/**
 * Created by 许巧生 on 2016/9/22.
 */
public interface AutoLoginService {

     Map<String, String> login(String SBCodes, Integer type);
}
