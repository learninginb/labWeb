package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.KnowledgeType;

public class KnowledgeTypeVo extends KnowledgeType {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_PARENT_ID = "父节点id";
    public static final String ALIAS_KNOWLEDGE_TYPE = "知识分类";
    public static final String ALIAS_KNOWLEDGE_TYPE_DESC = "知识分类描述";
    public static final String ALIAS_KNOWLEDGE_TYPE_STATE = "知识分类状态（1-有效0无效）";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_MODIFY_TIME = "更新时间";
    
    public String parentName;//上级分类名称

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
    
	
}
