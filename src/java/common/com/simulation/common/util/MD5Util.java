package com.simulation.common.util;

import java.security.MessageDigest; 

public class MD5Util {
	
	/**
	 * 
	* @Title: string2MD5 
	* @Description: MD5加码 生成32位md5码  
	* @param @param inStr
	* @param @return    设定文件 
	* @return String    返回类型 
	* @author Administrator
	* @throws
	 */
	public static String string2MD5(String inStr){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			
			char[] charArray = inStr.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			
			for (int i = 0; i < charArray.length; i++){
				byteArray[i] = (byte) charArray[i];
			}
			
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++){
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16){
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			
			return hexValue.toString();
		} catch (Exception e) {
 			e.printStackTrace();
 			return "";
		}						
	}

	/** 
	 * 加密解密算法 执行一次加密，两次解密 
	 */
	public static String convertMD5(String inStr){
		char[] a = inStr.toCharArray(); 
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		
		return new String(a);
	}
	
	public static String MD5ToString(String inStr){
		return convertMD5(convertMD5(inStr));
	}
 
	 public static void main(String[] args) {  
			String s = new String("123");  
		 	System.out.println("原始：" + s);  
		 	System.out.println("MD5后：" + string2MD5(s));  
		 	System.out.println("加密的：" + convertMD5(s));  
		 	System.out.println("解密的：" + MD5ToString(s));  
	}  

}
