package com.simulation.model.knowledge;

import java.io.Serializable;

public class KnowledgeCollection implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final Integer IS_VALID = 1;//有效
	public static final Integer IS_INVALID = 0;//无效

 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 知识id
 	*/
   	private Integer knowledgeId;
 	/**
 	* 收藏用户id
 	*/
   	private Integer userId;
 	/**
 	* 收藏是否有效（1-是0-否）
 	*/
   	private Integer isValid;
 	/**
 	* 
 	*/
   	private java.util.Date collectionTime;


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

   	public void setIsValid(Integer isValid){
   		this.isValid = isValid;
   	}

   	public Integer getIsValid(){
		return isValid;
	}

   	public void setCollectionTime(java.util.Date collectionTime){
   		this.collectionTime = collectionTime;
   	}

   	public java.util.Date getCollectionTime(){
		return collectionTime;
	}
}

