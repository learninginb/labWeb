package com.simulation.model.sys;

import java.util.Date;
import java.io.Serializable;

public class SysRole implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 超级管理员角色类型
	 */
	public static final Integer ROLE_SUPER_ADMIN=Integer.valueOf(1);
	/**
	 * 一般管理员角色类型
	 */
	public static final Integer ROLE_ADMIN=Integer.valueOf(2);
	/**
	 * 发布方角色类型
	 */
	public static final Integer ROLE_PUBLISHER=Integer.valueOf(3);
	/**
	 * 普通会员角色
	 */
	public static final Integer ROLE_MEMEBER=Integer.valueOf(4);
	

 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 角色名称
 	*/
   	private String roleName;
 	/**
 	* 角色类型(1-超级管理员 2-管理员 3-发布方 4-超级管理员)
 	*/
   	private Integer roleType;
 	/**
 	* 状态(1-启用 2-禁用 3-删除)
 	*/
   	private Integer roleStatus;
 	/**
 	* 创建人id
 	*/
   	private Integer createUserId;
 	/**
 	* 创建时间
 	*/
   	private Date createTime;
 	/**
 	* 修改人id
 	*/
   	private Integer modifyUserId;
 	/**
 	* 修改时间
 	*/
   	private Date modifyTime;
 	/**
 	* 角色描述
 	*/
   	private String roleDesc;


   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
	}

   	public void setRoleName(String roleName){
   		this.roleName = roleName;
   	}

   	public String getRoleName(){
		return roleName;
	}

   	public void setRoleType(Integer roleType){
   		this.roleType = roleType;
   	}

   	public Integer getRoleType(){
		return roleType;
	}

   	public void setRoleStatus(Integer roleStatus){
   		this.roleStatus = roleStatus;
   	}

   	public Integer getRoleStatus(){
		return roleStatus;
	}

   	public void setCreateUserId(Integer createUserId){
   		this.createUserId = createUserId;
   	}

   	public Integer getCreateUserId(){
		return createUserId;
	}

   	public void setCreateTime(Date createTime){
   		this.createTime = createTime;
   	}

   	public Date getCreateTime(){
		return createTime;
	}

   	public void setModifyUserId(Integer modifyUserId){
   		this.modifyUserId = modifyUserId;
   	}

   	public Integer getModifyUserId(){
		return modifyUserId;
	}

   	public void setModifyTime(Date modifyTime){
   		this.modifyTime = modifyTime;
   	}

   	public Date getModifyTime(){
		return modifyTime;
	}

   	public void setRoleDesc(String roleDesc){
   		this.roleDesc = roleDesc;
   	}

   	public String getRoleDesc(){
		return roleDesc;
	}
}

