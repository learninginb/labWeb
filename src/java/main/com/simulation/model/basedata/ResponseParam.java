package com.simulation.model.basedata;

import java.util.Map;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月20日 上午11:09:54  
*/
public class ResponseParam {
//状态码
private Integer code;
//响应信息	
private String msg;
//响应体
private Map<String,Object> data ;
public Integer getCode() {
	return code;
}
public void setCode(Integer code) {
	this.code = code;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public Map<String, Object> getData() {
	return data;
}
public void setData(Map<String, Object> data) {
	this.data = data;
}


}
