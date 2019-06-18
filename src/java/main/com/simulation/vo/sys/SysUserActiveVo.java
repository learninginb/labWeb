package com.simulation.vo.sys;

import java.io.Serializable;
import java.util.List;

import com.simulation.model.sys.SysUser;


/**
 * 
* Description:登录用户信息
* @ClassName: SysUserActiveVo 
* @author Jalf
* @since 2016年5月31日 上午9:26:56 
* Copyright  https://shop106571874.taobao.com All right reserved.
 */
public class SysUserActiveVo extends SysUser implements Serializable{

	private static final long serialVersionUID = 1L;
	//用户登录名，可以是账号，邮箱，手机号码
	private String loginName;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
