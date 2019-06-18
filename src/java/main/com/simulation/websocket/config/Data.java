package com.simulation.websocket.config;

import java.io.Serializable;

public class Data implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9139633279528921805L;
	private int sort;
	private String type;
	private String datas;
	private Integer num;
	private String time;
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDatas() {
		return datas;
	}
	public void setDatas(String datas) {
		this.datas = datas;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	

}
