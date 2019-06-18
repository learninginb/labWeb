package com.simulation.vo.sys;

import com.simulation.model.sys.SysUser;

public class SysUserVo extends SysUser {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_USER_NAME = "姓名";
    public static final String ALIAS_ACCOUNT = "账号";
    public static final String ALIAS_EMAIL = "电子邮箱";
    public static final String ALIAS_MOBILE_PHONE = "手机号码";
    public static final String ALIAS_PASSWORD = "密码";
    public static final String ALIAS_REG_TIME = "添加时间";
    public static final String ALIAS_REG_IP = "添加ip";
    public static final String ALIAS_LAST_LOGIN_TIME = "最后登录时间";
    public static final String ALIAS_LAST_LOGIN_ERR_TIME = "最后错误登录时间";
    public static final String ALIAS_LAST_LOGIN_IP = "最后登录ip";
    public static final String ALIAS_ID_NUMBER = "身份证号";
    public static final String ALIAS_LOGIN_ERR_TIMES = "登录累计错误次数";
    public static final String ALIAS_USER_TYPE = "用户类型(1-超级管理员 2-管理员 3-发布方 4-超级管理员)";
    public static final String ALIAS_STATUS = "状态(1启用 2禁用 3 删除)";
    
    /**
     * 检查类型_账户名称
     */
    public static final String CHECK_TYPE_ACCOUNT="1";
    /**
     * 检查类型_电子邮箱
     */
    public static final String CHECK_TYPE_EMAIL="2";
    /**
     * 检查类型_手机号码
     */
    public static final String CHECK_TYPE_MOBILEPHONE="3";
    /**
     * 检查类型_身份证号
     */
    public static final String CHECK_TYPE_IDNUMBER="4";
	
}
