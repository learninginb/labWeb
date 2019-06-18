package com.simulation.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
* Description: 拼音工具类，注意以下方法不会识别多音字
* @ClassName: PinyinUtil 
* @author Jalf
* @since 2016年6月2日 下午2:04:25 
* Copyright  foxtail All right reserved.
 */
public class PinyinUtil {

	/**
	* Description:获得中文的第一个字的全拼和非第一个字的首拼    
	* @Title: getPinyinForName  
	* @author Jalf
	* @since 2016年6月2日 下午2:09:25
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getPinyinForName(String str){
		char[] strChar = null;
		strChar = str.toCharArray();
		String[] stcLength = new String[strChar.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat();
		pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String strPinyin = "";
		try {
			for (int i = 0; i < strChar.length; i++) {
				// 判断是否为汉字字符
				// System.out.println(strChar[i]);
				if (Character.toString(strChar[i]).matches("[\\u4E00-\\u9FA5]+")) {
					stcLength = PinyinHelper.toHanyuPinyinStringArray(strChar[i], pinyinOutputFormat);// 将汉字的几种全拼都存到stcLength数组中
					if(i==0){
						strPinyin += stcLength[0]; //姓取全拼
					}else{
						strPinyin += stcLength[0].substring(0, 1);// 取出该汉字全拼的第一种读音并连接到字符串strPinyin后
					}
				} else {
					// 如果不是汉字字符，直接取出字符并连接到字符串strPinyin后
					strPinyin += Character.toString(strChar[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return strPinyin;
	}
	
	/**
	* Description:获得中文的全屏   
	* @Title: getPinYin  
	* @author Jalf
	* @since 2016年6月2日 下午2:12:48
	* @param src
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getPinYin(String src) {  
        char[] t1 = null;  
        t1 = src.toCharArray();  
        // System.out.println(t1.length);  
        String[] t2 = new String[t1.length];  
        // System.out.println(t2.length);  
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断能否为汉字字符  
                // System.out.println(t1[i]);  
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中  
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后  
                } else {  
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后  
                    t4 += Character.toString(t1[i]);  
                }  
            }  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return t4;  
    }  
	
	/**
	 * 获得中文的首字母
	* Description:    
	* @Title: getPinYinHeadChar  
	* @author Jalf
	* @since 2016年6月2日 下午2:14:35
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getPinYinHeadChar(String str) {  
		String convert = "";  
	    for (int j = 0; j < str.length(); j++) {  
	    	char word = str.charAt(j);  
	        // 提取汉字的首字母  
	        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	        if (pinyinArray != null) {  
	        	convert += pinyinArray[0].charAt(0);  
	        }else{  
	        	convert += word;  
	        }  
	    }  
	    return convert;  
	}  
	
	public static void main(String[] args ){
		String pinyin = getPinYin("单田方");
		System.out.println(pinyin);
	}
	
}
