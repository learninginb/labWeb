package com.simulation.service.knowledge;

import java.util.List;

import com.simulation.common.base.BaseMybatisService;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.vo.knowledge.KnowledgeVo;

public interface KnowledgeService extends BaseMybatisService<Knowledge,Integer> {

	public void deleteIds(String ids);
    
    public List<Knowledge> selectList(Knowledge knowledge);
    
    public Pagination findListByPage(int rows, int page,KnowledgeVo vo);

	public KnowledgeVo selectVoByPrimaryKey(Integer id);

	public void updateState(String ids, Integer state);

	public KnowledgeVo readByPrimaryKey(Integer id);

	/**校验*/
	public String validate(Knowledge po);
	
	/**知识修改*/
	public String upadateKnowledge(Knowledge po);
   
}

