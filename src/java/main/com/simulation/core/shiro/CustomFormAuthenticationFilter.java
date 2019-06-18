package com.simulation.core.shiro;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.simulation.common.util.TcpipUtil;
import com.simulation.model.sys.SysUser;
import com.simulation.service.sys.SysUserService;
import com.simulation.vo.sys.SysUserActiveVo;


/**
 * 
* Description: 自定义FormAuthenticationFilter
* @ClassName: CustomFormAuthenticationFilter 
* @author Jalf
* @since 2016年5月31日 上午9:23:40 
* Copyright  983150316 All right reserved.
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	
	@Autowired
	private SysUserService sysUserService;
	
	private final static Logger log = Logger.getLogger(CustomFormAuthenticationFilter.class);

	//重写认证方法，在认证前先进行验证码匹配
//	@Override
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpSession session = httpServletRequest.getSession();
//		String validateCode = (String) session.getAttribute("CodeKey");
//		String randomcode = httpServletRequest.getParameter("passKey");
//		if(null== validateCode ){
//			return super.onAccessDenied(request, response);
//		}
//		log.debug("正确的验证码是："+validateCode +",你输入的验证码是："+randomcode);
//		if (randomcode !=null && !randomcode.equals(validateCode)) {
//			httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
//			/**如果此时失败了，则应当把验证码给清空了（或者随机产生验证码），防止暴力破解软件重复使用验证码，
//			现在的做法是只有在页面刷新的时候才会有验证码，显然不妥当**/
//			session.setAttribute("CodeKey", null);
//			return true;
//		}
//		return super.onAccessDenied(request, response);
//	}
	
	
	//登录失败，异常抛出
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String className = e.getClass().getName();
		if(e!=null && !UnknownAccountException.class.getName().equals(className)
				&&!IncorrectCredentialsException.class.getName().equals(className)
				&&!IncorrectCaptchaException.class.getName().equals(className)  //验证码错误
				&&!LockedAccountException.class.getName().equals(className)){  //用户被锁定
			e.printStackTrace(); //非验证异常抛出 
		}
		return super.onLoginFailure(token, e, request, response);
	}


	//重写认证通过后的页面跳转，shiro会默认跳转到上一次请求的页面，不适用于iframe的框架
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		//aop不能拦截filter的内容，记录登录认证的日志
		SysUserActiveVo sysUserActive = ShiroUser.getUser();
		//更新用户登录时间
		SysUser user = new SysUser();
		user.setId(sysUserActive.getId());
		user.setLastLoginTime(new Date());
		
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		String ipAddr = TcpipUtil.getIpAddr(httpServletRequest);
		user.setLastLoginIp(ipAddr);
		user.setLoginErrTimes(Integer.valueOf(0));
		//清空登录出错信息
		user.setStatus(SysUser.STATUS_ENABLE);
		sysUserService.updateByPrimaryKeySelective(user);
		//认证通过后的跳转地址
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}
	
	
	protected String getCaptcha(ServletRequest request){
		return WebUtils.getCleanParam(request, "passKey");
	}

	//重写createToken方法
	@Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response){
    	String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String ipAddr="";
		try {
			ipAddr=TcpipUtil.getIpAddr(httpServletRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new CaptchaUsernamePasswordToken(username, password, rememberMe, host,captcha,ipAddr);
   }
}
