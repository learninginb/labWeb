package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeSummary;
import com.simulation.vo.knowledge.KnowledgeSummaryVo;

public interface KnowledgeSummaryDao extends BaseMybatisDao<KnowledgeSummary,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<KnowledgeSummary> selectList(KnowledgeSummary knowledgeSummary);
  
    List<KnowledgeSummaryVo> findListByPage(@Param("vo") KnowledgeSummaryVo vo,@Param("page")Pagination page);

    /**根据knowledgeId查找*/
	public KnowledgeSummary findByKnowledgeId(Integer knowledgeId);

}
