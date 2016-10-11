package com.ecmoho.common.sns;

import com.ecmoho.common.constant.Constants;
import com.ecmoho.common.regex.RegexUtils;
import com.ecmoho.common.util.CollectionHelper;
import com.ecmoho.common.util.StringUtilsExt;

/**
 * Created by Administrator on 2015/7/10.
 */
public class WeiBoUtils {

    /**
     * 解析@的所有用户 @格式必须满足 <@xxx >最后以空格隔开
     *
     * @param content
     * @return
     */
    public static String[] parseAtNickName(String content) {
        String userName[] = RegexUtils.atMe(content);
        if (userName != null) {
            for (int i = 0; i < userName.length; i++) {
                userName[i] = userName[i].trim().replaceAll("@|:", "");
            }
        }
        return CollectionHelper.arrayUnique(userName);
    }

    public static String replaceUrlToLink(String content) {
        return content.replaceAll(RegexUtils.URL, "<a href='$1' target='_black'>网页链接</a>");
    }

    public static String replaceAtNickNameToLink(String content, String urlPix) {
        if (content == null) {
            return "";
        }
        return content.replaceAll(RegexUtils.WEBBO_AT_ME, "<a href='" + urlPix + "/$1' target='_black'>@$1</a>");
    }

    public static String cutContent(String content) {
        String newStr = content;
        while (StringUtilsExt.getWordCountRegex(newStr) > Constants.BYTE_LENG_240) {
            newStr = newStr.substring(0, newStr.lastIndexOf("@"));
            System.out.println(newStr);
        }
        return newStr;
    }


    public static void main(String[] args) {
    }
}
