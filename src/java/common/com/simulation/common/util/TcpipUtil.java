package com.simulation.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * tcp/ip 工具类
* Description: 
* @ClassName: TcpipUtil 
* @author Jalf
* @since 2016年5月31日 上午9:20:28 
* Copyright  foxtail All right reserved.
 */
public class TcpipUtil {

	/**
	* Description:获得ip地址
	* @Title: getIpAddr  
	* @author Jalf
	* @since 2016年5月31日 上午9:20:45
	* @param request
	* @return
	* Copyright  foxtail All right reserved.
	 */
	  public static String getIpAddr(HttpServletRequest request)
	  {
	    String ip = request.getHeader("x-forwarded-for");
	    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
	      ip = request.getHeader("Proxy-Client-IP");
	    }
	    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
	      ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
	      ip = request.getRemoteAddr();
	    }
	    return ip;
	  }
	  
	 /**
	 * Description:获得mac地址
	 * @Title: getMacIp  
	 * @author Jalf
	 * @since 2016年5月31日 上午9:21:05
	 * @param sip
	 * @return
	 * @throws Exception
	 * Copyright  foxtail All right reserved.
	  */
	  public static String getMacIp(String sip) throws Exception{
		  String smac = "";
		  UdpGetClientMacAddr umac = new UdpGetClientMacAddr(sip);
		  smac = umac.GetRemoteMacAddr();
		  return smac;
	  }
}
