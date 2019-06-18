package com.simulation.model.knowledge;

import java.util.Date;
import java.io.Serializable;

public class KnowledgeSummary implements Serializable{

	private static final long serialVersionUID = 1L;

 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 知识id
 	*/
   	private Integer knowledgeId;
 	/**
 	* 阅读次数
 	*/
   	private Integer readTimes;
 	/**
 	* 收藏次数
 	*/
   	private Integer collectionTimes;
 	/**
 	* 关注次数
 	*/
   	private Integer followTimes;
 	/**
 	* 评论次数
 	*/
   	private Integer commentTimes;


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

   	public void setReadTimes(Integer readTimes){
   		this.readTimes = readTimes;
   	}

   	public Integer getReadTimes(){
		return readTimes;
	}

   	public void setCollectionTimes(Integer collectionTimes){
   		this.collectionTimes = collectionTimes;
   	}

   	public Integer getCollectionTimes(){
		return collectionTimes;
	}

   	public void setFollowTimes(Integer followTimes){
   		this.followTimes = followTimes;
   	}

   	public Integer getFollowTimes(){
		return followTimes;
	}

   	public void setCommentTimes(Integer commentTimes){
   		this.commentTimes = commentTimes;
   	}

   	public Integer getCommentTimes(){
		return commentTimes;
	}
}

