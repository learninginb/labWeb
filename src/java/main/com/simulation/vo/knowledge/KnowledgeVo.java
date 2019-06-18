package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.Knowledge;

public class KnowledgeVo extends Knowledge {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_KNOWLEDGE_TYPE = "知识分类id";
    public static final String ALIAS_KNOWLEDGE_TITLE = "知识标题";
    public static final String ALIAS_CREATE_USER = "发布人id";
    public static final String ALIAS_KNOWLEDGE_COMMENT = "正文";
    public static final String ALIAS_KNOWLEDGE_ICON_URL = "图片地址";
    public static final String ALIAS_KNOWLEDGE_ATTACH_URL = "附件地址";
    public static final String ALIAS_KNOWLEDGE_STATE = "状态（0-待审核1-已发布2-已点评3-关注过4-收藏5-被驳回6-过期7-回收站）";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_MODIFY_TIME = "更新时间";
    
    /**
   	 * 知识分类名称
   	 */
   	private String knowledgeTypeName;
   	
   	/**
   	 * 创建用户名称
   	 */
   	private String createUserName;
   	
   	/**
   	 * 收藏是否有效
   	 */
   	private Integer isCollectionValid;
   	
   	/**
   	 * 阅读次数
   	 */
   	private Integer readingTimes;
   	
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

	public Integer getIsCollectionValid() {
		return isCollectionValid;
	}

	public void setIsCollectionValid(Integer isCollectionValid) {
		this.isCollectionValid = isCollectionValid;
	}

	public Integer getReadingTimes() {
		return readingTimes;
	}

	public void setReadingTimes(Integer readingTimes) {
		this.readingTimes = readingTimes;
	}
	
}
