package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeReading;
import com.simulation.vo.knowledge.KnowledgeReadingVo;

public interface KnowledgeReadingDao extends BaseMybatisDao<KnowledgeReading,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<KnowledgeReading> selectList(KnowledgeReading knowledgeReading);
  
    List<KnowledgeReadingVo> findListByPage(@Param("vo") KnowledgeReadingVo vo,@Param("page")Pagination page);

	 /**查询当前用户的最新阅读纪录*/
	public KnowledgeReading selectLastestReading(@Param("knowledgeId") Integer knowledgeId,@Param("userId") Integer userId);

}
