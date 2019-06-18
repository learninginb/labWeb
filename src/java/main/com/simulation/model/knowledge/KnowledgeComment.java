package com.simulation.model.knowledge;

import java.io.Serializable;

public class KnowledgeComment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final Integer IS_VALID = 1;//有效
	public static final Integer IS_INVALILD = 0;//无效

 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 知识id
 	*/
   	private Integer knowledgeId;
 	/**
 	* 评论用户id
 	*/
   	private Integer commentUserId;
 	/**
 	* 评论内容
 	*/
   	private String commentContent;
 	/**
 	* 得分
 	*/
   	private Integer knowledgeScore;
 	/**
 	* 评论时间
 	*/
   	private java.util.Date commentTime;
 	/**
 	* 
 	*/
   	private Integer isComment;


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

   	public void setCommentUserId(Integer commentUserId){
   		this.commentUserId = commentUserId;
   	}

   	public Integer getCommentUserId(){
		return commentUserId;
	}

   	public void setCommentContent(String commentContent){
   		this.commentContent = commentContent;
   	}

   	public String getCommentContent(){
		return commentContent;
	}

   	public void setKnowledgeScore(Integer knowledgeScore){
   		this.knowledgeScore = knowledgeScore;
   	}

   	public Integer getKnowledgeScore(){
		return knowledgeScore;
	}

   	public void setCommentTime(java.util.Date commentTime){
   		this.commentTime = commentTime;
   	}

   	public java.util.Date getCommentTime(){
		return commentTime;
	}

   	public void setIsComment(Integer isComment){
   		this.isComment = isComment;
   	}

   	public Integer getIsComment(){
		return isComment;
	}
}

