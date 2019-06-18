package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeType;
import com.simulation.vo.knowledge.KnowledgeTypeVo;

public interface KnowledgeTypeService extends BaseMybatisService<KnowledgeType,Integer> {	
    
    public void deleteIds(String ids);
    
    public List<KnowledgeType> selectList(KnowledgeType knowledgeType);
    
    public Pagination findListByPage(int rows, int page,KnowledgeTypeVo vo);
    
    public List<KnowledgeType> selectListByParentId(Integer parentId);

    /**知识f分类添加，包括一些基本的校验*/
	public String saveKnowledgeType(KnowledgeType po);

	/**批量删除*/
	public String deleteObjs(String ids);
   
}

