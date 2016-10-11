package com.ecmoho.common.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tad on 2015/8/6.
 */
public class StringUtil {

    /**
     * 判空
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {

        if (value == null || value.trim().equals("")||value.trim().equals("null")) {

            return true;
        }

        return false;
    }

    /**
     * 转换为int
     * @param value
     * @return
     */
    public static int convertToInteger(String value) {

        if (value == null || value.trim().equals("")) {

            return 0;
        }

        return Integer.valueOf(value);
    }

    /**
     * 转换为long
     * @param value
     * @return
     */
    public static long convertToLong(String value) {

        if (value == null || value.trim().equals("")) {

            return 0;
        }

        return Long.valueOf(value);
    }

    /**
     * 转换为float
     * @param value
     * @return
     */
    public static float convertToFloat(String value) {

        if (value == null || value.trim().equals("")) {

            return 0;
        }

        return Float.valueOf(value);
    }

    /**
     * 转换为byte
     * @param value
     * @return
     */
    public static byte convertToByte(String value) {

        if (value == null || value.trim().equals("")) {

            return 0;
        }

        return Byte.valueOf(value);
    }

    /**
     * 转换为short
     * @param value
     * @return
     */
    public static short convertToShort(String value) {

        if (value == null || value.trim().equals("")) {

            return 0;
        }

        return Short.valueOf(value);
    }

    /**
     * 转换为BigDecimal
     * @param value
     * @return BigDecimal
     */
    public static BigDecimal convertToBigDecimal(String value) {

        if (value == null || value.trim().equals("")) {

            return BigDecimal.ZERO;
        }

        return new BigDecimal(value);
    }

    /**
     * 判断是否为正数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isNumeric("-13.0"));
    }

}
