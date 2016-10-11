package com.ecmoho.common.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class CommonUtils {

	private static final String CHAR_NUMBER_LINE_REG = "[a-zA-Z0-9_\\.]+";
	public static boolean matcher(String reg, String string) {
		boolean tem = false;
		if (reg == null || string == null) {
			return tem;
		}
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);
		tem = matcher.matches();
		return tem;
	}

	/**
	 * 判断是否是数据库列名命名规则
	 * @param string
	 * @return
	 */
	public static boolean isDateBaseColumn(String string) {
		return matcher(CHAR_NUMBER_LINE_REG,string);
	}

	public static String parseHexStringFromBytes(final byte[] text) {
		StringBuffer buff = new StringBuffer(0);
		for (int i = 0; i < text.length; i++) {
			byte _byte = text[i];
			byte _bytel = (byte) (_byte & 0x000f);
			byte _byteh = (byte) (_byte & 0x00f0);
			getHexString(buff, (byte) ((_byteh >> 4) & 0x000f));
			getHexString(buff, _bytel);
		}
		return buff.toString();
	}
	
	private static void getHexString(final StringBuffer buffer, final byte value) {
		if (value - 9 > 0) {
			int index = value - 9;
			switch (index) {
			case 1:
				buffer.append("A");
				break;
			case 2:
				buffer.append("B");
				break;
			case 3:
				buffer.append("C");
				break;
			case 4:
				buffer.append("D");
				break;
			case 5:
				buffer.append("E");
				break;
			case 6:
				buffer.append("F");
				break;
			default:
				break;
			}
		} else {
			buffer.append(String.valueOf(value));
		}
	}
	
	public static byte[] parseBytesByHexString(final String hexString) {
		if (hexString == null || hexString.length() == 0
				|| hexString.equals("")) {
			return new byte[0];
		}
		if (hexString.length() % 2 != 0) {
			throw new IllegalArgumentException(
					"hexString length must be an even number!");
		}
		StringBuffer sb = new StringBuffer(hexString);
		StringBuffer sb1 = new StringBuffer(2);
		int n = hexString.length() / 2;
		byte[] bytes = new byte[n];
		for (int i = 0; i < n; i++) {
			if (sb1.length() > 1) {
				sb1.deleteCharAt(0);
				sb1.deleteCharAt(0);
			}
			sb1.append(sb.charAt(0));
			sb1.append(sb.charAt(1));
			sb.deleteCharAt(0);
			sb.deleteCharAt(0);
			bytes[i] = (byte) Integer.parseInt(sb1.toString(), 16);
		}
		return bytes;
	}
	
	public static Integer convert2int(Object o){
		if(o==null) return 0;
		else return (Integer)o;
	}

	/**
	 * 随机数
	 * @param numberFlag  是否纯数字
	 * @param length 多少位
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length){
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr.toUpperCase();
	}


}
