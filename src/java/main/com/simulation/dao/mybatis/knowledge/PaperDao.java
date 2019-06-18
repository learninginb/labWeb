package com.simulation.dao.mybatis.knowledge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.Paper;
import com.simulation.vo.knowledge.PaperVo;

public interface PaperDao extends BaseMybatisDao<Paper, Integer>{

	
	void deleteByIds(@Param("ids") List<Integer> idsList);

	List<Paper> selectList(Paper po);

	List<PaperVo> findListByPage(@Param("vo") Paper vo,@Param("location")String location,
			@Param("page") Pagination page);

	List<Paper> selectObj(@Param("name") String name);
}

