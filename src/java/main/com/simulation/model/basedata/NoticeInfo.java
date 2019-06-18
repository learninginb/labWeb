package com.simulation.model.basedata;


import com.simulation.model.base.ModelBase;
/**
 * 工廠信息
 * @author starlist
 *
 */
public class NoticeInfo extends ModelBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7486408469886981372L;
	
	private String receive_user_ids;
	
	private String content;
	
	private String type;

	public String getReceive_user_ids() {
		return receive_user_ids;
	}

	public void setReceive_user_ids(String receive_user_ids) {
		this.receive_user_ids = receive_user_ids;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
	
	
	

}
