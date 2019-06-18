package com.simulation.vo.knowledge;

import com.simulation.model.knowledge.KnowledgeComment;

public class KnowledgeCommentVo extends KnowledgeComment {

	private static final long serialVersionUID = 1L;
	
    public static final String ALIAS_ID = "主键";
    public static final String ALIAS_KNOWLEDGE_ID = "知识id";
    public static final String ALIAS_COMMENT_USER_ID = "评论用户id";
    public static final String ALIAS_COMMENT_CONTENT = "评论内容";
    public static final String ALIAS_KNOWLEDGE_SCORE = "得分";
    public static final String ALIAS_COMMENT_TIME = "评论时间";
    public static final String ALIAS_IS_COMMENT = "";
	
  //知识标题
    public String knowledgeTitle;
    
    //知识分类
    public String knowledgeType;
     
    //阅读用户
    public String commentUserName;

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

	public String getCommentUserName() {
		return commentUserName;
	}

	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
    
}
