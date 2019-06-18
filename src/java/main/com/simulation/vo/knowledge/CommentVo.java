package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.Comment;

/** 
* @author 作者 ：spxin 
* @version 创建时间：2019年4月3日 下午7:25:17  
*/
public class CommentVo extends Comment{
	private String userName;
	private String paperName;
	//评论是否被点赞
	private boolean praised ;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public boolean getPraised() {
		return praised;
	}
	public void setPraised(boolean praised) {
		this.praised = praised;
	}
	
	
	
	
	
}
