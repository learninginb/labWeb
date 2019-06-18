package com.simulation.websocket.config;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2706478402544338311L;
	private String status;
	private String msg;
	private List<Data> list;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Data> getList() {
		return list;
	}
	public void setList(List<Data> list) {
		this.list = list;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
