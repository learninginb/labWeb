package com.simulation.model.knowledge;
import java.io.Serializable;

public class Knowledge implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final Integer knowledge_state_00 = 0;//待审核
	public static final Integer knowledge_state_01 = 1;//已发布
	public static final Integer knowledge_state_02 = 2;//已点评
	public static final Integer knowledge_state_03 = 3;//关注过
	public static final Integer knowledge_state_04 = 4;//收藏
	public static final Integer knowledge_state_05 = 5;//被驳回
	public static final Integer knowledge_state_06 = 6;//过期
	public static final Integer knowledge_state_07 = 7;//回收站
	
 	/**
 	* 主键
 	*/
   	private Integer id;
 	/**
 	* 知识分类id
 	*/
   	private String knowledgeType;
 	/**
 	* 知识标题
 	*/
   	private String knowledgeTitle;
   	
   	/**
   	 * 知识版本号
   	 */
   	private Double knowledgeVersion;
 	/**
 	* 发布人id
 	*/
   	private Integer createUser;
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
 	* 创建时间
 	*/
   	private java.util.Date createTime;
 	/**
 	* 更新时间
 	*/
   	private java.util.Date modifyTime;


   	public void setId(Integer id){
   		this.id = id;
   	}

   	public Integer getId(){
		return id;
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

   	public void setCreateUser(Integer createUser){
   		this.createUser = createUser;
   	}

   	public Integer getCreateUser(){
		return createUser;
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

   	public void setCreateTime(java.util.Date createTime){
   		this.createTime = createTime;
   	}

   	public java.util.Date getCreateTime(){
		return createTime;
	}

   	public void setModifyTime(java.util.Date modifyTime){
   		this.modifyTime = modifyTime;
   	}

   	public java.util.Date getModifyTime(){
		return modifyTime;
	}

	public Double getKnowledgeVersion() {
		return knowledgeVersion;
	}

	public void setKnowledgeVersion(Double knowledgeVersion) {
		this.knowledgeVersion = knowledgeVersion;
	}
   	
}

