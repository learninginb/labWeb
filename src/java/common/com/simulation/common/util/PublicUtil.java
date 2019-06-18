package com.simulation.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
* Description:通用检查工具类 
* @ClassName: PublicUtil 
* @author Jalf
* @since 2016年6月2日 下午2:15:29 
* Copyright  foxtail All right reserved.
 */
public class PublicUtil {
	
	/**
	* Description:检查邮件是否合法    
	* @Title: checkEmail  
	* @author Jalf
	* @since 2016年6月2日 下午2:16:53
	* @param email
	* @return
	* Copyright  foxtail All right reserved.
	 */
    public static boolean checkEmail (String email) {
        Pattern p = Pattern.compile("^[_\\.0-9a-z-]+@([0-9a-z][0-9a-z-]+\\.)+[a-z]{2,3}$");
        return p.matcher(email).matches();
    }

    /**
    * Description:检查是否是空的list
    * @Title: checkEmptyList  
    * @author Jalf
    * @since 2016年6月2日 下午2:17:30
    * @param list
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static boolean checkEmptyList (List list) {
        if ((list == null) || (list.size() == 0)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
    * Description:检查字符串是否为空    
    * @Title: checkEmptyString  
    * @author Jalf
    * @since 2016年6月2日 下午2:18:56
    * @param str
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static boolean checkEmptyString (String str) {
        if(((str == null) || (str.trim().length() == 0))||"null".equals(str)) {
            return true;
        }else{
            return false;
        }
    }

    /**
    * Description:转换字符串编码    
    * @Title: convertStringEncode  
    * @author Jalf
    * @since 2016年6月2日 下午2:19:30
    * @param str
    * @param encode
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static String convertStringEncode (String str, String encode) {
        try {
            byte bytes[] = str.getBytes(encode);    // "ISO-8859-1");
            String UTFtekst = new String(bytes);
            return UTFtekst;
        }
        catch (UnsupportedEncodingException w) {
            System.out.println("cannot get this charset");
            return "error";
        }
    }

    /**
    * Description:格式化float型数据    
    * @Title: formatFloat  
    * @author Jalf
    * @since 2016年6月2日 下午2:19:52
    * @param source
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static String formatFloat (float source) {
        return new DecimalFormat("######0.00").format(source);
    }
    
    /**
    * Description:数字前面补零    
    * @Title: leftFillPosition  
    * @author Jalf
    * @since 2016年6月2日 下午2:21:49
    * @param count
    * @param data
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static String leftFillPosition(int count,int data){
    	//得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(count);
        //设置最小整数位数   
        nf.setMinimumIntegerDigits(count);
    	return nf.format(data);
    }
    
}
