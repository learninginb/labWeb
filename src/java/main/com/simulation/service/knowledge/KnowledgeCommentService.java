package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeComment;
import com.simulation.vo.knowledge.KnowledgeCommentVo;

public interface KnowledgeCommentService extends BaseMybatisService<KnowledgeComment,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<KnowledgeComment> selectList(KnowledgeComment knowledgeComment);
    
    public Pagination findListByPage(int rows, int page,KnowledgeCommentVo vo);

    /**根据knowledgeId查找*/
	public KnowledgeComment findByKnowledgeId(Integer knowledgeId);

	/**评论*/
	public void comment(KnowledgeComment po);
   
}

