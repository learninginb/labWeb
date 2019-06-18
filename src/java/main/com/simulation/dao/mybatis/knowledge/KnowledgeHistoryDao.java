package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KnowledgeHistory;
import com.simulation.vo.knowledge.KnowledgeHistoryVo;

public interface KnowledgeHistoryDao extends BaseMybatisDao<KnowledgeHistory,Integer> {	
    
    public void deleteByIds(@Param("ids") List<Integer> ids);
    
    public List<KnowledgeHistory> selectList(KnowledgeHistory knowledgeHistory);
  
    List<KnowledgeHistoryVo> findListByPage(@Param("vo") KnowledgeHistoryVo vo,@Param("page")Pagination page);

	public void deleteByKnowledgeIds(@Param("ids") List<Integer> ids);

	/**获取最大版本号的纪录*/
	public KnowledgeHistory selectLastest(@Param("knowledgeId") Integer knowledgeId);

}
