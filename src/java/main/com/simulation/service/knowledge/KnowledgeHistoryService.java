package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.model.knowledge.KnowledgeHistory;
import com.simulation.vo.knowledge.KnowledgeHistoryVo;

public interface KnowledgeHistoryService extends BaseMybatisService<KnowledgeHistory,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<KnowledgeHistory> selectList(KnowledgeHistory knowledgeHistory);
    
    public Pagination findListByPage(int rows, int page,KnowledgeHistoryVo vo);

	public void deleteByKnowledgeIds(String ids);

	/**把po添加到历史版本中*/
	public void addVersion(Knowledge po);
   
}

