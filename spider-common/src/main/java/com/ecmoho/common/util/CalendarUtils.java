package com.ecmoho.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CalendarUtils {

    private static Map<String, String> cnHolidayMap = new HashMap<>();
    static {
        Properties props = new Properties();
        try {
//            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("cn_holiday.properties"));
            for (Map.Entry<Object, Object> item : props.entrySet()) {
                cnHolidayMap.put(item.getKey().toString(), new String(item.getValue().toString().getBytes("iso8859-1"), "UTF-8"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CalendarUtils() {
    }

    public static Date add(int field, Date date, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int fieldNewValue = (c.get(field) + value);
        c.set(field, fieldNewValue);
        return c.getTime();
    }

    public static int get(int field, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(field);
    }

    public static boolean isEqualField(int field, Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return c1.get(field) == c2.get(field);
    }

    //获取明天得到日期
    public static Date getTomorrawDay(Date today) {
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY,20);
        cal.set(Calendar.SECOND,00);
        cal.set(Calendar.MINUTE,00);
        //Date sss = new SimpleDateFormat("yyyy--MM--ss--hh--mm").format(new java.util.Date());*/
        Calendar cld = Calendar.getInstance();
        cld.setTime(new Date());
        cld.set(Calendar.HOUR_OF_DAY, 23);
        cld.set(Calendar.SECOND, 59);
        cld.set(Calendar.MINUTE, 59);
        return cld.getTime();
    }

    public static boolean isWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        int week = cld.get(Calendar.DAY_OF_WEEK);
        if (week == 1 || week == 7) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 股票交易时间点
     */
    private static int[][] stockTradeTimeNode = new int[][]{new int[]{930, 1130}, new int[]{1300, 1500}};

    public static boolean isStockTradeTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        String hhmmStr = DateUtil.date2String(date, "HHmm");
        int hhmm = Integer.valueOf(hhmmStr);
        if (hhmm >= stockTradeTimeNode[0][0] && hhmm <= stockTradeTimeNode[0][1]
                || hhmm >= stockTradeTimeNode[1][0] && hhmm <= stockTradeTimeNode[1][1]) {
            return true;
        }
        return false;
    }

    /**
     * 中午休盘
     */
    public static boolean isStockTradePause(Date date) {
        if (date == null) {
            date = new Date();
        }
        String hhmmStr = DateUtil.date2String(date, "HHmm");
        int hhmm = Integer.valueOf(hhmmStr);
        if (hhmm > 1130 && hhmm < 1300) {
            return true;
        }
        return false;
    }

    /**
     * 中午休盘
     */
    public static String getCnHolidayText(Date date) {
        if (date == null) {
            date = new Date();
        }
        String yyMMdd = DateUtil.date2String(date, "yyyy-MM-dd");
        return cnHolidayMap.get(yyMMdd);
    }

    public static void main(String[] args) {
        Date ds = DateUtil.string2Date("2015-10-1 12:00:12", "yyyy-MM-dd HH:mm:ss");
        boolean b = isStockTradePause(ds);
        System.out.println(b);
        System.out.println(getCnHolidayText(ds));
    }

}
