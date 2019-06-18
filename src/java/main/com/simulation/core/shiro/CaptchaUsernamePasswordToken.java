package com.simulation.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
* Description: shiro扩充验证码
* @ClassName: CaptchaUsernamePasswordToken 
* @author Jalf
* @since 2016年5月31日 上午9:17:19 
* Copyright  983150316 All right reserved.
 */
public class CaptchaUsernamePasswordToken extends  UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 验证码
	 */
	private String captcha;
	/**
	 * ip地址
	 */
	private String ipAddr;
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getCaptcha() {
		return captcha;
	}
	
	public CaptchaUsernamePasswordToken(){
		super();
	}
	
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	public CaptchaUsernamePasswordToken(String username,String password,boolean rememberMe,String host,String captcha,String ipAddr) {        
		super(username, password, rememberMe, host);
	    this.captcha = captcha;
	    this.ipAddr=ipAddr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
