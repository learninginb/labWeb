package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeSummary;
import com.simulation.vo.knowledge.KnowledgeSummaryVo;

public interface KnowledgeSummaryService extends BaseMybatisService<KnowledgeSummary,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<KnowledgeSummary> selectList(KnowledgeSummary knowledgeSummary);
    
    public Pagination findListByPage(int rows, int page,KnowledgeSummaryVo vo);

    /**阅读次数+1*/
	public void plusReadingTimes(Integer knowledgeId);
   
}

