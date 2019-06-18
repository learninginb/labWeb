package com.simulation.model.knowledge;

import java.util.Date;
import java.io.Serializable;

public class KnowledgeType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final Integer KNOWLEDGE_TYPE_STATE_1=1;//有效
	public static final Integer KNOWLEDGE_TYPE_STATE_0=0;//无效

 	/**
 	* 主键
 	*/
   	private Integer id;
   	
   	/**
   	 * 父节点id
   	 */
   	private Integer parentId;
 	/**
 	* 知识分类
 	*/
   	private String knowledgeType;
 	/**
 	* 知识分类描述
 	*/
   	private String knowledgeTypeDesc;
 	/**
 	* 知识分类状态（1-有效0无效）
 	*/
   	private Integer knowledgeTypeState;
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

   	public void setKnowledgeTypeDesc(String knowledgeTypeDesc){
   		this.knowledgeTypeDesc = knowledgeTypeDesc;
   	}

   	public String getKnowledgeTypeDesc(){
		return knowledgeTypeDesc;
	}

   	public void setKnowledgeTypeState(Integer knowledgeTypeState){
   		this.knowledgeTypeState = knowledgeTypeState;
   	}

   	public Integer getKnowledgeTypeState(){
		return knowledgeTypeState;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
   	
}

