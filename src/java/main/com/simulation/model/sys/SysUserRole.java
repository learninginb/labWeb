package com.simulation.model.sys;

import java.util.Date;
import java.io.Serializable;

public class SysUserRole implements Serializable{

	private static final long serialVersionUID = 1L;

 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 用户id
 	*/
   	private Integer userId;
 	/**
 	* 角色id
 	*/
   	private Integer roleId;


   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
	}

   	public void setUserId(Integer userId){
   		this.userId = userId;
   	}

   	public Integer getUserId(){
		return userId;
	}

   	public void setRoleId(Integer roleId){
   		this.roleId = roleId;
   	}

   	public Integer getRoleId(){
		return roleId;
	}
}

