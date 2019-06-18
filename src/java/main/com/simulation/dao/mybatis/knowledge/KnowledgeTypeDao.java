package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeType;
import com.simulation.vo.knowledge.KnowledgeTypeVo;

public interface KnowledgeTypeDao extends BaseMybatisDao<KnowledgeType,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<KnowledgeType> selectList(KnowledgeType knowledgeType);
  
    List<KnowledgeTypeVo> findListByPage(@Param("vo") KnowledgeTypeVo vo,@Param("page")Pagination page);

    public List<KnowledgeType> selectListByParentId(@Param("parentId") Integer parentId,@Param("knowledgeTypeState") Integer knowledgeTypeState);

    /**根据parentId和分类名称查找，用于重复性判断*/
	public KnowledgeType selectObj(@Param("parentId") Integer parentId, @Param("knowledgeType") String knowledgeType);

	/**查找子分类*/
	public List<KnowledgeType> selectListByParentIds(@Param("parentIds") List<Integer> parentIds, @Param("knowledgeTypeState") Integer knowledgeTypeState);

}
