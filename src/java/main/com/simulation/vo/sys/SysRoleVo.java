package com.simulation.vo.sys;

import com.simulation.model.sys.SysRole;

public class SysRoleVo extends SysRole {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_ROLE_NAME = "角色名称";
    public static final String ALIAS_ROLE_TYPE = "角色类型(1-超级管理员 2-管理员 3-发布方 4-超级管理员)";
    public static final String ALIAS_ROLE_STATUS = "状态(1-启用 2-禁用 3-删除)";
    public static final String ALIAS_CREATE_USER_ID = "创建人id";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_MODIFY_USER_ID = "修改人id";
    public static final String ALIAS_MODIFY_TIME = "修改时间";
    public static final String ALIAS_ROLE_DESC = "角色描述";
	
}
