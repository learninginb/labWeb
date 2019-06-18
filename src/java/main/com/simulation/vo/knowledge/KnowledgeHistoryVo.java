package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.KnowledgeHistory;

public class KnowledgeHistoryVo extends KnowledgeHistory {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_KNOWLEDGE_ID = "知识id";
    public static final String ALIAS_KNOWLEDGE_VERSION = "版本号";
    public static final String ALIAS_KNOWLEDGE_TYPE = "知识分类id";
    public static final String ALIAS_KNOWLEDGE_TITLE = "知识标题";
    public static final String ALIAS_KNOWLEDGE_COMMENT = "正文";
    public static final String ALIAS_KNOWLEDGE_ICON_URL = "图片地址";
    public static final String ALIAS_KNOWLEDGE_ATTACH_URL = "附件地址";
    public static final String ALIAS_KNOWLEDGE_STATE = "状态（0-待审核1-已发布2-已点评3-关注过4-收藏5-被驳回6-过期7-回收站）";
    public static final String ALIAS_CREATE_USER = "发布人id";
	
    /**
   	 * 知识分类名称
   	 */
   	private String knowledgeTypeName;
   	
   	/**
   	 * 创建用户名称
   	 */
   	private String createUserName;
   	
    public String getKnowledgeTypeName() {
		return knowledgeTypeName;
	}

	public void setKnowledgeTypeName(String knowledgeTypeName) {
		this.knowledgeTypeName = knowledgeTypeName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
}
