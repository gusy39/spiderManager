package com.ecmoho.common.util;

import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_DATE = "yyyy-MM-dd";

    public static final String PATTERN_DATE_MIN="yyyy-MM-dd HH:mm";

	public static String timestamp2String(Timestamp timestamp, String pattern) {
		if (timestamp == null) {
			throw new IllegalArgumentException("timestamp null illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(timestamp.getTime()));
	}

	public static String date2String(Date date, String pattern) {
		if (date == null) {
			throw new IllegalArgumentException("timestamp null illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
			;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Timestamp currentTimestamp() {
		return new Timestamp(new Date().getTime());
	}


	public static Long currentTime() {
		return new Date().getTime();
	}
	public static String currentTimestamp2String(String pattern) {
		return timestamp2String(currentTimestamp(), pattern);
	}

	public static Timestamp string2Timestamp(String strDateTime, String pattern) {
		if (strDateTime == null || strDateTime.equals("")) {
			throw new IllegalArgumentException("Date Time Null Illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(strDateTime);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new Timestamp(date.getTime());
	}

	public static Date string2Date(String strDate, String pattern) {
		if (strDate == null || strDate.equals("")) {
			throw new RuntimeException("str date null");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = DateUtil.PATTERN_DATE;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;

		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	public static String stringToYear(String strDest) {
		if (strDest == null || strDest.equals("")) {
			throw new IllegalArgumentException("str dest null");
		}

		Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return String.valueOf(c.get(Calendar.YEAR));
	}

	public static String stringToMonth(String strDest) {
		if (strDest == null || strDest.equals("")) {
			throw new IllegalArgumentException("str dest null");
		}

		Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// return String.valueOf(c.get(Calendar.MONTH));
		int month = c.get(Calendar.MONTH);
		month = month + 1;
		if (month < 10) {
			return "0" + month;
		}
		return String.valueOf(month);
	}

	public static String stringToDay(String strDest) {
		if (strDest == null || strDest.equals("")) {
			throw new IllegalArgumentException("str dest null");
		}

		Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			return "0" + day;
		}
		return "" + day;
	}

	public static Date getFirstDayOfMonth(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = 1;
		c.set(year, month, day, 0, 0, 0);
		return c.getTime();
	}

	public static Date getLastDayOfMonth(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = 1;
		if (month > 11) {
			month = 0;
			year = year + 1;
		}
		c.set(year, month, day - 1, 0, 0, 0);
		return c.getTime();
	}

	public static String date2GregorianCalendarString(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date is null");
		}
		long tmp = date.getTime();
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTimeInMillis(tmp);
		try {
			XMLGregorianCalendar t_XMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(ca);
			return t_XMLGregorianCalendar.normalize().toString();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException("Date is null");
		}

	}

	public static boolean compareDate(Date firstDate, Date secondDate) {
		if (firstDate == null || secondDate == null) {
			throw new RuntimeException();
		}

		String strFirstDate = date2String(firstDate, "yyyy-MM-dd");
		String strSecondDate = date2String(secondDate, "yyyy-MM-dd");
		if (strFirstDate.equals(strSecondDate)) {
			return true;
		}
		return false;
	}


	public static Date getTruncDate(Date date) {
		String strDateTime = date2String(date,"yyyy-MM-dd");
		return string2Date(strDateTime,"yyyy-MM-dd");
	}

	public static Date getStartTimeOfDate(Date currentDate) {
		Assert.notNull(currentDate);
		String strDateTime = date2String(currentDate,"yyyy-MM-dd") + " 00:00:00";
		return string2Date(strDateTime,"yyyy-MM-dd hh:mm:ss");
	}
	
	public static Date getEndTimeOfDate(Date currentDate) {
		Assert.notNull(currentDate);
		String strDateTime = date2String(currentDate,"yyyy-MM-dd") + " 59:59:59";
		return string2Date(strDateTime,"yyyy-MM-dd hh:mm:ss");
	}

    /**
     * 判断某个时间是不是股票交易时间  9:30-11:30  13:00-15：00
     * @param paramDate
     * @return
     */
    public static Boolean  isBusinessTime(Date paramDate)
    {
        Calendar cl1 = Calendar.getInstance();
        cl1.setTime(paramDate);
        cl1.set(Calendar.SECOND, 00);
        cl1.set(Calendar.MINUTE, 30);
        cl1.set(Calendar.HOUR_OF_DAY,9);

        Calendar cl2 = Calendar.getInstance();
        cl2.setTime(paramDate);
        cl2.set(Calendar.SECOND, 00);
        cl2.set(Calendar.MINUTE, 30);
        cl2.set(Calendar.HOUR_OF_DAY,11);

        Calendar cl3 = Calendar.getInstance();
        cl3.setTime(paramDate);
        cl3.set(Calendar.SECOND, 00);
        cl3.set(Calendar.MINUTE, 00);
        cl3.set(Calendar.HOUR_OF_DAY,13);

        Calendar cl4 = Calendar.getInstance();
        cl4.setTime(paramDate);
        cl4.set(Calendar.SECOND, 00);
        cl4.set(Calendar.MINUTE, 00);
        cl4.set(Calendar.HOUR_OF_DAY,15);

        if(paramDate.getTime() < cl1.getTimeInMillis())
        {
            return false;
        }
        if(paramDate.getTime() > cl1.getTimeInMillis() && paramDate.getTime() < cl2.getTimeInMillis())
        {
            return true;
        }
        if(paramDate.getTime() > cl2.getTimeInMillis() && paramDate.getTime() < cl3.getTimeInMillis())
        {
            return false;
        }
        if(paramDate.getTime() > cl3.getTimeInMillis() && paramDate.getTime() < cl4.getTimeInMillis())
        {
            return true;
        }
        if(paramDate.getTime() > cl4.getTimeInMillis())
        {
            return false;
        }
        return false;
    }

    /**
     * 判断是不是今天
     * @param date
     * @return
     */
    public static Boolean isToDay(Date date){
        date = getStartTimeOfDate(date);
        Date toDay = new Date();
        toDay = getStartTimeOfDate(toDay);
        if(!date.equals(toDay)){
            return false;
        }
        return true;
    }

    public static Boolean isConduct(Date attendStart,Date attendOver,Date gameStart,Date gameOver){
        attendStart = DateUtil.getStartTimeOfDate(attendStart);
        attendOver = DateUtil.getStartTimeOfDate(attendOver);
        gameStart = DateUtil.getStartTimeOfDate(gameStart);
        gameOver = DateUtil.getStartTimeOfDate(gameOver);
        if(gameStart.getTime() > gameOver.getTime()){
             return false;
        }
        if(attendStart.getTime() > attendOver.getTime()){
            return false;
        }
        if(attendStart.getTime() > gameStart.getTime()){
            return false;
        }
        return true;
    }


    //**=====================new===================*//

    /**
     * 毫秒转字符串
     * @param time
     * @return
     */
    public static String long2String(long time){
        Date date=new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_STANDARD);
        return sdf.format(date);
    }

    /**
     * 毫秒转字符串
     * @param time
     * @return
     */
    public static String long2DateString(long time){
        Date date=new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_DATE);
        return sdf.format(date);
    }



    public static String convert2StandDate(String str){
        if(StringUtil.isEmpty(str)){
            throw new RuntimeException("时间不能为空");
        }
        str=str.trim();
        String newStr=str;
        int count=0;
        while( newStr.indexOf(":")!=-1){
            count++;
            //将字符串出现c的位置之前的全部截取掉
            newStr = newStr.substring(newStr.indexOf(":")+1);
        }
        if(count==2){
            return DateUtil.long2String(DateUtil.string2Date(str,PATTERN_STANDARD).getTime());
        }
        if(count==1){
            return DateUtil.long2String(DateUtil.string2Date(str,PATTERN_DATE_MIN).getTime());
        }
        if(count==0){
            return DateUtil.long2String(DateUtil.string2Date(str,PATTERN_DATE).getTime());
        }
        return null;

    }

	public static void main(String[] args){
        System.out.println(DateUtil.convert2StandDate("2016-10-10 10"));
        System.out.println(DateUtil.convert2StandDate("2016-10-10 10:20"));
        System.out.println(DateUtil.convert2StandDate("2016-10-10 10:17:20"));
		/*String str1 = "2011-01-01";
		String str2 = "1988-09-09";
		Date date1 = DateUtil.string2Date(str1, "yyyy-MM-dd");
		Date date2 = DateUtil.string2Date(str2, "yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		c2.add(Calendar.YEAR, 4);
		if (c2.before(c1)) {
			System.out.println("illegal");
		}else {
			System.out.println("ok");
		}*/

        /*Date myDate = DateUtil.string2Date("2015-8-14 09:05:00",DateUtil.PATTERN_STANDARD);
        System.out.print(isToDay(myDate));*/

//        Date attendStart = DateUtil.string2Date("2015-8-14 09:05:00",DateUtil.PATTERN_STANDARD);
//        Date attendOver = DateUtil.string2Date("2015-8-16 09:05:00",DateUtil.PATTERN_STANDARD);
//        Date gameStart = DateUtil.string2Date("2015-8-15 09:05:00",DateUtil.PATTERN_STANDARD);
       Date gameOver = DateUtil.string2Date("2015-8-15 09:05:33",DateUtil.PATTERN_STANDARD);
//        System.out.print(isConduct(attendStart,attendOver,gameStart,gameOver));


        System.out.println(gameOver.getTime());

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time=new Long("1439600700000");
        String d = format.format(time);

        System.out.println("Format To String(Date):"+d);

        System.out.println(DateUtil.currentTimestamp());

    }
}