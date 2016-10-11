package com.ecmoho.common.util;

/**
 *
 */
public class StringUtilsExt {
    /*基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。
    这样就可以直接例用length方法获得字符串的字节长度了*/
    public static int getWordCountRegex(String s) {
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }
}
