package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeCollection;
import com.simulation.vo.knowledge.KnowledgeCollectionVo;

public interface KnowledgeCollectionService extends BaseMybatisService<KnowledgeCollection,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<KnowledgeCollection> selectList(KnowledgeCollection knowledgeCollection);
    
    public Pagination findListByPage(int rows, int page,KnowledgeCollectionVo vo);

    /**取消收藏*/
	public void cancelCollection(KnowledgeCollection po);

	/**收藏*/
	public void collection(KnowledgeCollection po);
   
}

