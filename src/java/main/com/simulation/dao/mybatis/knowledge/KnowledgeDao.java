package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Knowledge;
import com.simulation.vo.knowledge.KnowledgeVo;

public interface KnowledgeDao extends BaseMybatisDao<Knowledge,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<Knowledge> selectList(Knowledge knowledge);
  
    List<KnowledgeVo> findListByPage(@Param("vo") KnowledgeVo vo,@Param("page")Pagination page);

	public KnowledgeVo selectVoByPrimaryKey(Integer id);

	public void updateState(@Param("ids") List<Integer> ids, @Param("knowledgeState") Integer knowledgeState);

	public void updateStateByPrimaryKey(@Param("id") Integer id, @Param("knowledgeState") Integer knowledgeState);

	public KnowledgeVo readByPrimaryKey(Integer id);

	/**查找知识条目*/
	public List<Knowledge> selectListByKnowledgeTypes(@Param("knowledgeTypes") List<Integer> knowledgeTypes);
}
