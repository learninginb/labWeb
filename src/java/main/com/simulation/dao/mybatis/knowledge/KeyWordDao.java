package com.simulation.dao.mybatis.knowledge;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.simulation.common.base.BaseMybatisDao;
import com.simulation.common.page.Pagination;
import com.simulation.model.knowledge.KeyWord;

public interface KeyWordDao extends BaseMybatisDao<KeyWord, Integer>{

	
	void deleteByIds(@Param("ids") List<Integer> idsList);

	List<KeyWord> selectList(KeyWord po);

	List<KeyWord> findListByPage(@Param("vo") KeyWord vo,
			@Param("page") Pagination page);

	List<KeyWord> selectObj(@Param("name") String name);
}