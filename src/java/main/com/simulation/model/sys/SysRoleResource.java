package com.simulation.model.sys;

import java.util.Date;
import java.io.Serializable;

public class SysRoleResource implements Serializable{

	private static final long serialVersionUID = 1L;

 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 角色id
 	*/
   	private Integer roleId;
 	/**
 	* 资源id
 	*/
   	private Integer resourceId;


   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
	}

   	public void setRoleId(Integer roleId){
   		this.roleId = roleId;
   	}

   	public Integer getRoleId(){
		return roleId;
	}

   	public void setResourceId(Integer resourceId){
   		this.resourceId = resourceId;
   	}

   	public Integer getResourceId(){
		return resourceId;
	}
}

