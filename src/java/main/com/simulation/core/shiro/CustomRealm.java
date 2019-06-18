package com.simulation.core.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.simulation.model.sys.SysResource;
import com.simulation.model.sys.SysUser;
import com.simulation.service.sys.SysResourceService;
import com.simulation.service.sys.SysRoleService;
import com.simulation.service.sys.SysUserService;
import com.simulation.vo.sys.SysUserActiveVo;

/**
* Description:自定义realm 
* @ClassName: CustomRealm 
* @author Jalf
* @since 2016年5月31日 上午9:35:31 
* Copyright  983150316 All right reserved.
 */
public class CustomRealm extends AuthorizingRealm{
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysResourceService sysResourceService;
	
	@Autowired
	private SysRoleService sysRoleService;
	

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		CaptchaUsernamePasswordToken captchaUsernamePasswordToken=(CaptchaUsernamePasswordToken)token;
		String userCode = (String) captchaUsernamePasswordToken.getUsername();
		//验证验证码是否正确
		String captcha=null;
//		Object obj_captcha=SecurityUtils.getSubject().getSession().getAttribute("CodeKey");
//		if(obj_captcha instanceof String){
//			captcha = (String)obj_captcha;
//		}
//		String passKey = captchaUsernamePasswordToken.getCaptcha();
//		if(captcha != null &&!captcha.equalsIgnoreCase(passKey)){
//			SecurityUtils.getSubject().getSession().setAttribute("CodeKey",null);
//			throw new IncorrectCaptchaException("验证码错误！");
//		}else if (null==captcha||null==captcha) {
//			SecurityUtils.getSubject().getSession().setAttribute("CodeKey",null);
//			throw new IncorrectCaptchaException("验证码为空");
//		}
		SysUserActiveVo sysUserActive = null;
		SysUser sysUser = sysUserService.findSingleUser(userCode);
		//验证用户名
		if(null == sysUser){
			return null; //shiro 会自动抛出账号不存在的异常
		}
		//验证是否锁定
		if (sysUser.isLocked()) {//抛出帐号锁定的异常,配置ip地址可以放行
			throw new LockedAccountException();  
			
		}
		sysUserActive = new SysUserActiveVo();
		BeanUtils.copyProperties(sysUser, sysUserActive);
		sysUserActive.setLoginName(userCode);
		String password = sysUser.getPassword();
		
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUserActive, password, this.getName());
		return simpleAuthenticationInfo;
	}
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserActiveVo sysUser =  (SysUserActiveVo) principals.getPrimaryPrincipal();
		List<String> permissions = new ArrayList<String>();
		if(null != sysUser){
			List<SysResource> resources = sysResourceService.findAllByUserId(sysUser.getId());
			for(SysResource resource : resources){
				String permission = resource.getPermissionStr();
				if(null != permission && ! "".equals(permission.trim())){
					permissions.add(permission);
					
				}
			}
		}
		List<String> roleList = sysRoleService.findRoleTypesByUserId(sysUser.getId());
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		simpleAuthorizationInfo.addRoles(roleList);//角色类型
		
		return simpleAuthorizationInfo;
	}

	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}
	
	//清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

	
}
