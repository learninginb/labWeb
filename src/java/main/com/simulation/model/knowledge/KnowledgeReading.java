package com.simulation.model.knowledge;

import java.io.Serializable;
import java.util.Date;

public class KnowledgeReading implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String COMMENT = "评论";//阅读后评论
	public static final String COLLECTION = "收藏";//阅读后收藏
	public static final String COLLECTION_CANCEL = "取消收藏";//阅读后取消收藏
	
	public static final String FOLLOW = "关注";//阅读后关注
	public static final String FOLLOWED_CANCEL = "取消关注";//阅读后取消关注
	
 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 知识id
 	*/
   	private Integer knowledgeId;
 	/**
 	* 用户id
 	*/
   	private Integer userId;
 	/**
 	* 阅读时间
 	*/
   	private Date readTime;
	
    /**
   	 * 阅读后相关操作
   	 */
    public String aboutOperation;
    
   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
	}

   	public void setKnowledgeId(Integer knowledgeId){
   		this.knowledgeId = knowledgeId;
   	}

   	public Integer getKnowledgeId(){
		return knowledgeId;
	}

   	public void setUserId(Integer userId){
   		this.userId = userId;
   	}

   	public Integer getUserId(){
		return userId;
	}

   	public void setReadTime(java.util.Date readTime){
   		this.readTime = readTime;
   	}

   	public java.util.Date getReadTime(){
		return readTime;
	}

	public String getAboutOperation() {
		return aboutOperation;
	}

	public void setAboutOperation(String aboutOperation) {
		this.aboutOperation = aboutOperation;
	}
	
}

