package com.simulation.vo.sys;

import com.simulation.model.sys.SysUserRole;

public class SysUserRoleVo extends SysUserRole {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_USER_ID = "用户id";
    public static final String ALIAS_ROLE_ID = "角色id";
    
    /**
     * 角色名称
     */
    private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
    
	
}
