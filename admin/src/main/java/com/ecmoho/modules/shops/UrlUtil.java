package com.ecmoho.modules.shops;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class UrlUtil {
	   
	   //获取抓取内容
	   public static String getUrlString(String urlStr,String requestType) {
			URL url = null;
			HttpURLConnection http = null;
			String result = "";
			try {
				Random random = new Random();
				// 抓取频率，休息一段时间
				try {
					int rest=random.nextInt(5) * 1000;
					Thread.sleep(rest);
					System.out.println("休息时间："+rest+"s");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				url = new URL(urlStr);
				http = (HttpURLConnection) url.openConnection();
				http.setUseCaches(false);
				http.setConnectTimeout(50000);// 连接超时时长
				http.setReadTimeout(50000);// 读取超时时长
				http.setRequestMethod(requestType);
				http.setDoOutput(true);
				http.setDoInput(true);

				// http.setDoOutput(true);
				http.connect();
				if (http.getResponseCode() == 200) {
					// String set_cookie =
					// http.getFirstHeader("Set-Cookie").getValue();
					BufferedReader in = new BufferedReader(new InputStreamReader(
							http.getInputStream(), "utf-8"));
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						result += inputLine;
					}
					System.out.println(result);
					in.close();
				} else {
					
					//System.out.println("抓取失败，当前cookie失效");
				}
			} catch (Exception e) {
				//System.out.println("err");
			} finally {
				if (http != null)
					http.disconnect();
			}
			return result;
		}
		
}
