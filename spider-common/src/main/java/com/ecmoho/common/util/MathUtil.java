package com.ecmoho.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class MathUtil {
	
	//默认的数值运算精度
	private static final int scale = 2;
	
	private static final int max_scale = 8;
	
	//进行处罚运算时的数字精度，目的是要更精确的得到值，因为用BigDecimal进行处罚运算的时候，必须指定精度
	private static final int scaleOfDivide = 10;
	
	//为了确保精确的小数位,在小数进行运算的时候,先乘以scaleIndex,待运算结束以后再除以scaleIndex.
	//private static int scaleIndex = 10000;
	
	/**
	 * 提供精确的加法运算
	 * @ param num_1 被加数
	 * @ param num_2 加数
	 * @ return 两个参数的和
	 */
	public static String add(String num_1, String num_2) {
		return add(num_1, num_2, scale);
	}
	
	public static String add(String num_1, String num_2, int iscale) {
		if (StringUtils.isBlank(num_1)) return round(num_2);
		if (StringUtils.isBlank(num_2)) return round(num_1);
		//summand为被加数
		BigDecimal summmand = new BigDecimal(StringUtils.trimToEmpty(num_1));
		//addend为加数
		BigDecimal addend = new BigDecimal(StringUtils.trimToEmpty(num_2));
		
		BigDecimal result = summmand.add(addend);
		return result.setScale(iscale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static String add(Object obj1, Object obj2) {
		if (obj1 == null && obj2 != null) return round(String.valueOf(obj2));
		if (obj2 == null && obj1 != null) return round(String.valueOf(obj1));
		if (obj1 == null && obj2 == null) return "0.00";
		BigDecimal big1 = null;
		if (obj1 instanceof BigDecimal) {
			big1 = (BigDecimal) obj1;
		} else {
			big1 = new BigDecimal(String.valueOf(obj1));
		}
		BigDecimal big2 = null;
		if (obj2 instanceof BigDecimal) {
			big2 = (BigDecimal) obj2;
		} else {
			big2 = new BigDecimal(String.valueOf(obj2));
		}
		BigDecimal result = big1.add(big2);
		return result.setScale(scale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static float add(float num_1, float num_2) {
		//summand为被加数
		BigDecimal summmand = new BigDecimal(num_1);
		//addend为加数
		BigDecimal addend = new BigDecimal(num_2);
		BigDecimal result = summmand.add(addend);
		return result.floatValue();
	}
	
	public static double add(double num_1, double num_2) {
		//summand为被加数
		BigDecimal summmand = new BigDecimal(num_1);
		//addend为加数
		BigDecimal addend = new BigDecimal(num_2);
		BigDecimal result = summmand.add(addend);
		return result.doubleValue();
	}

    public static BigDecimal add(BigDecimal num_1, double num_2) {
        //summand为被加数
        //addend为加数
        BigDecimal addend = new BigDecimal(num_2);
        BigDecimal result = num_1.add(addend);
        return result;
    }

    public static BigDecimal add(BigDecimal num_1, BigDecimal num_2) {
        //summand为被加数
        //addend为加数
        BigDecimal result = num_1.add(num_2);
        return result;
    }
	
	/**
	 * 提供精确的减法运算
	 * @ param num_1 被减数
	 * @ param num_2 减数
	 * @ return
	 */
	public static String substract(String num_1, String num_2) {
		return substract(num_1, num_2, scale);
	}
	
	/**
	 * 提供精确的减法运算
	 * @ param num_1 被减数
	 * @ param num_2 减数
	 * @ return
	 */
	public static String substract(String num_1, String num_2, int iscale) {
		if (StringUtils.isBlank(num_1)) num_1 = "0";
		if (StringUtils.isBlank(num_2)) return round(num_1);
		//minuend为被减数
		BigDecimal minuend = new BigDecimal(StringUtils.trimToEmpty(num_1));
		//subtrahend为减数
		BigDecimal subtrahend = new BigDecimal(StringUtils.trimToEmpty(num_2));
		BigDecimal result = minuend.subtract(subtrahend);
		return result.setScale(iscale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static String substract(Object obj1, Object obj2) {
		if (obj1 == null && obj2 != null) return round(String.valueOf(obj2));
		if (obj2 == null && obj1 != null) return round(String.valueOf(obj1));
		if (obj1 == null && obj2 == null) return "0.00";
		BigDecimal big1 = null;
		if (obj1 instanceof BigDecimal) {
			big1 = (BigDecimal) obj1;
		} else {
			big1 = new BigDecimal(String.valueOf(obj1));
		}
		BigDecimal big2 = null;
		if (obj2 instanceof BigDecimal) {
			big2 = (BigDecimal) obj2;
		} else {
			big2 = new BigDecimal(String.valueOf(obj2));
		}
		BigDecimal result = big1.subtract(big2);
		return result.setScale(scale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static float substract(float num_1, float num_2) {
		//minuend为被减数
		BigDecimal minuend = new BigDecimal(num_1);
		//subtrahend为减数
		BigDecimal subtrahend = new BigDecimal(num_2);
		BigDecimal result = minuend.subtract(subtrahend);
		return result.floatValue();
	}
	
	public static double substract(double num_1, double num_2) {
		//minuend为被减数
		BigDecimal minuend = new BigDecimal(num_1);
		//subtrahend为减数
		BigDecimal subtrahend = new BigDecimal(num_2);
		BigDecimal result = minuend.subtract(subtrahend);
		return result.doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算
	 * @ param num_1 被乘数
	 * @ param num_2 乘数
	 * @ return
	 */
	public static String multiplicate(String num_1, String num_2) {
		return multiplicate(num_1, num_2, scale);
	}
	
	/**
	 * 提供精确的乘法运算
	 * @ param num_1 被乘数
	 * @ param num_2 乘数
	 * @ return
	 */
	public static String multiplicate(String num_1, String num_2, int iscale) {
		if (StringUtils.isBlank(num_1)) return round(num_2);
		if (StringUtils.isBlank(num_2)) return round(num_1);
		//multiplicand为被乘数
		BigDecimal multiplicand = new BigDecimal(StringUtils.trimToEmpty(num_1));
		//multiplier为乘数
		BigDecimal multiplier = new BigDecimal(StringUtils.trimToEmpty(num_2));
		
		BigDecimal result = multiplicand.multiply(multiplier);
		return result.setScale(iscale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static String multiplicate(Object obj1, Object obj2) {
		if (obj1 == null && obj2 != null) return round(String.valueOf(obj2));
		if (obj2 == null && obj1 != null) return round(String.valueOf(obj1));
		if (obj1 == null && obj2 == null) return "0.00";
		BigDecimal big1 = null;
		if (obj1 instanceof BigDecimal) {
			big1 = (BigDecimal) obj1;
		} else {
			big1 = new BigDecimal(String.valueOf(obj1));
		}
		BigDecimal big2 = null;
		if (obj2 instanceof BigDecimal) {
			big2 = (BigDecimal) obj2;
		} else {
			big2 = new BigDecimal(String.valueOf(obj2));
		}
		BigDecimal result = big1.multiply(big2);
		return result.setScale(scale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static float multiplicate(float num_1, float num_2) {
		//multiplicand为被乘数
		BigDecimal multiplicand = new BigDecimal(num_1);
		//multiplier为乘数
		BigDecimal multiplier = new BigDecimal(num_2);
		
		BigDecimal result = multiplicand.multiply(multiplier);
		return result.floatValue();
	}
	
	public static float multiplicate(float num_1, float num_2, int iscale) {
		//multiplicand为被乘数
		BigDecimal multiplicand = new BigDecimal(num_1);
		//multiplier为乘数
		BigDecimal multiplier = new BigDecimal(num_2);
		
		BigDecimal result = multiplicand.multiply(multiplier);
		return result.setScale(iscale, RoundingMode.FLOOR).floatValue();
	}
	
	public static double multiplicate(double num_1, double num_2) {
		//multiplicand为被乘数
		BigDecimal multiplicand = new BigDecimal(num_1);
		//multiplier为乘数
		BigDecimal multiplier = new BigDecimal(num_2);
		
		BigDecimal result = multiplicand.multiply(multiplier);
		return result.doubleValue();
	}
	
	public static double multiplicate(double num_1, double num_2, int iscale) {
		//multiplicand为被乘数
		BigDecimal multiplicand = new BigDecimal(num_1);
		//multiplier为乘数
		BigDecimal multiplier = new BigDecimal(num_2);
		
		BigDecimal result = multiplicand.multiply(multiplier);
		return result.setScale(iscale, RoundingMode.FLOOR).doubleValue();
	}
	
	/**
	 * 提供精确的除法运算
	 * @ param num_1 被除数
	 * @ param num_2 除数
	 * @ return
	 */
	public static String divide(String num_1, String num_2) {
		return divide(num_1, num_2, scale);
	}
	
	/**
	 * 提供精确的除法运算
	 * @ param num_1 被除数
	 * @ param num_2 除数
	 * @ return
	 */
	public static String divide(String num_1, String num_2, int iscale) {
		if (StringUtils.isBlank(num_1)) return round(num_2);
		if (StringUtils.isBlank(num_2)) return round(num_1);
		if(StringUtils.equals(StringUtils.trim(num_2),"0"))  {
			return "0";
		}
		//dividend为被除数
		BigDecimal dividend = new BigDecimal(StringUtils.trimToEmpty(num_1));
		//divisor除数
		BigDecimal divisor = new BigDecimal(StringUtils.trimToEmpty(num_2));
		return dividend.divide(divisor, iscale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	public static String divide(Object obj1, Object obj2) {
		if (obj1 == null && obj2 != null) return round(String.valueOf(obj2));
		if (obj2 == null && obj1 != null) return round(String.valueOf(obj1));
		if (obj1 == null && obj2 == null) return "0.00";
		BigDecimal big1 = null;
		if (obj1 instanceof BigDecimal) {
			big1 = (BigDecimal) obj1;
		} else {
			big1 = new BigDecimal(String.valueOf(obj1));
		}
		BigDecimal big2 = null;
		if (obj2 instanceof BigDecimal) {
			big2 = (BigDecimal) obj2;
		} else {
			big2 = new BigDecimal(String.valueOf(obj2));
		}
		return big1.divide(big2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public static float divide(float num_1, float num_2) {
		//dividend为被除数
		BigDecimal dividend = new BigDecimal(num_1);
		//divisor除数
		BigDecimal divisor = new BigDecimal(num_2);
		return dividend.divide(divisor, scaleOfDivide, BigDecimal.ROUND_FLOOR).floatValue();
	}
	
	public static float divide(float num_1, float num_2, int iscale) {
		//dividend为被除数
		BigDecimal dividend = new BigDecimal(num_1);
		//divisor除数
		BigDecimal divisor = new BigDecimal(num_2);
		BigDecimal result = dividend.divide(divisor, scaleOfDivide, BigDecimal.ROUND_FLOOR);
		return result.setScale(iscale, RoundingMode.FLOOR).floatValue();
	}
	
	public static double divide(double num_1, double num_2) {
		//dividend为被除数
		BigDecimal dividend = new BigDecimal(num_1);
		//divisor除数
		BigDecimal divisor = new BigDecimal(num_2);
		return dividend.divide(divisor, scaleOfDivide, BigDecimal.ROUND_FLOOR).doubleValue();
	}
	
	public static double divide(double num_1, double num_2, int iscale) {
		//dividend为被除数
		BigDecimal dividend = new BigDecimal(num_1);
		//divisor除数
		BigDecimal divisor = new BigDecimal(num_2);
		BigDecimal result = dividend.divide(divisor, scaleOfDivide, BigDecimal.ROUND_FLOOR);
		return result.setScale(iscale, RoundingMode.FLOOR).doubleValue();
	}
	
	/**
	 * 提供精确的四舍五入处理
	 * @ param num 需要进行四舍五入的数字
	 * @ return 四舍五入后的结果
	 */
	public static String round(String num) {
		if (StringUtils.isBlank(num)) return null;
		BigDecimal targetNum = new BigDecimal(StringUtils.trimToEmpty(num));
		return targetNum.setScale(scale, BigDecimal.ROUND_FLOOR).toString();
	}
	
	/**
	 * 返回两个数中较大的数
	 * @ param num_1
	 * @ param num_2
	 * @ return 两个数中较大的数
	 */
	public static String returnMax(String num_1, String num_2) {
		if (StringUtils.isBlank(num_1)) return round(num_2);
		if (StringUtils.isBlank(num_2)) return round(num_1);
		BigDecimal max = null;
		//需要比对的第一个数字
		BigDecimal compare1 = new BigDecimal(StringUtils.trimToEmpty(num_1));
		//需要比对的第二个数字
		BigDecimal compare2 = new BigDecimal(StringUtils.trimToEmpty(num_2));
		max = compare1.max(compare2);
		return max.toString();
	}
	
	/**
	 * 返回两个数中较小的数
	 * @ param num_1
	 * @ param num_2
	 * @ return 两个数中较小的数
	 */
	public static String returnMin(String num_1, String num_2) {
		if (StringUtils.isBlank(num_1)) return round(num_2);
		if (StringUtils.isBlank(num_2)) return round(num_1);
		BigDecimal min = null;
		//需要比对的第一个数字
		BigDecimal compare1 = new BigDecimal(StringUtils.trimToEmpty(num_1));
		//需要比对的第二个数字
		BigDecimal compare2 = new BigDecimal(StringUtils.trimToEmpty(num_2));
		min = compare1.min(compare2);
		return min.toString();
	}
	
	/**   
	* 精确对比两个数字   
	* @ param num_1 需要被对比的第一个数
	* @ param num_2 需要被对比的第二个数
	* @ return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
	*/    
    public static int compareTo(String num_1, String num_2) {
    	if (StringUtils.isBlank(num_1) && StringUtils.isBlank(num_2)) return 0;
    	if (StringUtils.isBlank(num_1)) return -1;
		if (StringUtils.isBlank(num_2)) return 1;
    	//需要比对的第一个数字
	    BigDecimal compare1 = new BigDecimal(StringUtils.trimToEmpty(num_1));
	    //需要比对的二个数字
	    BigDecimal compare2 = new BigDecimal(StringUtils.trimToEmpty(num_2));
	    return compare1.compareTo(compare2);
	}
    
    public static int compareTo(BigDecimal num_1, BigDecimal num_2) {
	    return num_1.compareTo(num_2);
	}
    
    /**
     * 根据传入的参数计算出百分比
     * @ param molecular 分子
     * @ param denominator 分母
     * @ return 处理后的百分比值
     */
    public static String calPercentage(String molecular, String denominator) {
    	if (StringUtils.isBlank(molecular) || StringUtils.isBlank(denominator)) return "0.00%";
    	return multiplicate(divide(molecular, denominator, scaleOfDivide), "100");
    }
    
    /**
     * 提供数字的格式化样式,统一为 XX.00
     * @ param source
     * @ return 格式化后的数字
     */
    public static String numberFormat(Object source) {
    	if (source == null) return null;
    	DecimalFormat decimalFormat = new DecimalFormat("#.00");
    	decimalFormat.setRoundingMode(RoundingMode.FLOOR);
    	return decimalFormat.format(source);
    }

	/**
	 * 把天数转为非自然月
	 * param iTerm 总天数
	 */

	public static Map<String,String> transferArray(int iTerm) {
		int month = iTerm/30;
		int day = iTerm%30;
		Map<String,String> mParam = new HashMap<String,String>();
		if(day > 15) {
			month++;
		}
		for (int i = 1; i <= month; i++) {
			if(i == month) {
				mParam.put(String.valueOf(i), substract(String.valueOf(iTerm), multiplicate(substract(String.valueOf(month), "1", 0), "30", 0), 0));
				break;
			}
			mParam.put(String.valueOf(i), "30");
		}
		return mParam;
	}


    /**
     * --------------------------------------------------
     */
    /**
     * 精确相加返回 BigDecimal
     * @param num_1
     * @param num_2
     * @return
     */
    public static BigDecimal add(Double num_1, Double num_2) {

        //summand为被加数
        BigDecimal summmand = new BigDecimal(num_1);
        //addend为加数
        BigDecimal addend = new BigDecimal(num_2);

        BigDecimal result = summmand.add(addend);

        return result.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 精确相减返回BigDecimal
     * @param num_1
     * @param num_2
     * @return
     */
    public static BigDecimal subtract(Double num_1,Double num_2){
        BigDecimal minuend = new BigDecimal(num_1);
        //subtrahend为减数
        BigDecimal subtrahend = new BigDecimal(num_2);
        BigDecimal result = minuend.subtract(subtrahend);
        return result.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 精确乘法运算BigDecimal
     * @param num_1
     * @param num_2
     * @return
     */
    public static BigDecimal multiply(Double num_1,Double num_2){
        BigDecimal multiplicand = new BigDecimal(num_1);
        //multiplier为乘数
        BigDecimal multiplier = new BigDecimal(num_2);

        BigDecimal result = multiplicand.multiply(multiplier);
        return result.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 精确除法返回BigDecimal
     * @param num_1
     * @param num_2
     * @return
     */
    public static BigDecimal divide(Double num_1,Double num_2){
        BigDecimal dividend = new BigDecimal(num_1);
        //divisor除数
        BigDecimal divisor = new BigDecimal(num_2);

        BigDecimal result = dividend.divide(divisor,scaleOfDivide, BigDecimal.ROUND_FLOOR);
        return result.setScale(scale,BigDecimal.ROUND_FLOOR);
    }


    /**
     * 根据经纬度获取距离
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    /**
     * 判断是否是整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是正整数
     * @param str
     * @return
     */
    public static boolean isPositiveInteger(String str) {
        if(!StringUtil.isEmpty(str)&&isInteger(str)&&Integer.valueOf(str)>0){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Integer t=1;
        Integer t1=1;
        System.out.println( t==t1);
        /*System.out.print(add(num_1,num_2));*/
        /*System.out.print(subtract(num_1,num_2));*/
        /*System.out.print(multiply(num_1,num_2));*/
        /*System.out.print(divide(num_1,num_2,2));*/
        /*System.out.print(divide(num_1,num_2));*/
    }
}

