package com.simulation.vo.sys;

import com.simulation.model.sys.SysResource;

public class SysResourceVo extends SysResource {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_PARENT_ID = "上级id";
    public static final String ALIAS_RESOURCE_NAME = "资源名称";
    public static final String ALIAS_RESOURCE_PATH = "资源路径";
    public static final String ALIAS_RESOURCE_ICON = "资源图标";
    public static final String ALIAS_LEVEL = "层级（1-系统,2-模块, 3-菜单，4-按钮）";
    public static final String ALIAS_RESOURCE_DESC = "资源描述";
    public static final String ALIAS_PERMISSION_STR = "权限字符串";
    public static final String ALIAS_ORDER_NUM = "排序";
    public static final String ALIAS_IS_ENABLE = "是否启用";
    public static final String ALIAS_CREATE_USER_ID = "创建人id";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_MODIFY_USER_ID = "修改人id";
    public static final String ALIAS_MODIFY_TIME = "修改时间";
    
    private String parentName;
	private String roleId;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceLevelStr(){
		String level = "";
		if(this.getLevel() != null){
			switch (this.getLevel()) {
			case 0:
				level = "资源根目录";
				break;
	        case 1:
				level = "一级菜单";
				break;
			case 2:
				level = "二级菜单";
				break;
			case 3:
				level = "三级菜单";
				break;
			case 4:
				level = "操作按钮";
				break;
			default:
				break;
			}
		}
		return level;
	}
	
	public String getIsEnableStr(){
		String enable = "";
		if(this.getIsEnable() != null){
			switch (this.getIsEnable()) {
			case 0:
				enable = "禁用";
				break;
	        case 1:
	        	enable = "启用";
				break;
			default:
				break;
			}
		}
		return enable;
	}
	
}
