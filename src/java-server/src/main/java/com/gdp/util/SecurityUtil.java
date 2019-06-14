package com.gdp.util;

import java.security.MessageDigest;

public class SecurityUtil {
	
	/**
	 * MD5 加密
	 * 
	 * @param source	原始密码
	 * @param salt		盐值
	 * @param iterations	加密次数
	 * @return
	 */
	public static String getPassword(String source, String salt, Integer iterations) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt.getBytes("UTF-8"));
			byte [] str = source.getBytes("UTF-8");
			byte[] encryptStr;
			do {
				encryptStr = md.digest(str);
				str = encryptStr;
				iterations--;
			}while(iterations > 0);
			
			for(byte b : encryptStr) {
	            String temp = Integer.toHexString(b & 0xff);
	            if(temp.length() == 1) {
	                temp = "0" + temp;
	            }
	            result = result + temp;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return result;
	}

	public static void main(String[] args) {
		String string = getPassword("123456", "admin", 1024);
		
		System.out.println("加密结果：" + string);
	}
}
