package com.simulation.model.sys;

import java.io.Serializable;


public class SysUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static Integer STATUS_ENABLE=Integer.valueOf(1);
	public static Integer STATUS_LOCKED=Integer.valueOf(2);
	public static Integer STATUS_DELETE=Integer.valueOf(3);
 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 姓名
 	*/
   	private String userName;
 	/**
 	* 账号
 	*/
   	private String account;
 	/**
 	* 电子邮箱
 	*/
   	private String email;
 	/**
 	* 手机号码
 	*/
   	private String mobilePhone;
 	/**
 	* 密码
 	*/
   	private String password;
   	
   	private String oldPassword;
   	
   	private String newPassword;
   	
   	private String surePassword;
 	/**
 	* 添加时间
 	*/
   	private java.util.Date regTime;
 	/**
 	* 添加ip
 	*/
   	private String regIp;
 	/**
 	* 最后登录时间
 	*/
   	private java.util.Date lastLoginTime;
 	/**
 	* 最后错误登录时间
 	*/
   	private java.util.Date lastLoginErrTime;
 	/**
 	* 最后登录ip
 	*/
   	private String lastLoginIp;
 	/**
 	* 身份证号
 	*/
   	private String idNumber;
 	/**
 	* 登录累计错误次数
 	*/
   	private Integer loginErrTimes;
 	/**
 	* 用户类型(1-超级管理员 2-管理员 3-发布方 4-超级管理员)
 	*/
   	private Integer userType;
 	/**
 	* 状态(1启用 2禁用 3 删除)
 	*/
   	private Integer status;
   	/**
   	 * 用户所在的实验室
   	 */
   	private String location;


   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
	}

   	public void setUserName(String userName){
   		this.userName = userName;
   	}

   	public String getUserName(){
		return userName;
	}

   	public void setAccount(String account){
   		this.account = account;
   	}

   	public String getAccount(){
		return account;
	}

   	public void setEmail(String email){
   		this.email = email;
   	}

   	public String getEmail(){
		return email;
	}

   	public void setMobilePhone(String mobilePhone){
   		this.mobilePhone = mobilePhone;
   	}

   	public String getMobilePhone(){
		return mobilePhone;
	}

   	public void setPassword(String password){
   		this.password = password;
   	}

   	public String getPassword(){
		return password;
	}

   	public void setRegTime(java.util.Date regTime){
   		this.regTime = regTime;
   	}

   	public java.util.Date getRegTime(){
		return regTime;
	}

   	public void setRegIp(String regIp){
   		this.regIp = regIp;
   	}

   	public String getRegIp(){
		return regIp;
	}

   	public void setLastLoginTime(java.util.Date lastLoginTime){
   		this.lastLoginTime = lastLoginTime;
   	}

   	public java.util.Date getLastLoginTime(){
		return lastLoginTime;
	}

   	public void setLastLoginErrTime(java.util.Date lastLoginErrTime){
   		this.lastLoginErrTime = lastLoginErrTime;
   	}

   	public java.util.Date getLastLoginErrTime(){
		return lastLoginErrTime;
	}

   	public void setLastLoginIp(String lastLoginIp){
   		this.lastLoginIp = lastLoginIp;
   	}

   	public String getLastLoginIp(){
		return lastLoginIp;
	}

   	public void setIdNumber(String idNumber){
   		this.idNumber = idNumber;
   	}

   	public String getIdNumber(){
		return idNumber;
	}

   	public void setLoginErrTimes(Integer loginErrTimes){
   		this.loginErrTimes = loginErrTimes;
   	}

   	public Integer getLoginErrTimes(){
		return loginErrTimes;
	}

   	public void setUserType(Integer userType){
   		this.userType = userType;
   	}

   	public Integer getUserType(){
		return userType;
	}

   	public void setStatus(Integer status){
   		this.status = status;
   	}

   	public Integer getStatus(){
		return status;
	}
   	
   	public boolean isLocked(){
   		boolean isLocked=false;
   		if (null!=getStatus()&&SysUser.STATUS_LOCKED.equals(getStatus())) {
			isLocked=true;
		}
   		return isLocked;
   	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getSurePassword() {
		return surePassword;
	}

	public void setSurePassword(String surePassword) {
		this.surePassword = surePassword;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
   	
   	
   	
}

