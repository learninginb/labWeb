package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeCollection;
import com.simulation.vo.knowledge.KnowledgeCollectionVo;

public interface KnowledgeCollectionDao extends BaseMybatisDao<KnowledgeCollection,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<KnowledgeCollection> selectList(KnowledgeCollection knowledgeCollection);
  
    List<KnowledgeCollectionVo> findListByPage(@Param("vo") KnowledgeCollectionVo vo,@Param("page")Pagination page);

	public void cancelByKnowledgeId(@Param("knowledgeId") Integer knowledgeId, @Param("userId") Integer userId, @Param("valid") Integer valid);

	/**通过knowledgeId查找对象*/
	public KnowledgeCollection findByKnowledgeId(Integer knowledgeId);

}
