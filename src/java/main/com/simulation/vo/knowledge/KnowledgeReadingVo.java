package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.KnowledgeReading;

public class KnowledgeReadingVo extends KnowledgeReading {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_KNOWLEDGE_ID = "知识id";
    public static final String ALIAS_USER_ID = "用户id";
    public static final String ALIAS_READ_TIME = "阅读时间";
	
    //知识标题
    public String knowledgeTitle;
    
    //知识分类
    public String knowledgeType;
     
    //阅读用户
    public String userName;

	public String getKnowledgeTitle() {
		return knowledgeTitle;
	}

	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}

	public String getKnowledgeType() {
		return knowledgeType;
	}

	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
}
