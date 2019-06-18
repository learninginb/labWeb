package com.simulation.model.basedata;

import java.io.Serializable;
import java.util.Date;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年5月9日 下午12:15:29  
*/
public class Message implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer receiverId;//接收人id
	private String type;//类型
	private String content;//内容
	private String messageUrl;//消息的路径
	private Date CreateTime;//消息的时间
	private Integer isRead;//是否被读
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiver_id) {
		this.receiverId = receiver_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMessageUrl() {
		return messageUrl;
	}
	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
	

}
