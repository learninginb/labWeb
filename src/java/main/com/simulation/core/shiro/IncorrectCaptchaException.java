package com.simulation.core.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
* Description: 验证码错误异常类
* @ClassName: IncorrectCaptchaException 
* @author Jalf
* @since 2016年5月31日 上午9:34:51 
* Copyright  983150316 All right reserved.
 */
public class IncorrectCaptchaException extends  AuthenticationException {

	private static final long serialVersionUID = 1L;

	public IncorrectCaptchaException(){
		super();
	}

   public IncorrectCaptchaException(String message,Throwable cause){
	   super(message,cause);
   }

   public IncorrectCaptchaException(String message){
	   super(message);
   }

   public IncorrectCaptchaException(Throwable cause){
	   super(cause);
   }
}
