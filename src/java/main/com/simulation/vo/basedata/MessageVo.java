package com.simulation.vo.basedata;

import com.simulation.model.basedata.Message;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午12:26:52  
*/
public class MessageVo extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean readStatus;
	private String receiver;
	public Boolean getReadStatus() {
		
		return readStatus;
	}
	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	} 
	
	
}
