package com.simulation.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* Description:日期工具类 
* @ClassName: DateUtils 
* @author Jalf
* @since 2016年6月2日 下午2:44:02 
* Copyright  foxtail All right reserved.
 */
public class DateUtils {
	public static final String Y ="yyyy";
	
	public static final String YMD = "yyyyMMdd";
	
	public static final String YMDHMS = "yyyyMMdd HH:mm:ss";
	
	public static final String Y_M_D = "yyyy-MM-dd";
	
	public static final String Y_M_DHMS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YMDHMSS="yyyyMMddhhmmssSSS";
	
	/**
	* Description:根据毫秒数转换为时间    
	* @Title: getTime  
	* @author Jalf
	* @since 2016年6月2日 下午2:45:20
	* @param millseconds
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getTime(Long millseconds) {
		String time = "";
		if (millseconds == null) {
			return "";
		}
		int days = (int) millseconds.longValue() / 1000 / 60 / 60 / 24;
		if (days > 0) {
			time = time + days + "天";
		}
		long hourMillseconds = millseconds.longValue() - days * 1000 * 60 * 60 * 24;
		int hours = (int) hourMillseconds / 1000 / 60 / 60;
		if (hours > 0) {
			time = time + hours + "小时";
		}
		long minuteMillseconds = millseconds.longValue() - days * 1000 * 60 * 60 * 24 - hours * 1000 * 60 * 60;

		int minutes = (int) minuteMillseconds / 1000 / 60;
		if (minutes > 0) {
			time = time + minutes + "分钟";
		}
		
		long secondMillseconds = millseconds.longValue() - days * 1000 * 60 * 60 * 24 - hours * 1000 * 60 * 60 - minutes * 1000 * 60;
		int second = (int) secondMillseconds / 1000;
		if (second > 0) {
			time = time + second + "秒";
		}
		return time;
	}
	
	/**
	* Description:获取当前时间字符串    
	* @Title: getCurrTimeForString  
	* @author Jalf
	* @since 2016年6月2日 下午2:46:24
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getCurrTimeForString(){
		return formatDate(new Date(), Y_M_DHMS);
	}
	
	/**
	* Description:获取当前日期字符串    
	* @Title: getCurrDateForString  
	* @author Jalf
	* @since 2016年6月2日 下午2:46:47
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getCurrDateForString(){
		return formatDate(new Date(), YMD);
	}
	
	/**
	* Description:格式化日期成字符串    
	* @Title: formatDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:47:29
	* @param date
	* @param pattern
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String formatDate(Date date,String pattern)
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	* Description:将格式日期的字符串解析成Date    
	* @Title: parseDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:47:55
	* @param dateStr
	* @param pattern
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date parseDate(String dateStr,String pattern)
	{
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* Description:计算两个时间的时间差    
	* @Title: getIntervalForTwoDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:48:43
	* @param startDate
	* @param endDate
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getIntervalForTwoDate(Date startDate,Date endDate)
	{
		if(null!=startDate&&null!=endDate)
		{
			long t1 = startDate.getTime();
			long t2 = endDate.getTime();
			int hours=(int) ((t2 - t1)/3600000);
	        int minutes=(int) (((t2 - t1)/1000-hours*3600)/60);
	        int second=(int) ((t2 - t1)/1000-hours*3600-minutes*60);
	        return ""+hours+"小时"+minutes+"分"+second+"秒";
		}
		return "";
	}

	/**
	* Description:按calendar的类型获取时间数    
	* @Title: getDateField  
	* @author Jalf
	* @since 2016年6月2日 下午2:49:02
	* @param date
	* @param field
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static int getDateField(Date date,int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * 计算两个时间的相差年数
	* Description:    
	* @Title: getYearsBetweenDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:49:41
	* @param begin
	* @param end
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static int getYearsBetweenDate(Date begin,Date end){
		int bYear = getDateField(begin,Calendar.YEAR);
		int eYear = getDateField(end,Calendar.YEAR);
		return eYear - bYear;
	}
	
	/**
	* Description:计算两个时间相差的天数    
	* @Title: getDaysBetweenDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:50:09
	* @param begin
	* @param end
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static int getDaysBetweenDate(Date begin,Date end){
		int bDay = getDateField(begin,Calendar.DAY_OF_YEAR);
		int eDay = getDateField(end,Calendar.DAY_OF_YEAR);
		return eDay - bDay;
	}

	/**
	* Description:获取date年后(前)的amount年的第一天的开始时间    
	* @Title: getSpecficYearStart  
	* @author Jalf
	* @since 2016年6月2日 下午2:50:29
	* @param date
	* @param amount
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getSpecficYearStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	* Description:获取date年后的amount年的最后一天的终止时间    
	* @Title: getSpecficYearEnd  
	* @author Jalf
	* @since 2016年6月2日 下午2:50:55
	* @param date
	* @param amount
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getSpecficYearEnd(Date date,int amount) {
		Date temp = getStartDate(getSpecficYearStart(date,amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	* Description:获取date月后的amount月的第一天的开始时间    
	* @Title: getSpecficMonthStart  
	* @author Jalf
	* @since 2016年6月2日 下午2:51:11
	* @param date
	* @param amount
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getSpecficMonthStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	* Description:获取当前自然月后的amount月的最后一天的终止时间    
	* @Title: getSpecficMonthEnd  
	* @author Jalf
	* @since 2016年6月2日 下午2:51:46
	* @param date
	* @param amount
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getSpecficMonthEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date,amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 
	* Description:获取date周后的第amount周的开始时间（这里星期一为一周的开始）    
	* @Title: getSpecficWeekStart  
	* @author Jalf
	* @since 2016年6月2日 下午2:52:06
	* @param date
	* @param amount
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getSpecficWeekStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	* Description:获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）    
	* @Title: getSpecficWeekEnd  
	* @author Jalf
	* @since 2016年6月2日 下午2:55:17
	* @param date
	* @param amount
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getSpecficWeekEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}

	/**
	* Description:得到指定日期的一天的的最后时刻23:59:59    
	* @Title: getFinallyDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:54:57
	* @param date
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getFinallyDate(Date date) {
		String temp = formatDate(date,YMD);
		temp += " 23:59:59";
		return parseDate(temp,YMDHMS);
	}

	/**
	* Description:得到指定日期的一天的开始时刻00:00:00    
	* @Title: getStartDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:54:27
	* @param date
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getStartDate(Date date) {
		String temp = formatDate(date,YMD);
		temp += " 00:00:00";
		return parseDate(temp, YMDHMS);
	}

	/**
	* Description:拿到当前月的最后一天    
	* @Title: getLastDayOfMonth  
	* @author Jalf
	* @since 2016年6月2日 下午2:53:56
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Date getLastDayOfMonth(){
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
		 return calendar.getTime();
	}
	
	/**
	* Description:判断传入的日期是否是周末    
	* @Title: isWeek  
	* @author Jalf
	* @since 2016年6月2日 下午2:53:35
	* @param currentDate
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean isWeek(Date currentDate) {  
		int currentDay = 9  ;  
		if(null!=currentDate){
 			currentDay = currentDate.getDay();
		}  
		if(currentDay==0||currentDay==6)  {  
			return true ;  
		}  
		return false; 
	} 
	
	/**
	 * 将未指定格式的日期字符串转化成
	* Description:    
	* @Title: parseStringToDate  
	* @author Jalf
	* @since 2016年6月2日 下午2:53:24
	* @param date
	* @return
	* Copyright  foxtail All right reserved.
	 */
    public static Date parseStringToDate(String date) {  
        Date result = null;  
        String parse = date;  
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");  
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");  
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");  
        DateFormat format = new SimpleDateFormat(parse);  
        try {
			result = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        return result;  
    } 

    public  static long getNextTime(int timeUnit, int interval, long timeMill)
      {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timeMill);
        switch (timeUnit) {
        case 0:
          ca.add(13, interval);
          break;
        case 1:
          ca.add(12, interval);
          break;
        case 2:
          ca.add(10, interval);
          break;
        case 3:
          ca.add(5, interval);
          break;
        case 4:
          ca.add(2, interval);
          break;
        default:
          return 0L;
        }
        return ca.getTimeInMillis();
      }
    
    public static Date getMillis(Date startTime,int timeMill){
		 Calendar cal = Calendar.getInstance();
		 cal.setTimeInMillis(startTime.getTime());
		 cal.add(Calendar.MINUTE,timeMill);
		return cal.getTime();
    }
    
    /**
    * Description:获得指定日期的几天     
    * @Title: getSpecifiedDayAfter  
    * @author Jalf
    * @since 2016年6月2日 下午2:52:57
    * @param specifiedDate
    * @param number
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static Date getSpecifiedDayAfter(Date specifiedDate,int number){ 
		Calendar c = Calendar.getInstance();     
		c.setTime(specifiedDate); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+number); 
		
		return c.getTime(); 
    } 
 
    /**
    * Description:两个时间相差的秒数    
    * @Title: getSecondDiff  
    * @author Jalf
    * @since 2016年6月2日 下午2:52:37
    * @param startTime
    * @param endTime
    * @return
    * Copyright  foxtail All right reserved.
     */
    public static Long getSecondDiff(Date startTime,Date endTime){
        long start = startTime.getTime();
        long end = endTime.getTime();
        return end - start;

    }
    
    public static void main(String[] args){
    
    }
    
    
   
}
