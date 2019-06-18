package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeComment;
import com.simulation.vo.knowledge.KnowledgeCommentVo;

public interface KnowledgeCommentDao extends BaseMybatisDao<KnowledgeComment,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<KnowledgeComment> selectList(KnowledgeComment knowledgeComment);
  
    List<KnowledgeCommentVo> findListByPage(@Param("vo") KnowledgeCommentVo vo,@Param("page")Pagination page);

    /**根据knowledgeId查找*/
	public KnowledgeComment findByKnowledgeId(Integer knowledgeId);

}
