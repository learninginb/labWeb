package com.simulation.model.knowledge;

import java.io.Serializable;

public class KnowledgeHistory implements Serializable{

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
 	* 版本号
 	*/
   	private Double knowledgeVersion;
 	/**
 	* 知识分类id
 	*/
   	private String knowledgeType;
 	/**
 	* 知识标题
 	*/
   	private String knowledgeTitle;
 	/**
 	* 正文
 	*/
   	private String knowledgeComment;
 	/**
 	* 图片地址
 	*/
   	private String knowledgeIconUrl;
 	/**
 	* 附件地址
 	*/
   	private String knowledgeAttachUrl;
 	/**
 	* 状态（0-待审核1-已发布2-已点评3-关注过4-收藏5-被驳回6-过期7-回收站）
 	*/
   	private Integer knowledgeState;
 	/**
 	* 发布人id
 	*/
   	private Integer createUser;


   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
	}

	public void setKnowledgeVersion(Double knowledgeVersion){
   		this.knowledgeVersion = knowledgeVersion;
   	}

   	public Double getKnowledgeVersion(){
		return knowledgeVersion;
	}

   	public void setKnowledgeType(String knowledgeType){
   		this.knowledgeType = knowledgeType;
   	}

   	public String getKnowledgeType(){
		return knowledgeType;
	}

   	public void setKnowledgeTitle(String knowledgeTitle){
   		this.knowledgeTitle = knowledgeTitle;
   	}

   	public String getKnowledgeTitle(){
		return knowledgeTitle;
	}

   	public void setKnowledgeComment(String knowledgeComment){
   		this.knowledgeComment = knowledgeComment;
   	}

   	public String getKnowledgeComment(){
		return knowledgeComment;
	}

   	public void setKnowledgeIconUrl(String knowledgeIconUrl){
   		this.knowledgeIconUrl = knowledgeIconUrl;
   	}

   	public String getKnowledgeIconUrl(){
		return knowledgeIconUrl;
	}

   	public void setKnowledgeAttachUrl(String knowledgeAttachUrl){
   		this.knowledgeAttachUrl = knowledgeAttachUrl;
   	}

   	public String getKnowledgeAttachUrl(){
		return knowledgeAttachUrl;
	}

   	public void setKnowledgeState(Integer knowledgeState){
   		this.knowledgeState = knowledgeState;
   	}

   	public Integer getKnowledgeState(){
		return knowledgeState;
	}

   	public void setCreateUser(Integer createUser){
   		this.createUser = createUser;
   	}

   	public Integer getCreateUser(){
		return createUser;
	}

	public Integer getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Integer knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
   	
}

