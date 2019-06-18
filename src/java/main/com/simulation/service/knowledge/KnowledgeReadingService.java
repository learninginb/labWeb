package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeReading;
import com.simulation.vo.knowledge.KnowledgeReadingVo;

public interface KnowledgeReadingService extends BaseMybatisService<KnowledgeReading,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<KnowledgeReading> selectList(KnowledgeReading knowledgeReading);
    
    public Pagination findListByPage(int rows, int page,KnowledgeReadingVo vo);
   
}

